package teste.domain.professor;

import java.util.List;

import teste.domain.curso.Curso;
import teste.domain.disciplina.Disciplina;

public interface RepositoryProfessor {

   public List<Professor> listarTodos() throws Exception;

   public Professor buscarPorMatricula(String matricula) throws Exception;

   public Professor buscarPorId(int id) throws Exception;

   public void contratar(Professor professor) throws Exception;

   public void atualizarCadastroDoProfessor(Professor professor) throws Exception;

   public void demitir(Professor professor) throws Exception;

   public void lecionarDisciplina(int id, Disciplina disciplina) throws Exception;

   public void pararDeLecionarDisciplina(int id, Disciplina disciplina) throws Exception;

   public void adicionarCurso(int id, Curso curso) throws Exception;

   public void removerCurso(int id, Curso curso) throws Exception;
}
