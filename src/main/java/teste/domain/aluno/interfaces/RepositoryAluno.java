package teste.domain.aluno.interfaces;

import java.util.List;

import teste.domain.aluno.Aluno;

public interface RepositoryAluno {

   public List<Aluno> listarTodos() throws Exception;

   public Aluno buscarPorMatricula(String matricula) throws Exception;

   public Aluno buscarPorId(int id) throws Exception;

   public void matricular(Aluno aluno) throws Exception;

   public void rematricular(int id) throws Exception;

   public void atualizarCadastroDoAluno(Aluno aluno) throws Exception;

   public void cancelarMatricula(int id) throws Exception;
}
