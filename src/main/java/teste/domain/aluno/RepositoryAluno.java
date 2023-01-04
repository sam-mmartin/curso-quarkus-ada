package teste.domain.aluno;

import java.util.List;

public interface RepositoryAluno {

   public List<Aluno> listarTodos() throws Exception;

   public Aluno buscarPorMatricula(String matricula) throws Exception;

   public Aluno buscarPorId(int id) throws Exception;

   public List<Aluno> listarAlunosPorCurso(int idCurso) throws Exception;

   public Aluno matricular(Aluno aluno) throws Exception;

   public Aluno rematricular(Aluno aluno) throws Exception;

   public Aluno atualizarCadastroDoAluno(Aluno aluno) throws Exception;

   public Aluno cancelarMatricula(Aluno aluno) throws Exception;
}
