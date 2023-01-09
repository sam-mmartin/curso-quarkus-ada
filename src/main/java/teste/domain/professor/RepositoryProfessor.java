package teste.domain.professor;

import java.util.List;

public interface RepositoryProfessor {

   public List<Professor> listarTodos() throws Exception;

   public Professor buscarPorMatricula(String matricula);

   public Professor buscarPorId(int id) throws Exception;

   public Professor contratar(Professor professor) throws Exception;

   public Professor atualizarProfessor(Professor professor) throws Exception;

   public void demitir(Professor professor) throws Exception;
}
