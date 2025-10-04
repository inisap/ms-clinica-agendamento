package br.com.postech.ms_agendamento.application.usecases;

import br.com.postech.ms_agendamento.adapter.in.graphql.dto.EditAgendamentoInputDto;
import br.com.postech.ms_agendamento.adapter.out.persistence.AgendamentoEntity;
import br.com.postech.ms_agendamento.domain.exceptions.IdNaoExisteException;
import br.com.postech.ms_agendamento.domain.exceptions.SituacaoNaoPermiteEdicaoException;
import br.com.postech.ms_agendamento.domain.ports.out.AgendamentoRepositoryPortOut;
import br.com.postech.ms_agendamento.domain.ports.out.NotificacaoKafkaPorOut;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditarAgendamentoUseCaseTest {

    @Mock
    private AgendamentoRepositoryPortOut agendamentoRepositoryPortOut;

    @Mock
    private NotificacaoKafkaPorOut notificacaoKafkaPorOut;

    @InjectMocks
    private EditarAgendamentoUseCase editarAgendamentoUseCase;

    @Test
    void editandoParaCanceladoComSucesso(){
        //arrange
        Long id = 1L;
        String documento = "32134565412";
        String nomePaciente = "Maria da Silva";
        LocalDateTime dataConsulta = LocalDateTime.of(2025,10,1,12,0,0);
        String situacao = "CANCELADO";
        String emailPaciente = "maria@teste.com.br";
        String loginPaciente = "masilva";

        var agendamentoEntity = AgendamentoEntity.builder()
                .id(1L)
                .documentoPaciente(documento)
                .nomePaciente(nomePaciente)
                .dataConsulta(dataConsulta)
                .situacao("AGENDADO")
                .emailPaciente(emailPaciente)
                .login(loginPaciente)
                .build();


        EditAgendamentoInputDto addAgendamentoInputDto =
                new EditAgendamentoInputDto(
                        id,
                        documento,
                        nomePaciente,
                        dataConsulta,
                        situacao,
                        emailPaciente,
                        loginPaciente
                );

        when(agendamentoRepositoryPortOut.findById(any())).thenReturn(Optional.of(agendamentoEntity));

        when(agendamentoRepositoryPortOut.save(any())).thenReturn(agendamentoEntity);

        //act
        var retorno = editarAgendamentoUseCase.editar(addAgendamentoInputDto);


        //assert
        verify(notificacaoKafkaPorOut, times(1)).enviarNotificacao(any());
        assertEquals(retorno.getId(), 1L);
        assertEquals(retorno.getDocumentoPaciente(), documento);
        assertEquals(retorno.getNomePaciente(), nomePaciente);
        assertEquals(retorno.getDataConsulta(), dataConsulta);
        assertEquals(retorno.getSituacao(), "CANCELADO");
        assertEquals(retorno.getEmailPaciente(), emailPaciente);
        assertEquals(retorno.getLogin(), loginPaciente);

    }

    @Test
    void editandoParaCanceladoSemPassarDemaisDadosComSucesso(){
        //arrange
        Long id = 1L;
        String documento = "32134565412";
        String nomePaciente = "Maria da Silva";
        LocalDateTime dataConsulta = LocalDateTime.of(2025,10,1,12,0,0);
        String situacao = "CANCELADO";
        String emailPaciente = "maria@teste.com.br";
        String loginPaciente = "masilva";

        var agendamentoEntity = AgendamentoEntity.builder()
                .id(1L)
                .documentoPaciente(documento)
                .nomePaciente(nomePaciente)
                .dataConsulta(dataConsulta)
                .situacao("AGENDADO")
                .emailPaciente(emailPaciente)
                .login(loginPaciente)
                .build();


        EditAgendamentoInputDto addAgendamentoInputDto =
                new EditAgendamentoInputDto(
                        id,
                        null,
                        null,
                        null,
                        situacao,
                        null,
                        null
                );

        when(agendamentoRepositoryPortOut.findById(any())).thenReturn(Optional.of(agendamentoEntity));

        when(agendamentoRepositoryPortOut.save(any())).thenReturn(agendamentoEntity);

        //act
        var retorno = editarAgendamentoUseCase.editar(addAgendamentoInputDto);


        //assert
        verify(notificacaoKafkaPorOut, times(1)).enviarNotificacao(any());
        assertEquals(retorno.getId(), 1L);
        assertEquals(retorno.getDocumentoPaciente(), documento);
        assertEquals(retorno.getNomePaciente(), nomePaciente);
        assertEquals(retorno.getDataConsulta(), dataConsulta);
        assertEquals(retorno.getSituacao(), "CANCELADO");
        assertEquals(retorno.getEmailPaciente(), emailPaciente);
        assertEquals(retorno.getLogin(), loginPaciente);

    }

    @Test
    void editandoAlterandoApenasDataComSucesso(){
        //arrange
        Long id = 1L;
        String documento = "32134565412";
        String nomePaciente = "Maria da Silva";
        LocalDateTime dataConsulta = LocalDateTime.of(2025,10,1,12,0,0);
        String situacao = "AGENDADO";
        String emailPaciente = "maria@teste.com.br";
        String loginPaciente = "masilva";

        var agendamentoEntity = AgendamentoEntity.builder()
                .id(1L)
                .documentoPaciente(documento)
                .nomePaciente(nomePaciente)
                .dataConsulta(dataConsulta)
                .situacao("AGENDADO")
                .emailPaciente(emailPaciente)
                .login(loginPaciente)
                .build();


        EditAgendamentoInputDto addAgendamentoInputDto =
                new EditAgendamentoInputDto(
                        id,
                        null,
                        null,
                        dataConsulta,
                        situacao,
                        null,
                        null
                );

        when(agendamentoRepositoryPortOut.findById(any())).thenReturn(Optional.of(agendamentoEntity));

        when(agendamentoRepositoryPortOut.save(any())).thenReturn(agendamentoEntity);

        //act
        var retorno = editarAgendamentoUseCase.editar(addAgendamentoInputDto);


        //assert
        verify(notificacaoKafkaPorOut, times(1)).enviarNotificacao(any());
        assertEquals(retorno.getId(), 1L);
        assertEquals(retorno.getDocumentoPaciente(), documento);
        assertEquals(retorno.getNomePaciente(), nomePaciente);
        assertEquals(retorno.getDataConsulta(), dataConsulta);
        assertEquals(retorno.getSituacao(), "AGENDADO");
        assertEquals(retorno.getEmailPaciente(), emailPaciente);
        assertEquals(retorno.getLogin(), loginPaciente);

    }

    @Test
    void falhaAoTentareditarParaCanceladoPoisJaEstavaConcluido(){
        //arrange
        Long id = 1L;
        String documento = "32134565412";
        String nomePaciente = "Maria da Silva";
        LocalDateTime dataConsulta = LocalDateTime.of(2025,10,1,12,0,0);
        String situacao = "CANCELADO";
        String emailPaciente = "maria@teste.com.br";
        String loginPaciente = "masilva";

        var agendamentoEntity = AgendamentoEntity.builder()
                .id(1L)
                .documentoPaciente(documento)
                .nomePaciente(nomePaciente)
                .dataConsulta(dataConsulta)
                .situacao("CONCLUIDO")
                .emailPaciente(emailPaciente)
                .login(loginPaciente)
                .build();


        EditAgendamentoInputDto editAgendamentoInputDto =
                new EditAgendamentoInputDto(
                        id,
                        documento,
                        nomePaciente,
                        dataConsulta,
                        situacao,
                        emailPaciente,
                        loginPaciente
                );

        when(agendamentoRepositoryPortOut.findById(any())).thenReturn(Optional.of(agendamentoEntity));

        //act
        assertThrows(SituacaoNaoPermiteEdicaoException.class, ()
                -> editarAgendamentoUseCase.editar(editAgendamentoInputDto));

        //assert
        verify(notificacaoKafkaPorOut, times(0)).enviarNotificacao(any());
        verify(agendamentoRepositoryPortOut, times(0)).save(any());
    }

    @Test
    void falhaAoTentareditarPoisIdNaoExiste(){
        //arrange
        Long id = 2L;
        String documento = "32134565412";
        String nomePaciente = "Maria da Silva";
        LocalDateTime dataConsulta = LocalDateTime.of(2025,10,1,12,0,0);
        String situacao = "CANCELADO";
        String emailPaciente = "maria@teste.com.br";
        String loginPaciente = "masilva";

        EditAgendamentoInputDto editAgendamentoInputDto =
                new EditAgendamentoInputDto(
                        id,
                        documento,
                        nomePaciente,
                        dataConsulta,
                        situacao,
                        emailPaciente,
                        loginPaciente
                );

        when(agendamentoRepositoryPortOut.findById(any())).thenReturn(Optional.empty());

        //act
        assertThrows(IdNaoExisteException.class, ()
                -> editarAgendamentoUseCase.editar(editAgendamentoInputDto));

        //assert
        verify(notificacaoKafkaPorOut, times(0)).enviarNotificacao(any());
        verify(agendamentoRepositoryPortOut, times(0)).save(any());
    }
}
