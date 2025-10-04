package br.com.postech.ms_agendamento.domain.ports.in;

import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AddAgendamentoInputDto;
import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AgendamentoDtoResponse;

public interface CriarAgendamentoPortIn {
    public AgendamentoDtoResponse criar(AddAgendamentoInputDto agendamentoInput);
}
