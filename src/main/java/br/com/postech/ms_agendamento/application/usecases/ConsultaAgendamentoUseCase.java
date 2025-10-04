package br.com.postech.ms_agendamento.application.usecases;

import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AgendamentoDtoResponse;
import br.com.postech.ms_agendamento.adapter.out.persistence.AgendamentoEntity;
import br.com.postech.ms_agendamento.domain.ports.in.ConsultaAgendamentoPortIn;
import br.com.postech.ms_agendamento.domain.ports.out.AgendamentoRepositoryPortOut;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultaAgendamentoUseCase implements ConsultaAgendamentoPortIn {

    private final AgendamentoRepositoryPortOut agendamentoRepositoryPortOut;

    public ConsultaAgendamentoUseCase(AgendamentoRepositoryPortOut agendamentoRepositoryPortOut){
        this.agendamentoRepositoryPortOut = agendamentoRepositoryPortOut;
    }

    public List<AgendamentoDtoResponse> listarHistorico(){

        var listaAgendamentos = agendamentoRepositoryPortOut.findAll();

        List<AgendamentoDtoResponse> agendamentoDtoResponseList = new ArrayList<>();

        if(!listaAgendamentos.isEmpty()){
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
        }

        return agendamentoDtoResponseList;
     }
}
