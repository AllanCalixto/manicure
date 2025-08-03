package br.com.calixto.manicure.factory;

import br.com.calixto.manicure.entity.Agendamento;
import br.com.calixto.manicure.entity.Cliente;
import br.com.calixto.manicure.entity.TipoServico;
import ch.qos.logback.core.net.server.Client;

import java.time.LocalDate;
import java.time.LocalTime;

public class AgendamentoMockFatory {

    public Cliente criarCliente(Long id) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome("Allan Calixto");
        cliente.setTelefone("8599999999");
        return cliente;
    }

    public Cliente criarNovoCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Carol");
        cliente.setTelefone("8597777777");
        return cliente;
    }

    public Agendamento criarAgendamento(Long id) {
        Cliente cliente = criarCliente(id);
        Agendamento agendamento = new Agendamento();
        agendamento.setId(id);
        agendamento.setCliente(cliente);
        agendamento.setData(LocalDate.of(2025, 8, 3));
        agendamento.setHora(LocalTime.of(14, 0));
        agendamento.setServico(TipoServico.MAO_E_PE);
        return agendamento;
    }

    public Agendamento criarAgendamentoComClienteNovo() {
        Cliente cliente = criarNovoCliente();
        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setData(LocalDate.now());
        agendamento.setHora(LocalTime.of(10, 0));
        agendamento.setServico(TipoServico.PE);
        return agendamento;
    }
}
