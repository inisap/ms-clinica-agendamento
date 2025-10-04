package br.com.postech.ms_agendamento.application.usecases;

import br.com.postech.ms_agendamento.adapter.in.graphql.dto.AgendamentoDtoResponse;
import br.com.postech.ms_agendamento.adapter.out.persistence.AgendamentoEntity;
import br.com.postech.ms_agendamento.domain.exceptions.RegistroNaoEncontradoException;
import br.com.postech.ms_agendamento.domain.ports.out.AgendamentoRepositoryPortOut;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsultaAgendamentoPorLoginUseCaseTest {

    @Mock
    private AgendamentoRepositoryPortOut agendamentoRepositoryPortOut;

    @InjectMocks
    private ConsultaAgendamentoPorLoginUseCase consultaAgendamentoPorLoginUseCase;

    @Test
    void falhaAoRealizarConsulta(){
        //arrange
        when(agendamentoRepositoryPortOut.findByLogin(any())).thenReturn(Optional.empty());

        //act / assert
        assertThrows(RegistroNaoEncontradoException.class, ()
                -> consultaAgendamentoPorLoginUseCase.consultaAgendamentoPorLogin(any()));
    }


    @Test
    void consultaListaAgendamentosPorLoginRealizadaSucesso(){
        //arrange
        List<AgendamentoEntity> agendamentoEntityList = new ArrayList<>();

        agendamentoEntityList.add(
                AgendamentoEntity.builder()
                        .id(1L)
                        .dataConsulta(LocalDateTime.of(2025,10, 30, 11, 0, 0))
                        .login("maria")
                        .documentoPaciente("32143123423")
                        .emailPaciente("maria@teste.com.br")
                        .nomePaciente("Maria Da Silva")
                        .situacao("AGENDADO")
                        .build()
        );

        List<AgendamentoDtoResponse> agendamentoDtoResponseList = new ArrayList<>();

        agendamentoDtoResponseList.add(
                AgendamentoDtoResponse.builder()
                        .id(1L)
                        .dataConsulta(LocalDateTime.of(2025,10, 30, 11, 0, 0))
                        .login("maria")
                        .documentoPaciente("32143123423")
                        .emailPaciente("maria@teste.com.br")
                        .nomePaciente("Maria Da Silva")
                        .situacao("AGENDADO")
                        .build()
        );

        when(agendamentoRepositoryPortOut.findByLogin(any())).thenReturn(Optional.of(agendamentoEntityList));

        //act
        var retorno = consultaAgendamentoPorLoginUseCase.consultaAgendamentoPorLogin(any());

        assertEquals(retorno.size(), agendamentoDtoResponseList.size());
        assertEquals(retorno.get(0).getId(), agendamentoDtoResponseList.get(0).getId());
        assertEquals(retorno.get(0).getLogin(), agendamentoDtoResponseList.get(0).getLogin());
        assertEquals(retorno.get(0).getNomePaciente(), agendamentoDtoResponseList.get(0).getNomePaciente());
        assertEquals(retorno.get(0).getDataConsulta(), agendamentoDtoResponseList.get(0).getDataConsulta());
        assertEquals(retorno.get(0).getDocumentoPaciente(), agendamentoDtoResponseList.get(0).getDocumentoPaciente());
        assertEquals(retorno.get(0).getSituacao(), agendamentoDtoResponseList.get(0).getSituacao());
        assertEquals(retorno.get(0).getEmailPaciente(), agendamentoDtoResponseList.get(0).getEmailPaciente());

    }

}
