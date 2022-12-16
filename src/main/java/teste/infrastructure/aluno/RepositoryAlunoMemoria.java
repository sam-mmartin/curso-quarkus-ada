package teste.infrastructure.aluno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teste.domain.aluno.Aluno;
import teste.domain.aluno.interfaces.RepositoryAluno;

public class RepositoryAlunoMemoria implements RepositoryAluno {

   private final Map<Integer, Aluno> mapAlunos = new HashMap<>();

   @Override
   public List<Aluno> listarTodos() {
      return new ArrayList<>(mapAlunos.values());
   }

   @Override
   public Aluno buscarPorMatricula(int id) {
      return mapAlunos.get(id);
   }

   @Override
   public void matricular(Aluno aluno) {
      mapAlunos.put(aluno.getId(), aluno);
   }

   @Override
   public void rematricular(int id, Aluno aluno) {
      update(id, aluno);
   }

   @Override
   public void atualizarCadastroDoAluno(int id, Aluno aluno) {
      update(id, aluno);
   }

   @Override
   public void cancelarMatricula(int id) {
      Aluno aluno = mapAlunos.get(id);
      aluno.setStatus(false);
      update(id, aluno);
   }

   private void update(int id, Aluno aluno) {
      mapAlunos.put(id, aluno);
   }
}
