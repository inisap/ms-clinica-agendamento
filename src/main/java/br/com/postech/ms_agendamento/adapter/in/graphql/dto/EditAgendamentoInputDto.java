package br.com.postech.ms_agendamento.adapter.in.graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@SchemaMapping("EditAgendamentoInput")
public class EditAgendamentoInputDto {
    private Long id;
    private String documentoPaciente;
    private String nomePaciente;
    private LocalDateTime dataConsulta;
    private String situacao;
    private String emailPaciente;
    private String login;
}
