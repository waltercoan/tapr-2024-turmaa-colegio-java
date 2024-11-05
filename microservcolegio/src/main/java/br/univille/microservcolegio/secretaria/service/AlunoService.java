package br.univille.microservcolegio.secretaria.service;

import java.util.List;

import br.univille.microservcolegio.secretaria.entity.Aluno;

public interface AlunoService {
    List<Aluno> getAll();
    Aluno save(Aluno aluno);
}
