package br.com.postech.ms_agendamento.application.usecases;

import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AgendamentoDtoResponse;
import br.com.postech.ms_agendamento.domain.exceptions.IdNaoExisteException;
import br.com.postech.ms_agendamento.domain.ports.in.ConsultaAgendamentoPorIdPortIn;
import br.com.postech.ms_agendamento.domain.ports.out.AgendamentoRepositoryPortOut;
import org.springframework.stereotype.Service;

@Service
public class ConsultaAgendamentoPorIdUseCase implements ConsultaAgendamentoPorIdPortIn {

    private final AgendamentoRepositoryPortOut agendamentoRepositoryPortOut;

    public ConsultaAgendamentoPorIdUseCase(AgendamentoRepositoryPortOut agendamentoRepositoryPortOut){
        this.agendamentoRepositoryPortOut = agendamentoRepositoryPortOut;
    }

    public AgendamentoDtoResponse consultaPorIdDoAgendamento(Long idAgendamento){

        var consultaAgendamentosOptional = agendamentoRepositoryPortOut.findById(idAgendamento);

        if(consultaAgendamentosOptional.isEmpty()){
            throw new IdNaoExisteException(idAgendamento);
        }

        var agendamento = consultaAgendamentosOptional.get();

        return AgendamentoDtoResponse.builder()
                .id(agendamento.getId())
                .documentoPaciente(agendamento.getDocumentoPaciente())
                .nomePaciente(agendamento.getNomePaciente())
                .dataConsulta(agendamento.getDataConsulta())
                .situacao(agendamento.getSituacao())
                .emailPaciente(agendamento.getEmailPaciente())
                .login(agendamento.getLogin())
                .build();
     }
}
