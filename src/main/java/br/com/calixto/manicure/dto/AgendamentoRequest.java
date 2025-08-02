package br.com.calixto.manicure.dto;

import java.time.LocalDate;

public record AgendamentoRequest (Long id, ClienteRequest cliente, LocalDate data, String hora, String servico ){
}
