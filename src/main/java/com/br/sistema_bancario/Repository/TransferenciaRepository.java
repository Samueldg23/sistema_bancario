package com.br.sistema_bancario.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.br.sistema_bancario.entity.Conta;
import com.br.sistema_bancario.entity.Transferencia;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
    List<Transferencia> findByContaOrigemOrContaDestino(Conta contaOrigem, Conta contaDestino);
    List<Transferencia> findByContaOrigem_ContaIdOrContaDestino_ContaId(Long contaOrigemId, Long contaDestinoId);
    List<Transferencia> findAll();
}

