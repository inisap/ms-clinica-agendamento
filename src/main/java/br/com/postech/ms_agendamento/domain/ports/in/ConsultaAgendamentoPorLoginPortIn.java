package br.com.postech.ms_agendamento.domain.ports.in;

import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AgendamentoDtoResponse;

import java.util.List;

public interface ConsultaAgendamentoPorLoginPortIn {
    public List<AgendamentoDtoResponse> consultaAgendamentoPorLogin(String login);
}
