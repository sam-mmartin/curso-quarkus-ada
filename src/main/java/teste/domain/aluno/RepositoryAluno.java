package teste.domain.aluno;

import java.util.List;

public interface RepositoryAluno {

   public List<Aluno> listarTodos() throws Exception;

   public Aluno buscarPorMatricula(String matricula) throws Exception;

   public Aluno buscarPorId(int id) throws Exception;

   public List<Aluno> listarAlunosPorCurso(int idCurso) throws Exception;

   public void matricular(Aluno aluno) throws Exception;

   public void rematricular(Aluno aluno) throws Exception;

   public void atualizarCadastroDoAluno(Aluno aluno) throws Exception;

   public void cancelarMatricula(Aluno aluno) throws Exception;
}
