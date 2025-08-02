package br.com.calixto.manicure.repository;

import br.com.calixto.manicure.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByNomeAndTelefone(String nome, String telefone);

}
