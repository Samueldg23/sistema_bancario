package com.br.sistema_bancario.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.sistema_bancario.dto.AtualizarClienteDTO;
import com.br.sistema_bancario.dto.ClienteDTO;
import com.br.sistema_bancario.entity.Cliente;
import com.br.sistema_bancario.entity.Transferencia;
import com.br.sistema_bancario.service.ClienteService;

@RestController
@RequestMapping("/cb/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.cadastrar(clienteDTO);
        return ResponseEntity.created(URI.create("/cb/clientes/" + cliente.getClienteId())).body(cliente);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> buscarClienteById(@PathVariable("clienteId") Integer clienteId) {
        var cliente = clienteService.buscarClienteById(clienteId);

        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Cliente>> listarClientes() {
        var clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> deleteById(@PathVariable("clienteId") Integer clienteId) {
        clienteService.deleteById(clienteId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<Void> atualizarClienteById(@PathVariable("clienteId") Integer clienteId,
                                                     @RequestBody AtualizarClienteDTO atualizarClienteDto) {
        clienteService.atualizarCliente(clienteId, atualizarClienteDto);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{clienteId}/transferencias")
    public ResponseEntity<List<Transferencia>> consultarTransferenciasCliente(@PathVariable Integer clienteId) {
        List<Transferencia> transferencias = clienteService.consultarTransferenciasCliente(clienteId);
        return ResponseEntity.ok(transferencias);
    }
}
