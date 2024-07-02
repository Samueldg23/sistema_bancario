package com.br.sistema_bancario.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "conta")
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contaId;

    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    private double saldo;

    public void depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
        } else {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo");
        }
    }

    public void debitar(double valor) {
        if (valor > 0 && valor <= this.saldo) {
            this.saldo -= valor;
        } else {
            throw new IllegalArgumentException("Saldo insuficiente ou valor inválido");
        }
    }
}
