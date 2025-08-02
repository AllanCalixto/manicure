package br.com.calixto.manicure.service;

import br.com.calixto.manicure.entity.Agendamento;
import br.com.calixto.manicure.entity.Cliente;
import br.com.calixto.manicure.entity.TipoServico;
import br.com.calixto.manicure.repository.AgendamentoRepository;
import br.com.calixto.manicure.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AgendamentoServiceImpl implements IAgendamentoService{

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Agendamento> listarTodosAgendamentos() {
        return agendamentoRepository.findAll();
    }

    @Override
    public List<Agendamento> listarAgendamentoPorData(LocalDate data) {
        return agendamentoRepository.findByData(data);
    }

    @Override
    public boolean horarioDisponivel(LocalDate data, LocalTime hora) {
        return agendamentoRepository.findByDataAndHora(data, hora).isEmpty();
    }

    @Override
    public Agendamento criarNovoAgendamento(String nome, String telefone, LocalDate data, LocalTime hora, TipoServico servico) {
        if (!horarioDisponivel(data, hora)) {
            throw new IllegalArgumentException("Horário já está reservado!");
        }
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        clienteRepository.save(cliente);

        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setData(data);
        agendamento.setHora(hora);
        agendamento.setServico(servico);

        return agendamentoRepository.save(agendamento);
    }

    @Override
    public void cancelarAgendamento(Long id) {
        agendamentoRepository.deleteById(id);
    }
}
