package br.com.postech.ms_agendamento.domain.utils;

import java.util.HashMap;
import java.util.Map;

public class SituacoesAgendamento {

    private static final Map<String, Boolean> situacoes = new HashMap<>();

    public final String situacao;
    public final Boolean permiteEdicao;

    static {
        situacoes.put("AGENDADO", true);
        situacoes.put("CANCELADO", false);
        situacoes.put("CONCLUIDO", false);
    }

    public SituacoesAgendamento(String situacao, Boolean permiteEdicao) {
        this.situacao = situacao;
        this.permiteEdicao = permiteEdicao;
    }

    public static Boolean getSituacao(String chave){
        return situacoes.get(chave);
    }


}
