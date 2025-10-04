package br.com.postech.ms_agendamento.application.usecases;

import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AgendamentoDtoResponse;
import br.com.postech.ms_agendamento.adapter.out.persistence.AgendamentoEntity;
import br.com.postech.ms_agendamento.domain.exceptions.IdNaoExisteException;
import br.com.postech.ms_agendamento.domain.ports.out.AgendamentoRepositoryPortOut;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsultaAgendamentoPorIdUseCaseTest {

    @Mock
    private AgendamentoRepositoryPortOut agendamentoRepositoryPortOut;

    @InjectMocks
    private ConsultaAgendamentoPorIdUseCase consultaAgendamentoPorIdUseCase;

    @Test
    void falhaNaconsultaPorNaoExistirId(){
        //arrange
        Long id = 1L;
        when(agendamentoRepositoryPortOut.findById(id)).thenReturn(Optional.empty());

        // act / assert
        assertThrows(IdNaoExisteException.class, () -> consultaAgendamentoPorIdUseCase.consultaPorIdDoAgendamento(id));
    }

    @Test
    void consultaComSucesso(){
        //arrange
        AgendamentoEntity entity = AgendamentoEntity.builder()
                .id(1L)
                .dataConsulta(LocalDateTime.of(2025,10, 30, 11, 0, 0))
                .login("maria")
                .documentoPaciente("32143123423")
                .emailPaciente("maria@teste.com.br")
                .nomePaciente("Maria Da Silva")
                .situacao("AGENDADO")
                .build();

        AgendamentoDtoResponse response = AgendamentoDtoResponse.builder()
                .id(1L)
                .dataConsulta(LocalDateTime.of(2025,10, 30, 11, 0, 0))
                .login("maria")
                .documentoPaciente("32143123423")
                .emailPaciente("maria@teste.com.br")
                .nomePaciente("Maria Da Silva")
                .situacao("AGENDADO")
                .build();

        Long id = 1L;
        when(agendamentoRepositoryPortOut.findById(id)).thenReturn(Optional.of(entity));

        // act / assert
        var retorno = consultaAgendamentoPorIdUseCase.consultaPorIdDoAgendamento(id);

        assertEquals(retorno.getId(), response.getId());
        assertEquals(retorno.getDataConsulta(), response.getDataConsulta());
        assertEquals(retorno.getDocumentoPaciente(), response.getDocumentoPaciente());
        assertEquals(retorno.getLogin(), response.getLogin());
        assertEquals(retorno.getNomePaciente(), response.getNomePaciente());
        assertEquals(retorno.getEmailPaciente(), response.getEmailPaciente());
        assertEquals(retorno.getSituacao(), response.getSituacao());
    }


}
