package teste.aluno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import teste.domain.aluno.Aluno;
import teste.domain.curso.Curso;
import teste.infrastructure.aluno.AlunoRepositoryJDBC;
import teste.infrastructure.curso.CursoRepositoryJDBC;

@QuarkusTest
public class AlunoRepositoryTest {

   private static final String NOME = "Juliana Cintra";
   private static final String CPF = "546.212.310-81";
   private static final String MATRICULA = "1010-100";
   private static final String CURSO = "ANÁLISES QUÍMICA";

   private static final String[] parameters = { NOME, CPF, MATRICULA, CURSO };
   private static final String[][] all = {
         { "Juliana Cintra", CPF, "1010-100", CURSO },
         { "Mallu Estácio", CPF, "1010-200", CURSO },
         { "Thays Soares", CPF, "1010-300", CURSO }
   };

   @Inject
   AlunoRepositoryJDBC repositorio;

   @Inject
   CursoRepositoryJDBC cursoRepository;

   @Test
   void testListarTodos() throws Exception {
      var actual = repositorio.listarTodos();
      assertEquals(3, actual.size());

      actual.forEach((response) -> {
         executeAssertions(all[response.getId() - 1], response);
      });
   }

   @Test
   void testBuscarPorMatricula() throws Exception {
      var actual = repositorio.buscarPorMatricula(MATRICULA);
      executeAssertions(parameters, actual);
   }

   @Test
   void testBuscarPorMatriculaNotFound() {
      assertThrows(NoResultException.class,
            () -> repositorio.buscarPorMatricula("0000"));
   }

   @Test
   void testBuscarPorId() throws Exception {
      var actual = repositorio.buscarPorId(1);
      executeAssertions(parameters, actual);
   }

   @Test
   void testBuscarPorIdNotFound() throws Exception {
      var actual = repositorio.buscarPorId(4);
      assertNull(actual);
   }

   @Test
   void testListarAlunosPorCurso() throws Exception {
      var actual = repositorio.listarAlunosPorCurso(1);

      assertEquals(3, actual.size());
      actual.forEach((response) -> {
         executeAssertions(all[response.getId() - 1], response);
      });
   }

   @Test
   void testListarAlunosPorCursoNoResult() throws Exception {
      var actual = repositorio.listarAlunosPorCurso(2);
      assertTrue(actual.isEmpty());
   }

   @Test
   @Transactional
   void testMatricular() throws Exception {
      var entity = new Aluno(
            "Unknow 1",
            "384.777.090-09",
            new Curso(CURSO));
      var actual = repositorio.matricular(entity);
      String[] expected = {
            entity.getNome(),
            entity.getCpf(),
            entity.getMatricula().getNumero(),
            entity.getCursoMatriculado().getNomeDoCurso()
      };

      executeAssertions(expected, actual);

      // Desfaz as alterações para não influenciar nos outros testes
      repositorio.apagarRegistro(actual);
      actual = repositorio.buscarPorId(4);
      assertNull(actual);
   }

   @Test
   @Transactional
   void testMatricularExceptionCPFInvalido() {
      assertThrows(ConstraintViolationException.class,
            () -> {
               var entity = new Aluno(
                     "Unknow 1",
                     "001.001.001-01",
                     new Curso("CURSO"));
               repositorio.matricular(entity);
            });
   }

   @Test
   @Transactional
   void testMatricularExceptionNomeInvalido() {
      // Nome em branco
      assertThrows(ConstraintViolationException.class,
            () -> {
               var entity = new Aluno(
                     "",
                     CPF,
                     new Curso("CURSO"));
               repositorio.matricular(entity);
            });
      // Size < min
      assertThrows(ConstraintViolationException.class,
            () -> {
               var entity = new Aluno(
                     "ab",
                     CPF,
                     new Curso("CURSO"));
               repositorio.matricular(entity);
            });
      // Size > max
      assertThrows(ConstraintViolationException.class,
            () -> {
               var entity = new Aluno(
                     "012345678901234567890123456789012345678901234567890",
                     CPF,
                     new Curso("CURSO"));
               repositorio.matricular(entity);
            });
   }

   @Test
   @Transactional
   void testRematricular() throws Exception {
      var actual = repositorio.buscarPorId(3);
      actual = repositorio.rematricular(actual);

      assertTrue(actual.isEstado());
      executeAssertions(all[2], actual);

      // Desfaz alterações
      actual = repositorio.cancelarMatricula(actual);
      assertFalse(actual.isEstado());
   }

   @Test
   @Transactional
   void testAtualizarCadastroDoAluno() throws Exception {
      var entity = repositorio.buscarPorId(1);
      entity.setNome("Unknow 1");
      entity.setCpf("384.777.090-09");

      var actual = repositorio.atualizarCadastroDoAluno(entity);
      String[] expected = {
            entity.getNome(),
            entity.getCpf(),
            entity.getMatricula().getNumero(),
            entity.getCursoMatriculado().getNomeDoCurso()
      };

      executeAssertions(expected, actual);

      // Desfaz as alterações para não influenciar nos outros testes
      actual.setNome(NOME);
      actual.setCpf(CPF);
      actual = repositorio.atualizarCadastroDoAluno(actual);

      assertEquals(NOME, actual.getNome());
      assertEquals(CPF, actual.getCpf());
   }

   @Test
   @Transactional
   void tesCancelarMatricula() throws Exception {
      var actual = repositorio.buscarPorId(1);
      actual = repositorio.cancelarMatricula(actual);

      assertFalse(actual.isEstado());
      executeAssertions(parameters, actual);

      // Desfaz as alterações
      actual = repositorio.rematricular(actual);
      assertTrue(actual.isEstado());
   }

   private void executeAssertions(String[] parameters, Aluno aluno) {
      assertEquals(parameters[0], aluno.getNome());
      assertEquals(parameters[1], aluno.getCpf());
      assertEquals(parameters[2], aluno.getMatricula().getNumero());
      assertEquals(parameters[3], aluno.getCursoMatriculado().getNomeDoCurso());

   }

}
