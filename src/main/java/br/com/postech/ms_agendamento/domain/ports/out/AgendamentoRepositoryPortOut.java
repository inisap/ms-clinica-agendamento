package br.com.postech.ms_agendamento.domain.ports.out;

import br.com.postech.ms_agendamento.adapter.out.persistence.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgendamentoRepositoryPortOut extends JpaRepository<AgendamentoEntity, Long> {

    Optional<List<AgendamentoEntity>> findByLogin(String login);
    Optional<AgendamentoEntity> findById(Long id);
}
