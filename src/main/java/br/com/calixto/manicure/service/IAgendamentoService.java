package br.com.calixto.manicure.service;

import br.com.calixto.manicure.entity.Agendamento;
import br.com.calixto.manicure.entity.TipoServico;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public interface IAgendamentoService {

    public List<Agendamento> listarTodosAgendamentos();

    public List<Agendamento> listarAgendamentoPorData(LocalDate data);

    public boolean horarioDisponivel(LocalDate data, LocalTime hora);

    public Agendamento criarNovoAgendamento(String nome, String telefone, LocalDate data, LocalTime hora, TipoServico servico);

    public void cancelarAgendamento(Long id);

}
