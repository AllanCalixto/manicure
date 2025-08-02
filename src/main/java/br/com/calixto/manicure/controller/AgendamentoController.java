package br.com.calixto.manicure.controller;

import br.com.calixto.manicure.dto.AgendamentoRequest;
import br.com.calixto.manicure.dto.AgendamentoResponse;
import br.com.calixto.manicure.entity.Agendamento;
import br.com.calixto.manicure.service.IAgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/agendamentos")
public class AgendamentoController {

    @Autowired
    private IAgendamentoService agendamentoService;

    @GetMapping
    public ResponseEntity<List<AgendamentoResponse>> listarTodos() {
        List<AgendamentoResponse> agendamentos = agendamentoService.listarTodosAgendamentos()
                .stream()
                .map(AgendamentoResponse::fromEntity)
                .collect(Collectors.toList());
                return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/data")
    public ResponseEntity<List<AgendamentoResponse>> listarPorData(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<AgendamentoResponse> agendamentosPorData = agendamentoService.listarAgendamentoPorData(data)
                .stream()
                .map(AgendamentoResponse::fromEntity)
                .collect(Collectors.toList());
                return ResponseEntity.ok(agendamentosPorData);
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponse> criarNovoAgendamento(@RequestBody AgendamentoRequest request) {
        Agendamento agendamento = agendamentoService.criarNovoAgendamento(
                request.cliente().id(),
                request.cliente().nome(),
                request.cliente().telefone(),
                request.data(),
                request.hora(),
                request.servico()
        );
        return ResponseEntity.ok(AgendamentoResponse.fromEntity(agendamento));
    }

}
