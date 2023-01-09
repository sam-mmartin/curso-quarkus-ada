package teste.professor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import javax.inject.Inject;
import javax.transaction.RollbackException;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.quarkus.test.junit.QuarkusTest;
import teste.domain.professor.Professor;
import teste.infrastructure.professor.ProfessorRepositoryJDBC;

@QuarkusTest
public class ProfessorRepositoryTest {

   private static final String NOME = "Samuel";
   private static final String CPF = "546.212.310-81";
   private static final String MATRICULA = "1000-100";

   @Inject
   ProfessorRepositoryJDBC repository;

   @Test
   void testListarTodos() throws Exception {
      var actual = repository.listarTodos();

      assertEquals(1, actual.size());
      executeAssertions(actual.get(0));
   }

   @Test
   void testBuscarPorMatricula() throws Exception {
      var actual = repository.buscarPorMatricula(MATRICULA);
      executeAssertions(actual);
   }

   @Test
   void testBuscarPorMatriculaNoResult() throws Exception {
      var res = repository.buscarPorMatricula("0000");
      assertNull(res);
   }

   @Test
   void testBuscarPorId() throws Exception {
      var actual = repository.buscarPorId(1);
      executeAssertions(actual);
   }

   @Test
   void testBuscarPorIdNoResult() throws Exception {
      var actual = repository.buscarPorId(2);
      assertNull(actual);
   }

   @Test
   void testContratar() throws Exception {
      var entity = new Professor("NOME", "384.777.090-09");
      entity.setEstado(true);
      var actual = repository.contratar(entity);

      assertEquals(2, actual.getId());
      assertEquals("NOME", actual.getNome());
      assertEquals("384.777.090-09", actual.getCpf());
      assertTrue(actual.isEstado());

      // Desfaz as alterações
      repository.demitir(actual);
   }

   @ParameterizedTest
   @MethodSource("invalidFields")
   void testContratarException(final String nome, final String cpf, final String error) {
      assertThrows(ConstraintViolationException.class,
            () -> {
               var entity = new Professor(nome, cpf);
               repository.contratar(entity);
            },
            error);
   }

   @Test
   void testAtualizarProfessor() throws Exception {
      var actual = repository.buscarPorId(1);
      actual.setNome("NOME");
      actual.setCpf("384.777.090-09");

      actual = repository.atualizarProfessor(actual);

      assertEquals("NOME", actual.getNome());
      assertEquals("384.777.090-09", actual.getCpf());

      // Desfaz as alterações
      actual.setNome(NOME);
      actual.setCpf(CPF);
      actual = repository.atualizarProfessor(actual);
   }

   @ParameterizedTest
   @MethodSource("invalidFields")
   void testAtualizarProfessorException(final String nome, final String cpf, final String error) {
      assertThrows(RollbackException.class,
            () -> {
               var actual = repository.buscarPorId(1);
               actual.setNome(nome);
               actual.setCpf(cpf);
               repository.atualizarProfessor(actual);
            },
            error);
   }

   @Test
   void demitir() throws Exception {
      var entity = new Professor(NOME, CPF);
      var actual = repository.contratar(entity);

      repository.demitir(actual);
      actual = repository.buscarPorId(2);

      assertNull(actual);
   }

   static Stream<Arguments> invalidFields() {
      String fiveZero = "012345678901234567890123456789012345678901234567890";
      return Stream.of(
            arguments(null, CPF, "O nome é obrigatório"),
            arguments("   ", CPF, "O nome é obrigatório"),
            arguments("ex", CPF, "O nome do professor deve possuir no mínimo 3 e no máximo 50 caracteres"),
            arguments(fiveZero, CPF, "O nome do professor deve possuir no mínimo 3 e no máximo 50 caracteres"),
            arguments(NOME, "", "CPF inválido"),
            arguments(NOME, "000.111.222-33", "CPF inválido"));
   }

   private void executeAssertions(Professor actual) {
      assertEquals(NOME, actual.getNome());
      assertEquals(CPF, actual.getCpf());
      assertEquals(MATRICULA, actual.getMatricula().getNumero());
      assertTrue(actual.isEstado());
   }

}
