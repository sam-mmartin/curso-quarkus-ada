package teste.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;
import teste.domain.aluno.Aluno;
import teste.domain.curso.Curso;
import teste.infrastructure.aluno.AlunoRepositoryJDBC;
import teste.infrastructure.curso.CursoRepositoryJDBC;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlunoRepositoryTest {

   private static final String NOME = "Unknow 1";
   private static final String CPF = "384.777.090-09";
   private static final Curso curso = new Curso("CURSO 1");
   private static final Aluno entity_1 = new Aluno("Unknow 1", "384.777.090-09", curso);
   private static final Aluno entity_2 = new Aluno("Unknow 1", "384.777.090-09", curso);
   private static final String[] parameters = { NOME, CPF, curso.getNomeDoCurso() };

   @Inject
   AlunoRepositoryJDBC repositorio;

   @Inject
   CursoRepositoryJDBC cursoRepository;

   @Test
   @Order(1)
   @Transactional
   public void listarTodos() {
      try {
         curso.setObservacao("Curso implantado");
         cursoRepository.persist(curso);

         entity_1.setCursoMatriculado(curso);
         entity_2.setCursoMatriculado(curso);
         saveToRepository(repositorio, entity_1, entity_2);

         var actual = repositorio.listarTodos();

         assertEquals(2, actual.size());
         executeAssertions(parameters, actual.get(0));
         executeAssertions(parameters, actual.get(1));
      } catch (Exception e) {
         assertTrue(e instanceof PersistenceException);
      }
   }

   @Test
   @Order(2)
   public void buscarPorId() {
      try {
         var actual = repositorio.buscarPorId(1);

         executeAssertions(parameters, actual);
         assertTrue(actual.isEstado());
      } catch (Exception e) {
         assertTrue(e instanceof PersistenceException);
      }
   }

   @Test
   @Order(3)
   public void listarAlunosPorCurso() {
      try {
         var actual = repositorio.listarAlunosPorCurso(1);

         assertEquals(2, actual.size());
         assertTrue(actual.get(0).isEstado());
         executeAssertions(parameters, actual.get(0));
         executeAssertions(parameters, actual.get(1));
      } catch (Exception e) {
         assertTrue(e instanceof PersistenceException);
      }
   }

   @Test
   @Order(4)
   @Transactional
   public void cancelarMatricula() {
      try {
         var actual = repositorio.buscarPorId(1);
         repositorio.cancelarMatricula(actual);
         actual = repositorio.buscarPorId(1);

         assertFalse(actual.isEstado());
         executeAssertions(parameters, actual);
      } catch (Exception e) {
         assertTrue(e instanceof PersistenceException);
      }
   }

   @Test
   @Order(5)
   @Transactional
   public void rematricular() {
      try {
         var actual = repositorio.buscarPorId(1);
         repositorio.rematricular(actual);
         actual = repositorio.buscarPorId(1);

         assertTrue(actual.isEstado());
         executeAssertions(parameters, actual);
      } catch (Exception e) {
         assertTrue(e instanceof PersistenceException);
      }
   }

   private void executeAssertions(String[] parameters, Aluno aluno) {
      assertEquals(parameters[0], aluno.getNome());
      assertEquals(parameters[1], aluno.getCpf());
      assertEquals(parameters[2], aluno.getCursoMatriculado().getNomeDoCurso());
   }

   private void saveToRepository(AlunoRepositoryJDBC repositorio, Aluno... entities) throws Exception {
      for (Aluno entity : entities) {
         repositorio.matricular(entity);
      }
   }

}
