package br.com.postech.ms_agendamento.adapter.in.graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@SchemaMapping("AddAgendamentoInput")
public class AddAgendamentoInputDto {
    private String documentoPaciente;
    private String nomePaciente;
    private LocalDateTime dataConsulta;
    private String emailPaciente;
    private String login;
}
