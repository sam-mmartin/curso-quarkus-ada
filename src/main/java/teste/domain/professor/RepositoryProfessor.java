package teste.domain.professor;

import java.util.List;

public interface RepositoryProfessor {

   public List<Professor> listarTodos() throws Exception;

   public Professor buscarPorMatricula(String matricula) throws Exception;

   public Professor buscarPorId(int id) throws Exception;

   public void contratar(Professor professor) throws Exception;

   public void atualizarCadastroDoProfessor(Professor professor) throws Exception;

   public void demitir(Professor professor) throws Exception;
}
