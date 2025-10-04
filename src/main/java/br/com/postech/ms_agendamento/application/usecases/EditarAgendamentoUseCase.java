package br.com.postech.ms_agendamento.application.usecases;

import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AgendamentoDtoResponse;
import br.com.postech.ms_agendamento.adapter.in.graphql.dto.EditAgendamentoInputDto;
import br.com.postech.ms_agendamento.adapter.out.kafka.dto.MessageAgendamento;
import br.com.postech.ms_agendamento.domain.exceptions.IdNaoExisteException;
import br.com.postech.ms_agendamento.domain.exceptions.SituacaoNaoPermiteEdicaoException;
import br.com.postech.ms_agendamento.domain.ports.in.EditarAgendamentoPortIn;
import br.com.postech.ms_agendamento.domain.ports.out.AgendamentoRepositoryPortOut;
import br.com.postech.ms_agendamento.domain.ports.out.NotificacaoKafkaPorOut;
import br.com.postech.ms_agendamento.domain.utils.SituacoesAgendamento;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EditarAgendamentoUseCase implements EditarAgendamentoPortIn {

    private final AgendamentoRepositoryPortOut agendamentoRepositoryPortOut;

    private final NotificacaoKafkaPorOut notificacaoKafkaPorOut;

    public EditarAgendamentoUseCase(AgendamentoRepositoryPortOut agendamentoRepositoryPortOut,
                                    NotificacaoKafkaPorOut notificacaoKafkaPorOut){
        this.agendamentoRepositoryPortOut = agendamentoRepositoryPortOut;
        this.notificacaoKafkaPorOut = notificacaoKafkaPorOut;
    }

    @Override
    public AgendamentoDtoResponse editar(EditAgendamentoInputDto editAgendamentoInputDto){

        //consultar se id consulta existre
        var entity = agendamentoRepositoryPortOut.findById(editAgendamentoInputDto.getId());
        if(entity.isEmpty()){
            throw new IdNaoExisteException(editAgendamentoInputDto.getId());
        }

        //valida se pode editar devido a situacao
        if(!validaPermiteEdicao(entity.get().getSituacao())){
            throw new SituacaoNaoPermiteEdicaoException(entity.get().getSituacao());
        }

        var agendamentoEditar = entity.get();

        agendamentoEditar.setDocumentoPaciente(StringUtils.isNotBlank(editAgendamentoInputDto.getDocumentoPaciente()) ?
                editAgendamentoInputDto.getDocumentoPaciente() : agendamentoEditar.getDocumentoPaciente());
        agendamentoEditar.setDataConsulta(editAgendamentoInputDto.getDataConsulta() != null ?
                editAgendamentoInputDto.getDataConsulta() : agendamentoEditar.getDataConsulta());
        agendamentoEditar.setNomePaciente(StringUtils.isNotBlank(editAgendamentoInputDto.getNomePaciente()) ?
                editAgendamentoInputDto.getNomePaciente() : agendamentoEditar.getNomePaciente());
        agendamentoEditar.setSituacao(StringUtils.isNotBlank(editAgendamentoInputDto.getSituacao()) ?
                editAgendamentoInputDto.getSituacao() : agendamentoEditar.getSituacao());
        agendamentoEditar.setEmailPaciente(StringUtils.isNotBlank(editAgendamentoInputDto.getEmailPaciente()) ?
                editAgendamentoInputDto.getEmailPaciente() : agendamentoEditar.getEmailPaciente());

        var agendamentoSalvo = agendamentoRepositoryPortOut.save(agendamentoEditar);

        //envia msg para servico de notificacao
        MessageAgendamento msg = MessageAgendamento.builder()
                .idConsulta(agendamentoSalvo.getId())
                .dataAgendamento(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(agendamentoSalvo.getDataConsulta()))
                .emailsPaciente(agendamentoSalvo.getEmailPaciente())
                .nomePaciente(agendamentoSalvo.getNomePaciente())
                .statusAgendamento(agendamentoSalvo.getSituacao())
                .build();

        notificacaoKafkaPorOut.enviarNotificacao(msg);

        return AgendamentoDtoResponse.builder()
                .id(agendamentoSalvo.getId())
                .documentoPaciente(agendamentoSalvo.getDocumentoPaciente())
                .nomePaciente(agendamentoSalvo.getNomePaciente())
                .dataConsulta(agendamentoSalvo.getDataConsulta())
                .situacao(agendamentoSalvo.getSituacao())
                .emailPaciente(agendamentoSalvo.getEmailPaciente())
                .login(agendamentoSalvo.getLogin())
                .build();
     }

     private boolean validaPermiteEdicao(String situacaoAtual){
        return SituacoesAgendamento.getSituacao(situacaoAtual);
     }

}
