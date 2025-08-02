package br.com.calixto.manicure.dto;

import br.com.calixto.manicure.entity.Cliente;

public record ClienteResponse(Long id, String nome, String telefone ) {

    public static ClienteResponse fromEntity(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone()
        );
    }
}
