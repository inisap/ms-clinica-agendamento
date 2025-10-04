package br.com.postech.ms_agendamento.application.usecases;

import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AgendamentoDtoResponse;
import br.com.postech.ms_agendamento.adapter.out.persistence.AgendamentoEntity;
import br.com.postech.ms_agendamento.domain.exceptions.RegistroNaoEncontradoException;
import br.com.postech.ms_agendamento.domain.ports.in.ConsultaAgendamentoPorLoginPortIn;
import br.com.postech.ms_agendamento.domain.ports.out.AgendamentoRepositoryPortOut;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultaAgendamentoPorLoginUseCase implements ConsultaAgendamentoPorLoginPortIn {

    private final AgendamentoRepositoryPortOut agendamentoRepositoryPortOut;

    public ConsultaAgendamentoPorLoginUseCase(AgendamentoRepositoryPortOut agendamentoRepositoryPortOut){
        this.agendamentoRepositoryPortOut = agendamentoRepositoryPortOut;
    }

    public List<AgendamentoDtoResponse> consultaAgendamentoPorLogin(String login){

        var consultaAgendamentosListOptional = agendamentoRepositoryPortOut.findByLogin(login);

        if(consultaAgendamentosListOptional.isEmpty()){
            throw new RegistroNaoEncontradoException();
        }

        var listaAgendamentos = consultaAgendamentosListOptional.get();

        List<AgendamentoDtoResponse> agendamentoDtoResponseList = new ArrayList<>();

            for (AgendamentoEntity entity : listaAgendamentos){
                agendamentoDtoResponseList.add(
                        AgendamentoDtoResponse.builder()
                                .id(entity.getId())
                                .documentoPaciente(entity.getDocumentoPaciente())
                                .nomePaciente(entity.getNomePaciente())
                                .dataConsulta(entity.getDataConsulta())
                                .situacao(entity.getSituacao())
                                .emailPaciente(entity.getEmailPaciente())
                                .login(entity.getLogin())
                                .build()
                );
            }

        return agendamentoDtoResponseList;
     }
}
