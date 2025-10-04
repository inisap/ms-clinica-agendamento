package br.com.postech.ms_agendamento.adapter.out.kafka;

import br.com.postech.ms_agendamento.adapter.out.kafka.dto.MessageAgendamento;
import br.com.postech.ms_agendamento.domain.ports.out.NotificacaoKafkaPorOut;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendMessageNotificacaoAdapter implements NotificacaoKafkaPorOut {

    private final KafkaTemplate<String, MessageAgendamento> kafkaTemplate;

    public SendMessageNotificacaoAdapter(KafkaTemplate<String, MessageAgendamento> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void enviarNotificacao(MessageAgendamento msg){
        kafkaTemplate.send("topico-notifica-paciente", msg);
    }
}
