package br.com.postech.ms_agendamento.application.usecases;

import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AddAgendamentoInputDto;
import br.com.postech.ms_agendamento.adapter.out.persistence.AgendamentoEntity;
import br.com.postech.ms_agendamento.domain.ports.out.AgendamentoRepositoryPortOut;
import br.com.postech.ms_agendamento.domain.ports.out.NotificacaoKafkaPorOut;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CriarAgendamentoUseCaseTest {

    @Mock
    private AgendamentoRepositoryPortOut agendamentoRepositoryPortOut;

    @Mock
    private NotificacaoKafkaPorOut notificacaoKafkaPorOut;

    @InjectMocks
    private CriarAgendamentoUseCase criarAgendamentoUseCase;

    @Test
    void agendamentoCriadoComSucesso(){
        //arrange
        String documento = "32134565412";
        String nomePaciente = "Maria da Silva";
        LocalDateTime dataConsulta = LocalDateTime.of(2025,10,1,12,0,0);
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


        AddAgendamentoInputDto addAgendamentoInputDto =
                new AddAgendamentoInputDto(
                        documento,
                        nomePaciente,
                        dataConsulta,
                        emailPaciente,
                        loginPaciente
                );

        when(agendamentoRepositoryPortOut.save(any())).thenReturn(agendamentoEntity);

        //act
        var retorno = criarAgendamentoUseCase.criar(addAgendamentoInputDto);


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
}
