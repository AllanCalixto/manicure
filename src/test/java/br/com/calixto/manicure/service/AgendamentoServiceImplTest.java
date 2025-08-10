package br.com.calixto.manicure.service;

import br.com.calixto.manicure.entity.Agendamento;
import br.com.calixto.manicure.entity.Cliente;
import br.com.calixto.manicure.factory.AgendamentoMockFatory;
import br.com.calixto.manicure.repository.AgendamentoRepository;
import br.com.calixto.manicure.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceImplTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private AgendamentoServiceImpl agendamentoService;

    private AgendamentoMockFatory factory = new AgendamentoMockFatory();

    @Test
    void deveListarTodosAgendamentos() {
        List<Agendamento> agendamentos = List.of(
                factory.criarAgendamento(1L),
                factory.criarAgendamento(2L)
        );
        when(agendamentoRepository.findAll()).thenReturn(agendamentos);
        List<Agendamento> resultado = agendamentoService.listarTodosAgendamentos();

        assertEquals(2, resultado.size());
        verify(agendamentoRepository).findAll();
    }

    @Test
    void deveListarAgendamentoPorData() {
        LocalDate data = LocalDate.of(2025, 8, 3);
        List<Agendamento> agendamentos = List.of(
                factory.criarAgendamento(1L),
                factory.criarAgendamento(2L)
        );
        when(agendamentoRepository.findByData(data)).thenReturn(agendamentos);
        List<Agendamento> resultado = agendamentoService.listarAgendamentoPorData(data);

        assertEquals(2, resultado.size());
        verify(agendamentoRepository).findByData(data);
    }

    @Test
    void deveRetornarHorarioDisponivel() {
        LocalDate data = LocalDate.of(2025, 8, 3);
        LocalTime hora = LocalTime.of(14, 0);
        when(agendamentoRepository.findByDataAndHora(data, hora)).thenReturn(List.of());

        boolean resultado = agendamentoService.horarioDisponivel(data, hora);

        assertTrue(resultado);
        verify(agendamentoRepository).findByDataAndHora(data, hora);
    }

    @Test
    void deveRetornarFalseQuandoHorarioOcupado() {
        LocalDate data = LocalDate.of(2025, 8, 3);
        LocalTime hora = LocalTime.of(14, 0);
        when(agendamentoRepository.findByDataAndHora(data, hora))
                .thenReturn(List.of(factory.criarAgendamento(1L)));

        boolean resultado = agendamentoService.horarioDisponivel(data, hora);

        assertFalse(resultado);
        verify(agendamentoRepository).findByDataAndHora(data, hora);
    }

    @Test
    void deveCancelarAgendamento() {
        Long id = 1L;
        doNothing().when(agendamentoRepository).deleteById(id);

        agendamentoService.cancelarAgendamento(id);

        verify(agendamentoRepository).deleteById(id);
    }

    @Test
    void deveCriarNovoAgendamentoQuandoClienteExistir() {
        Long id = 1L;
        Cliente cliente = factory.criarCliente(id);
        LocalDate data = LocalDate.of(2025, 8, 3);
        String hora = "14:00:00";
        String servico = "MAO_E_PE";

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(agendamentoRepository.findByDataAndHora(data, LocalTime.parse(hora))).thenReturn(List.of());
        when(agendamentoRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Agendamento agendamento = agendamentoService.criarNovoAgendamento(id, "ignorar", "ignorar", data, hora, servico);

        assertNotNull(agendamento);
        assertEquals(id, agendamento.getCliente().getId());
        verify(clienteRepository).findById(id);
        verify(agendamentoRepository).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoHorarioNaoDisponivel() {
        Long id = 1L;
        LocalDate data = LocalDate.of(2025, 8, 3);
        String hora = "14:00:00";
        String servico = "MAO_E_PE";

        when(agendamentoRepository.findByDataAndHora(data, LocalTime.parse(hora)))
                .thenReturn(List.of(factory.criarAgendamento(1L)));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            agendamentoService.criarNovoAgendamento(id, "Allan", "123", data, hora, servico);
        });

        assertEquals("Horário não disponível!", ex.getMessage());
    }

}