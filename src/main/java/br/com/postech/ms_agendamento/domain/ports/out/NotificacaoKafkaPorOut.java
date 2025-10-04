package br.com.postech.ms_agendamento.domain.ports.out;

import br.com.postech.ms_agendamento.adapter.out.kafka.dto.MessageAgendamento;

public interface NotificacaoKafkaPorOut {

    void enviarNotificacao(MessageAgendamento msg);
}
