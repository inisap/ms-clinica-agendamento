package br.com.postech.ms_agendamento.adapter.in.grahql;

import br.com.postech.ms_agendamento.adapter.in.graphql.AgendamentoController;
import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AddAgendamentoInputDto;
import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AgendamentoDtoResponse;
import br.com.postech.ms_agendamento.adapter.in.graphql.dto.EditAgendamentoInputDto;
import br.com.postech.ms_agendamento.application.usecases.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendamentoControllerTest {

    @Mock
    private CriarAgendamentoUseCase criarAgendamentoUseCase;
    @Mock
    private ConsultaAgendamentoUseCase consultaAgendamentoUseCase;
    @Mock
    private EditarAgendamentoUseCase editarAgendamentoUseCase;
    @Mock
    private ConsultaAgendamentoPorIdUseCase consultaAgendamentoPorIdUseCase;
    @Mock
    private ConsultaAgendamentoPorLoginUseCase consultaAgendamentoPorLoginUseCase;

    @InjectMocks
    private AgendamentoController controller;

    @Test
    void deveListarAgendamentos() {
        String documento = "32134565412";
        String nomePaciente = "Maria da Silva";
        LocalDateTime dataConsulta = LocalDateTime.of(2025,10,1,12,0,0);
        String emailPaciente = "maria@teste.com.br";
        String loginPaciente = "masilva";

        var agendamento = new AgendamentoDtoResponse(
                1L,
                documento,
                nomePaciente,
                dataConsulta,
                "AGENDADO",
                emailPaciente,
                loginPaciente
                );
        when(consultaAgendamentoUseCase.listarHistorico()).thenReturn(List.of(agendamento));

        var result = controller.listAgendamentos();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNomePaciente()).isEqualTo(nomePaciente);
        verify(consultaAgendamentoUseCase, times(1)).listarHistorico();
    }

    @Test
    void deveCriarAgendamento() {
        String documento = "32134565412";
        String nomePaciente = "Maria da Silva";
        LocalDateTime dataConsulta = LocalDateTime.of(2025,10,1,12,0,0);
        String emailPaciente = "maria@teste.com.br";
        String loginPaciente = "masilva";

        var input = new AddAgendamentoInputDto(
                documento,
                nomePaciente,
                dataConsulta,
                emailPaciente,
                loginPaciente
        );
        var esperado = new AgendamentoDtoResponse(
                1L,
                documento,
                nomePaciente,
                dataConsulta,
                "AGENDADO",
                emailPaciente,
                loginPaciente
                );

        when(criarAgendamentoUseCase.criar(input)).thenReturn(esperado);

        var result = controller.criarAgendamento(input);

        assertThat(result.getId()).isEqualTo(1L);
        verify(criarAgendamentoUseCase).criar(input);
    }

    @Test
    void deveRetornaAgendamentoPorId() {
        Long id = 1L;
        String documento = "32134565412";
        String nomePaciente = "Maria da Silva";
        LocalDateTime dataConsulta = LocalDateTime.of(2025,10,1,12,0,0);
        String emailPaciente = "maria@teste.com.br";
        String loginPaciente = "masilva";

        var agendamento = new AgendamentoDtoResponse(
                1L,
                documento,
                nomePaciente,
                dataConsulta,
                "AGENDADO",
                emailPaciente,
                loginPaciente
        );
        when(consultaAgendamentoPorIdUseCase.consultaPorIdDoAgendamento(id)).thenReturn(agendamento);

        var result = controller.listAgendamentoPorId(id);

        assertEquals(result.getNomePaciente(), nomePaciente);
        assertEquals(result.getLogin(), loginPaciente);
        assertEquals(result.getSituacao(), "AGENDADO");
        assertEquals(result.getDataConsulta(), dataConsulta);
        assertEquals(result.getEmailPaciente(), emailPaciente);
        assertEquals(result.getId(), id);
        verify(consultaAgendamentoPorIdUseCase, times(1)).consultaPorIdDoAgendamento(any());
    }

    @Test
    void deveListarPorLogin() {
        String documento = "32134565412";
        String nomePaciente = "Maria da Silva";
        LocalDateTime dataConsulta = LocalDateTime.of(2025,10,1,12,0,0);
        String emailPaciente = "maria@teste.com.br";
        String loginPaciente = "masilva";

        var agendamento = new AgendamentoDtoResponse(
                1L,
                documento,
                nomePaciente,
                dataConsulta,
                "AGENDADO",
                emailPaciente,
                loginPaciente
        );
        when(consultaAgendamentoPorLoginUseCase.consultaAgendamentoPorLogin(loginPaciente)).thenReturn(List.of(agendamento));

        var result = controller.listAgendamentoPorLogin(loginPaciente);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNomePaciente()).isEqualTo(nomePaciente);
        verify(consultaAgendamentoPorLoginUseCase, times(1)).consultaAgendamentoPorLogin(any());
    }

    @Test
    void deveEditarAgendamento() {
        Long id = 1L;
        String documento = "32134565412";
        String nomePaciente = "Maria da Silva";
        String situacao = "CANCELADO";
        LocalDateTime dataConsulta = LocalDateTime.of(2025,10,1,12,0,0);
        String emailPaciente = "maria@teste.com.br";
        String loginPaciente = "masilva";

        var input = new EditAgendamentoInputDto(
                id,
                documento,
                nomePaciente,
                dataConsulta,
                emailPaciente,
                situacao,
                loginPaciente
        );
        var esperado = new AgendamentoDtoResponse(
                1L,
                documento,
                nomePaciente,
                dataConsulta,
                "AGENDADO",
                emailPaciente,
                loginPaciente
        );

        when(editarAgendamentoUseCase.editar(input)).thenReturn(esperado);

        var result = controller.editarAgendamento(input);

        assertThat(result.getId()).isEqualTo(1L);
        verify(editarAgendamentoUseCase).editar(input);
    }
}
