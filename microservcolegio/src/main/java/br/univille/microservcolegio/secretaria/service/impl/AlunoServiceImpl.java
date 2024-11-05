package br.univille.microservcolegio.secretaria.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.univille.microservcolegio.secretaria.entity.Aluno;
import br.univille.microservcolegio.secretaria.repository.AlunoRepository;
import br.univille.microservcolegio.secretaria.service.AlunoService;

@Service
public class AlunoServiceImpl implements AlunoService {

    @Autowired
    private AlunoRepository repository;
    
    @Override
    public List<Aluno> getAll() {
        var retornoIterador = repository.findAll();
        var listaAlunos = new ArrayList<Aluno>();
        
        retornoIterador.forEach(listaAlunos::add);
        return listaAlunos;
    }

    @Override
    public Aluno save(Aluno aluno) {
        return repository.save(aluno);
    }
    
}
