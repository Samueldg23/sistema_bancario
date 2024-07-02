package com.br.sistema_bancario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.sistema_bancario.Repository.ClienteRepository;
import com.br.sistema_bancario.Repository.ContaRepository;
import com.br.sistema_bancario.Repository.TransferenciaRepository;
import com.br.sistema_bancario.dto.AtualizarClienteDTO;
import com.br.sistema_bancario.dto.ClienteDTO;
import com.br.sistema_bancario.entity.Cliente;
import com.br.sistema_bancario.entity.Conta;
import com.br.sistema_bancario.entity.Transferencia;

@Service
public class ClienteService {
    private ClienteRepository repo;
    private ContaRepository contaRepo;
    private TransferenciaRepository trans;

    public ClienteService(ClienteRepository repo, ContaRepository contaRepo, TransferenciaRepository trans) {
        this.repo = repo;
        this.contaRepo = contaRepo;
        this.trans = trans;
    }

    public Cliente cadastrar(ClienteDTO clienteDTO) {
        // Transformando o DTO para uma entidade
        Cliente entity = new Cliente(
                null,
                clienteDTO.nome(),
                clienteDTO.email(),
                clienteDTO.sexo(),
                clienteDTO.municipio(),
                clienteDTO.senha(),
                clienteDTO.tipoConta(),
                null
        );
        Cliente clienteSave = repo.save(entity);

        Conta contaNova = new Conta(null, clienteSave, 0.0);
        Conta contaSave = contaRepo.save(contaNova);
        
        clienteSave.setConta(contaSave);
        return repo.save(clienteSave);
    }

    public Optional<Cliente> buscarClienteById(Integer clienteId) {
        return repo.findById(clienteId);
    }

    public List<Cliente> listarClientes() {
        return repo.findAll();
    }

    public void atualizarCliente(Integer clienteId, AtualizarClienteDTO atualizarClienteDto) {
        var clienteEntity = repo.findById(clienteId);
        if (clienteEntity.isPresent()) {
            var cliente = clienteEntity.get();
            if (atualizarClienteDto != null) {
                cliente.setNome(atualizarClienteDto.nome());
                cliente.setEmail(atualizarClienteDto.email());
                cliente.setMunicipio(atualizarClienteDto.municipio());
                cliente.setSenha(atualizarClienteDto.senha());
                repo.save(cliente);
            }
        }
    }

    public void deleteById(Integer clienteId) {
        Optional<Cliente> clienteOpt = repo.findById(clienteId);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            if (cliente.getConta() != null) {
                contaRepo.delete(cliente.getConta());
            }
            repo.deleteById(clienteId);
        }
    }

    public List<Transferencia> consultarTransferenciasCliente(Integer clienteId) {
        Optional<Cliente> clienteOpt = repo.findById(clienteId);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            Conta conta = cliente.getConta();
            if (conta != null) {
                return trans.findByContaOrigem_ContaIdOrContaDestino_ContaId(conta.getContaId(), conta.getContaId());
            } else {
                throw new IllegalArgumentException("Cliente não possui uma conta associada");
            }
        } else {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
    }
}
