package br.univille.microservcolegio.secretaria.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;

@Container(containerName = "aluno", autoCreateContainer = true)
public class Aluno {
    @Id
    @PartitionKey
    @GeneratedValue
    private UUID id;
    private String nome;
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    
    
}
