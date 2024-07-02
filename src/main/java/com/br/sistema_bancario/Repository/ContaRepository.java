package com.br.sistema_bancario.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.br.sistema_bancario.entity.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
}

