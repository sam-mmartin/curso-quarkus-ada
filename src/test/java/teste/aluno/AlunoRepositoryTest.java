package teste.aluno;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;
import teste.domain.aluno.Aluno;
import teste.domain.curso.Curso;
import teste.infrastructure.aluno.AlunoRepositoryJDBC;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlunoRepositoryTest {

   private static final String NOME = "Juliana Cintra";
   private static final String CPF = "546.212.310-81";
   private static final String MATRICULA = "1010-100";
   private static final Curso curso = new Curso("ANÁLISES QUÍMICA");
   private static final Aluno entity_1 = new Aluno("Unknow 1", "384.777.090-09", curso);
   private static final Aluno entity_2 = new Aluno("Unknow 1", "384.777.090-09", curso);

   @Inject
   AlunoRepositoryJDBC repositorio;

   @Test
   @Order(1)
   @Transactional
   public void listarTodos() {
      try {
         curso.setId(1);
         curso.setObservacao("Curso implantado");
         entity_1.setCursoMatriculado(curso);
         entity_2.setCursoMatriculado(curso);
         saveToRepository(repositorio, entity_1, entity_2);
         var actual = repositorio.listarTodos();

         Assertions.assertTrue(actual.size() == 5);
         executeAssertions(actual.get(3));
         executeAssertions(actual.get(4));
      } catch (Exception e) {
         Assertions.assertTrue(e instanceof PersistenceException);
      }
   }

   @Test
   @Order(2)
   public void buscarPorId() {
      try {
         var actual = repositorio.buscarPorId(1);
         String[] parameters = { NOME, CPF, MATRICULA, curso.getNomeDoCurso() };

         executeAssertionsFromBase(parameters, actual);
         Assertions.assertTrue(actual.isEstado());
      } catch (Exception e) {
         Assertions.assertTrue(e instanceof PersistenceException);
      }
   }

   @Test
   @Order(3)
   public void listarAlunosPorCurso() {
      try {
         var actual = repositorio.listarAlunosPorCurso(1);
         String[] parameters = { NOME, CPF, MATRICULA, curso.getNomeDoCurso() };

         Assertions.assertTrue(actual.size() == 3);
         Assertions.assertTrue(actual.get(0).isEstado());
         executeAssertionsFromBase(parameters, actual.get(0));
      } catch (Exception e) {
         Assertions.assertTrue(e instanceof PersistenceException);
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

         Assertions.assertFalse(actual.isEstado());

         String[] parameters = { NOME, CPF, MATRICULA, curso.getNomeDoCurso() };
         executeAssertionsFromBase(parameters, actual);
      } catch (Exception e) {
         Assertions.assertTrue(e instanceof PersistenceException);
      }
   }

   @Test
   @Order(5)
   @Transactional
   public void rematricular() {
      try {
         var actual = repositorio.buscarPorId(3);
         repositorio.rematricular(actual);
         actual = repositorio.buscarPorId(3);

         Assertions.assertTrue(actual.isEstado());

         String[] parameters = { "Thays Soares", CPF, "1010-300", curso.getNomeDoCurso() };
         executeAssertionsFromBase(parameters, actual);
      } catch (Exception e) {
         Assertions.assertTrue(e instanceof PersistenceException);
      }
   }

   private void executeAssertions(Aluno aluno) {
      Assertions.assertEquals("Unknow 1", aluno.getNome());
      Assertions.assertEquals("384.777.090-09", aluno.getCpf());
      Assertions.assertTrue(aluno.isEstado());
      Assertions.assertEquals(curso.getNomeDoCurso(), aluno.getCursoMatriculado().getNomeDoCurso());
   }

   private void executeAssertionsFromBase(String[] parameters, Aluno aluno) {
      Assertions.assertEquals(parameters[0], aluno.getNome());
      Assertions.assertEquals(parameters[1], aluno.getCpf());
      Assertions.assertEquals(parameters[2], aluno.getMatricula().getNumero());
      Assertions.assertEquals(parameters[3], aluno.getCursoMatriculado().getNomeDoCurso());
   }

   private void saveToRepository(AlunoRepositoryJDBC repositorio, Aluno... entities) throws Exception {
      for (Aluno entity : entities) {
         repositorio.matricular(entity);
      }
   }

}
