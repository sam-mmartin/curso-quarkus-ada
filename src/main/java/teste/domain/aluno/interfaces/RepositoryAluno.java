package teste.domain.aluno.interfaces;

import java.util.List;

import teste.domain.aluno.Aluno;

public interface RepositoryAluno {

   public List<Aluno> listarTodos();

   public Aluno buscarPorMatricula(int id);

   public void matricular(Aluno aluno);

   public void rematricular(int id, Aluno aluno);

   public void atualizarCadastroDoAluno(int id, Aluno aluno);

   public void cancelarMatricula(int id);
}
