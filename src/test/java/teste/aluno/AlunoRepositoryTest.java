package teste.aluno;

import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import teste.domain.aluno.Aluno;
import teste.domain.curso.Curso;
import teste.infrastructure.aluno.AlunoRepositoryJDBC;
import teste.infrastructure.curso.CursoRepositoryJDBC;

@QuarkusTest
public class AlunoRepositoryTest {

   private static final LocalDateTime DATA = LocalDateTime.now();
   private static final Curso curso = new Curso("CURSO 1");
   private static final Aluno entity_1 = new Aluno("Unknow 1", "384.777.090-09", curso);
   private static final Aluno entity_2 = new Aluno("Unknow 2", "384.777.090-09", curso);

   @Inject
   private AlunoRepositoryJDBC repositorio;

   @Inject
   private CursoRepositoryJDBC cursoRepository;

   @Test
   public void listarTodos() {
      try {
         cursoRepository.persist(curso);
         saveToRepository(repositorio, entity_1, entity_2);
         var actual = repositorio.listarTodos();
         Assertions.assertTrue(actual.size() == 2);
      } catch (Exception e) {
         Assertions.assertTrue(e instanceof PersistenceException);
      }
   }

   private void saveToRepository(AlunoRepositoryJDBC repositorio, Aluno... entities) throws Exception {
      for (Aluno entity : entities) {
         repositorio.matricular(entity);
      }
   }

}
