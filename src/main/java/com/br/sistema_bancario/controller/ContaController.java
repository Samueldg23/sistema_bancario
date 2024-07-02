package com.br.sistema_bancario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.br.sistema_bancario.entity.Conta;
import com.br.sistema_bancario.service.ContaService;

@RestController
@RequestMapping("/cb/contas")
public class ContaController {
    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping
    public ResponseEntity<List<Conta>> listarTodasAsContas() {
        List<Conta> contas = contaService.listarContas();
        return ResponseEntity.ok(contas);
    }

    @PostMapping("/depositar/{contaId}")
    public ResponseEntity<Conta> depositar(@PathVariable Long contaId, @RequestParam("valor") double valor) {
        Conta conta = contaService.depositar(contaId, valor);
        return ResponseEntity.ok(conta);
    }

    @PostMapping("/{contaId}/debitar")
    public ResponseEntity<Conta> debitar(@PathVariable Long contaId, @RequestParam double valor) {
        Conta conta = contaService.debitar(contaId, valor);
        return ResponseEntity.ok(conta);
    }

    @GetMapping("/{contaId}/saldo")
    public ResponseEntity<Double> verificarSaldo(@PathVariable Long contaId) {
        double saldo = contaService.verificarSaldo(contaId);
        return ResponseEntity.ok(saldo);
    }

    @GetMapping("/{contaId}/dados")
    public ResponseEntity<Conta> buscarContaComCliente(@PathVariable Long contaId) {
        Conta conta = contaService.buscarContaComCliente(contaId);
        return ResponseEntity.ok(conta);
    }
}
