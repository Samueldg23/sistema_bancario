package com.br.sistema_bancario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.br.sistema_bancario.Repository.ContaRepository;
import com.br.sistema_bancario.entity.Cliente;
import com.br.sistema_bancario.entity.Conta;

@Service
public class ContaService {
    private final ContaRepository contaRepo;

    public ContaService(ContaRepository contaRepo) {
        this.contaRepo = contaRepo;
    }

    public List<Conta> listarContas() {
        return contaRepo.findAll();
    }

    public Conta depositar(Long contaId, double valor) {
        Optional<Conta> contaOpt = contaRepo.findById(contaId);
        if (contaOpt.isPresent()) {
            Conta conta = contaOpt.get();
            conta.depositar(valor);
            return contaRepo.save(conta);
        } else {
            throw new IllegalArgumentException("Conta não encontrada");
        }
    }

    public Conta debitar(Long contaId, double valor) {
        Optional<Conta> contaOpt = contaRepo.findById(contaId);
        if (contaOpt.isPresent()) {
            Conta conta = contaOpt.get();
            conta.debitar(valor);
            return contaRepo.save(conta);
        } else {
            throw new IllegalArgumentException("Conta não encontrada");
        }
    }

    public double verificarSaldo(Long contaId) {
        Optional<Conta> contaOpt = contaRepo.findById(contaId);
        if (contaOpt.isPresent()) {
            return contaOpt.get().getSaldo();
        } else {
            throw new IllegalArgumentException("Conta não encontrada");
        }
    }

    public Conta buscarContaComCliente(Long contaId) {
        Optional<Conta> contaOpt = contaRepo.findById(contaId);
        if (contaOpt.isPresent()) {
            return contaOpt.get();
        } else {
            throw new IllegalArgumentException("Conta não encontrada");
        }
    }
    public void deletarContaPorCliente(Cliente cliente) {
        Conta conta = contaRepo.findById(cliente.getConta().getContaId())
            .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        contaRepo.delete(conta);
    }
}
