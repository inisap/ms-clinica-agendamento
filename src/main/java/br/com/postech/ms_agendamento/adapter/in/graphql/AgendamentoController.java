package br.com.postech.ms_agendamento.adapter.in.graphql;

import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AddAgendamentoInputDto;
import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AgendamentoDtoResponse;
import br.com.postech.ms_agendamento.adapter.in.graphql.dto.EditAgendamentoInputDto;
import br.com.postech.ms_agendamento.application.usecases.*;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AgendamentoController {

    private final CriarAgendamentoUseCase criarAgendamentoUseCase;
    private final ConsultaAgendamentoUseCase consultaAgendamentoUseCase;
    private final EditarAgendamentoUseCase editarAgendamentoUseCase;
    private final ConsultaAgendamentoPorIdUseCase consultaAgendamentoPorIdUseCase;
    private final ConsultaAgendamentoPorLoginUseCase consultaAgendamentoPorLoginUseCase;

    public AgendamentoController(CriarAgendamentoUseCase criarAgendamentoUseCase,
                                 ConsultaAgendamentoUseCase consultaAgendamentoUseCase,
                                 EditarAgendamentoUseCase editarAgendamentoUseCase,
                                 ConsultaAgendamentoPorIdUseCase consultaAgendamentoPorIdUseCase,
                                 ConsultaAgendamentoPorLoginUseCase consultaAgendamentoPorLoginUseCase) {
        this.criarAgendamentoUseCase = criarAgendamentoUseCase;
        this.consultaAgendamentoUseCase = consultaAgendamentoUseCase;
        this.editarAgendamentoUseCase = editarAgendamentoUseCase;
        this.consultaAgendamentoPorIdUseCase = consultaAgendamentoPorIdUseCase;
        this.consultaAgendamentoPorLoginUseCase = consultaAgendamentoPorLoginUseCase;
    }

    public static final String ENFERMEIRO = "ROLE_ENFERMEIRO";
    public static final String MEDICO = "ROLE_MEDICO";
    public static final String PACIENTE = "ROLE_PACIENTE";

    @PreAuthorize("hasRole('"+ENFERMEIRO+"') or hasRole('"+MEDICO+"')")
    @QueryMapping
    public List<AgendamentoDtoResponse> listAgendamentos() {
        return consultaAgendamentoUseCase.listarHistorico();
    }

    @PreAuthorize("hasRole('"+ENFERMEIRO+"') or hasRole('"+MEDICO+"')")
    @QueryMapping
    public AgendamentoDtoResponse listAgendamentoPorId(@Argument Long id) {
        return consultaAgendamentoPorIdUseCase.consultaPorIdDoAgendamento(id);
    }

    @PreAuthorize("hasRole('"+PACIENTE+"') and (authentication.principal.claims['sub'] == #login)")
    @QueryMapping
    public List<AgendamentoDtoResponse> listAgendamentoPorLogin(@Argument String login) {
        return consultaAgendamentoPorLoginUseCase.consultaAgendamentoPorLogin(login);
    }

    @PreAuthorize("hasRole('"+ENFERMEIRO+"')")
    @MutationMapping
    public AgendamentoDtoResponse criarAgendamento(@Argument AddAgendamentoInputDto input) {

        return criarAgendamentoUseCase.criar(input);
    }

    @PreAuthorize("hasRole('"+ENFERMEIRO+"') or hasRole('"+MEDICO+"')")
    @MutationMapping
    public AgendamentoDtoResponse editarAgendamento(@Argument EditAgendamentoInputDto input) {

        return editarAgendamentoUseCase.editar(input);
    }
}

