package com.br.sistema_bancario.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.br.sistema_bancario.Repository.ContaRepository;
import com.br.sistema_bancario.Repository.TransferenciaRepository;
import com.br.sistema_bancario.entity.Conta;
import com.br.sistema_bancario.entity.Transferencia;

@Service
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;
    private final ContaRepository contaRepository;

    public TransferenciaService(TransferenciaRepository transferenciaRepository, ContaRepository contaRepository) {
        this.transferenciaRepository = transferenciaRepository;
        this.contaRepository = contaRepository;
    }

    public Transferencia realizarTransferencia(Long origemId, Long destinoId, double valor) {
        Conta origem = contaRepository.findById(origemId)
                                      .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada"));
        Conta destino = contaRepository.findById(destinoId)
                                       .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada"));

        if (origem.getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente para realizar a transferência");
        }

        origem.debitar(valor);
        destino.depositar(valor);

        Transferencia transferencia = new Transferencia(null, origem, destino, valor, LocalDateTime.now());
        return transferenciaRepository.save(transferencia);
    }

    public List<Transferencia> listarTransferenciasPorConta(Conta conta) {
        return transferenciaRepository.findByContaOrigemOrContaDestino(conta, conta);
    }

    public List<Transferencia> listarTodasAsTransferencias() {
        return transferenciaRepository.findAll();
    }
}
