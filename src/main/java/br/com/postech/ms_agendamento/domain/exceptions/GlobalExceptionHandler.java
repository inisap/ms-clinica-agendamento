package br.com.postech.ms_agendamento.domain.exceptions;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @GraphQlExceptionHandler(IdNaoExisteException.class)
    public Mono<GraphQLError> handleIdNaoExiste(IdNaoExisteException ex) {
        GraphQLError error = GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .errorType(graphql.ErrorType.DataFetchingException) // similar ao NOT_FOUND
                .build();
        return Mono.just(error);
    }

    @GraphQlExceptionHandler(RegistroNaoEncontradoException.class)
    public Mono<GraphQLError> handleRegistroNaoEncontrado(RegistroNaoEncontradoException ex) {
        GraphQLError error = GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .errorType(graphql.ErrorType.DataFetchingException)
                .build();
        return Mono.just(error);
    }

    @GraphQlExceptionHandler(SituacaoNaoPermiteEdicaoException.class)
    public Mono<GraphQLError> handleSituacaoNaoPermiteEdicao(SituacaoNaoPermiteEdicaoException ex) {

        System.out.println("Entrou no handler pasini: " + ex.getMessage());

        GraphQLError error = GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .errorType(graphql.ErrorType.ValidationError) // equivalente a BAD_REQUEST
                .build();
        return Mono.just(error);
    }
}
