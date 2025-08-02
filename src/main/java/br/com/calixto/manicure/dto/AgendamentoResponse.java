package br.com.calixto.manicure.dto;

import br.com.calixto.manicure.entity.Agendamento;
import br.com.calixto.manicure.entity.TipoServico;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoResponse(
        Long id,
        ClienteResponse cliente,
        LocalDate data,
        LocalTime hora,
        TipoServico servico
) {


    public static AgendamentoResponse fromEntity(Agendamento agendamento) {

        return new AgendamentoResponse(
                    agendamento.getId(),
                    ClienteResponse.fromEntity(agendamento.getCliente()), //Convertendo dto na entidade
                    agendamento.getData(),
                    agendamento.getHora(),
                    agendamento.getServico()
        );
    }
}
