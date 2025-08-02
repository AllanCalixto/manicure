package br.com.calixto.manicure.service;

import br.com.calixto.manicure.entity.Agendamento;
import br.com.calixto.manicure.entity.Cliente;
import br.com.calixto.manicure.entity.TipoServico;
import br.com.calixto.manicure.repository.AgendamentoRepository;
import br.com.calixto.manicure.repository.ClienteRepository;
import ch.qos.logback.core.net.server.Client;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void cancelarAgendamento(Long id) {
        agendamentoRepository.deleteById(id);
    }

    @Override
    public Agendamento criarNovoAgendamento(Long id, String nome, String telefone, LocalDate data, String hora, String servico) {
        LocalTime horaConvertida = LocalTime.parse(hora); // converte "14:00:00" para LocalTime
        TipoServico servicoConvertido = TipoServico.valueOf(servico); // converte "MAO_E_PE" para ENUM

        if (!horarioDisponivel(data, horaConvertida)) {
            throw new IllegalArgumentException("Horário não disponível!");
        }
        Cliente cliente;

        if (id != null) {
            // tenta buscar o cliente existente pelo ID
            cliente = clienteRepository.findById(id)
                    .orElseThrow( () -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));
        } else {
            // cria um novo cliente
            cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setTelefone(telefone);
            clienteRepository.save(cliente);
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setData(data);
        agendamento.setHora(horaConvertida);
        agendamento.setServico(servicoConvertido);

        return agendamentoRepository.save(agendamento);
    }
}
