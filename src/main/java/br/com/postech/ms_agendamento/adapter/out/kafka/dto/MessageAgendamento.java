package br.com.postech.ms_agendamento.adapter.out.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageAgendamento {

    private Long idConsulta;
    private String emailsPaciente;
    private String dataAgendamento;
    private String nomePaciente;
    private String statusAgendamento;

}
