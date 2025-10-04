package br.com.postech.ms_agendamento.domain.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void deveRetornarNoContentParaIdNaoExisteException() {
        IdNaoExisteException ex = new IdNaoExisteException(1L);
        ResponseEntity<String> response = handler.idNaoExiste(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deveRetornarNoContentParaRegistroNaoEncontradoException() {
        RegistroNaoEncontradoException ex = new RegistroNaoEncontradoException();
        ResponseEntity<String> response = handler.registroNaoEncontrado(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deveRetornarBadRequestParaSituacaoNaoPermiteEdicaoException() {
        SituacaoNaoPermiteEdicaoException ex = new SituacaoNaoPermiteEdicaoException("CANCELADO");
        ResponseEntity<String> response = handler.registroNaoEncontrado(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
