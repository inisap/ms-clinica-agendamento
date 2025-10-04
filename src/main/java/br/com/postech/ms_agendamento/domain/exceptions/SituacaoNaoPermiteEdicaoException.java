package br.com.postech.ms_agendamento.domain.exceptions;

public class SituacaoNaoPermiteEdicaoException extends RuntimeException {

    public SituacaoNaoPermiteEdicaoException(String situacao) {
        super(String.format("Agendamento nao pode ser alterado devido a situacao: [%s]", situacao));
    }
}
