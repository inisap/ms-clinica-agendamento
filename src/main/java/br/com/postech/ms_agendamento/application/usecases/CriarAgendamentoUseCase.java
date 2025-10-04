package br.com.postech.ms_agendamento.application.usecases;

import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AddAgendamentoInputDto;
import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AgendamentoDtoResponse;
import br.com.postech.ms_agendamento.adapter.out.kafka.dto.MessageAgendamento;
import br.com.postech.ms_agendamento.adapter.out.persistence.AgendamentoEntity;
import br.com.postech.ms_agendamento.domain.ports.in.CriarAgendamentoPortIn;
import br.com.postech.ms_agendamento.domain.ports.out.AgendamentoRepositoryPortOut;
import br.com.postech.ms_agendamento.domain.ports.out.NotificacaoKafkaPorOut;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class CriarAgendamentoUseCase implements CriarAgendamentoPortIn {

    private final AgendamentoRepositoryPortOut agendamentoRepositoryPortOut;
    private final NotificacaoKafkaPorOut notificacaoKafkaPorOut;

    public CriarAgendamentoUseCase(AgendamentoRepositoryPortOut agendamentoRepositoryPortOut,
                                   NotificacaoKafkaPorOut notificacaoKafkaPorOut){
        this.agendamentoRepositoryPortOut = agendamentoRepositoryPortOut;
        this.notificacaoKafkaPorOut = notificacaoKafkaPorOut;
    }

    @Override
    public AgendamentoDtoResponse criar(AddAgendamentoInputDto input){

        var agendamentoEntity = AgendamentoEntity.builder()
                .documentoPaciente(input.getDocumentoPaciente())
                .nomePaciente(input.getNomePaciente())
                .dataConsulta(input.getDataConsulta())
                .situacao("AGENDADO")
                .emailPaciente(input.getEmailPaciente())
                .login(input.getLogin())
                .build();

        var agendamentoSalvo = agendamentoRepositoryPortOut.save(agendamentoEntity);

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
}
