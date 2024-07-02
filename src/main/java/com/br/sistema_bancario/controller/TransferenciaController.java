package com.br.sistema_bancario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.sistema_bancario.Repository.ContaRepository;
import com.br.sistema_bancario.dto.TransferenciaDTO;
import com.br.sistema_bancario.entity.Conta;
import com.br.sistema_bancario.entity.Transferencia;
import com.br.sistema_bancario.service.TransferenciaService;

@RestController
@RequestMapping("/cb")
public class TransferenciaController {
    private TransferenciaService transferenciaService;
      private ContaRepository contaRepository;

    public TransferenciaController(TransferenciaService transferenciaService, ContaRepository contaRepository) {
        this.transferenciaService = transferenciaService;
        this.contaRepository = contaRepository;
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Transferencia> realizarTransferencia(@RequestBody TransferenciaDTO transferenciaDTO) {
        var transferencia = transferenciaService.realizarTransferencia(
                transferenciaDTO.contaOrigemId(),
                transferenciaDTO.contaDestinoId(),
                transferenciaDTO.valor());
                
        return ResponseEntity.ok(transferencia);
    }

    @GetMapping("/conta/{contaId}/transferencias")
    public ResponseEntity<List<Transferencia>> listarTransferenciasPorConta(@PathVariable Long contaId) {
        Conta conta = contaRepository.findById(contaId)
                                     .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        List<Transferencia> transferencias = transferenciaService.listarTransferenciasPorConta(conta);
        return ResponseEntity.ok(transferencias);
    }

    @GetMapping("/transferencias/admin")
    public ResponseEntity<List<Transferencia>> listarTodasAsTransferencias() {
        List<Transferencia> transferencias = transferenciaService.listarTodasAsTransferencias();
        return ResponseEntity.ok(transferencias);
    }
}
