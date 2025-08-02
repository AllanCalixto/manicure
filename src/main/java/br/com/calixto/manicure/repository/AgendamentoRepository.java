package br.com.calixto.manicure.repository;

import br.com.calixto.manicure.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByData(LocalDate data);

    Collection<Object> findByDataAndHora(LocalDate data, LocalTime hora);
}
