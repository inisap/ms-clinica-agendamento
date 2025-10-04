package br.com.postech.ms_agendamento.domain.exceptions;

public class IdNaoExisteException extends RuntimeException {

    public IdNaoExisteException(Long id) {
        super (String.format("Id de consulta nao existe: [%s]",id));
    }
}
