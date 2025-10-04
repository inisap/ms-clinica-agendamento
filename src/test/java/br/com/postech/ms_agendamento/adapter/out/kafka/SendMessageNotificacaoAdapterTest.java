package br.com.postech.ms_agendamento.adapter.out.kafka;

import br.com.postech.ms_agendamento.adapter.out.kafka.dto.MessageAgendamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SendMessageNotificacaoAdapterTest {

    @Mock
    private KafkaTemplate<String, MessageAgendamento> kafkaTemplate;

    @InjectMocks
    private SendMessageNotificacaoAdapter sendMessageNotificacaoAdapter;

    @Test
    void enviaNotificacaoComSucesso(){
        //arrange
        MessageAgendamento msg = MessageAgendamento.builder()
                .idConsulta(1L)
                .nomePaciente("Maria da Silva")
                .statusAgendamento("AGENDADO")
                .dataAgendamento("2025-10-30- 11:00:00")
                .emailsPaciente("maria@teste.com.br")
                .build();

        sendMessageNotificacaoAdapter.enviarNotificacao(msg);

        verify(kafkaTemplate, times(1)).send("topico-notifica-paciente", msg);

    }
}
