package com.br.sistema_bancario.entity;
/*
import java.time.Instant;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;*/

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer clienteId;
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    @Column(name = "email", nullable = false, length = 130, unique = true)
    private String email;
    @Column(name = "sexo", nullable = false, length = 20)
    private String sexo;
    @Column(name = "municipio", nullable = false, length = 100)
    private String municipio;
    @Column(name = "senha", nullable = false, length = 20)
    private String senha;
    @Column(name = "tipo_conta", length = 255)
    private String tipoConta;
    @JsonIgnore
    @OneToOne(mappedBy = "cliente")
    private Conta conta;
    /*
    @GeneratedValue
    @CreationTimestamp
    private Instant dataCriacao;
    @GeneratedValue
    @UpdateTimestamp
    private Instant dataAtualizacao;*/
}
