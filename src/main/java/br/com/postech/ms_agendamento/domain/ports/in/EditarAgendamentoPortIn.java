package br.com.postech.ms_agendamento.domain.ports.in;

import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AgendamentoDtoResponse;
import br.com.postech.ms_agendamento.adapter.in.graphql.dto.EditAgendamentoInputDto;

public interface EditarAgendamentoPortIn {
    public AgendamentoDtoResponse editar(EditAgendamentoInputDto editAgendamentoInputDto);
}
