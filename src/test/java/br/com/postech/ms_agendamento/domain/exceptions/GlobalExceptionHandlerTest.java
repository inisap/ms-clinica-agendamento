package br.com.postech.ms_agendamento.domain.exceptions;

import graphql.GraphQLError;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void deveRetornarErroParaIdNaoExisteException() {
        IdNaoExisteException ex = new IdNaoExisteException(1L);
        Mono<GraphQLError> result = handler.handleIdNaoExiste(ex);
        GraphQLError error = result.block(); // extrai o erro do Mono

        assertThat(error).isNotNull();
        assertThat(error.getMessage()).contains("1");
        assertThat(error.getErrorType().toString()).isEqualTo("DataFetchingException");
    }

    @Test
    void deveRetornarErroParaRegistroNaoEncontradoException() {
        RegistroNaoEncontradoException ex = new RegistroNaoEncontradoException();
        Mono<GraphQLError> result = handler.handleRegistroNaoEncontrado(ex);
        GraphQLError error = result.block();

        assertThat(error).isNotNull();
        assertThat(error.getMessage()).isEqualTo(ex.getMessage());
        assertThat(error.getErrorType().toString()).isEqualTo("DataFetchingException");
    }

    @Test
    void deveRetornarErroParaSituacaoNaoPermiteEdicaoException() {
        SituacaoNaoPermiteEdicaoException ex = new SituacaoNaoPermiteEdicaoException("CANCELADO");
        Mono<GraphQLError> result = handler.handleSituacaoNaoPermiteEdicao(ex);
        GraphQLError error = result.block();

        assertThat(error).isNotNull();
        assertThat(error.getMessage()).contains("CANCELADO");
        assertThat(error.getErrorType().toString()).isEqualTo("ValidationError");
    }
}
