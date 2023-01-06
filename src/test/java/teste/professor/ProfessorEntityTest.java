package teste.professor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.validation.Validation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import teste.domain.VOs.matricula.Matricula;
import teste.domain.curso.Curso;
import teste.domain.mappeamento.CursoProfessor;
import teste.domain.mappeamento.ProfessorDisciplina;
import teste.domain.professor.Professor;

public class ProfessorEntityTest {

   private static final int id = 1;
   private static final String nome = "Unknow 2";
   private static final String cpf = "830.173.730-10";
   private static final Matricula matricula = new Matricula("1000-000");
   private static final boolean estado = true;

   private static final List<CursoProfessor> cursos = new ArrayList<>() {
      {
         add(new CursoProfessor(new Curso("CURSO"), new Professor()));
      }
   };
   private static final List<ProfessorDisciplina> disciplinas = new ArrayList<>() {
      {
         add(new ProfessorDisciplina());
      }
   };

   private static final LocalDateTime dataTime = LocalDateTime.now();

   private static final String observacao = "OBSERVACAO";

   @Test
   void testConstructorAllArgs() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var entity = new Professor(
            nome, cpf, matricula, estado, dataTime, dataTime, observacao);

      final var violations = validator.validate(entity);

      assertTrue(violations.isEmpty());
      assertEquals(nome, entity.getNome());
      assertEquals(cpf, entity.getCpf());
      assertEquals(matricula, entity.getMatricula());
      assertTrue(entity.isEstado());
      assertEquals(dataTime, entity.getDataCriacao());
      assertEquals(dataTime, entity.getDataAtualizacao());
      assertEquals(observacao, entity.getObservacao());

      factory.close();
   }

   @Test
   void testConstructorSomeArgs() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var entity = new Professor(nome, cpf);

      final var violations = validator.validate(entity);

      assertTrue(violations.isEmpty());
      assertEquals(nome, entity.getNome());
      assertEquals(cpf, entity.getCpf());

      factory.close();
   }

   @Test
   void testConstructorDefault() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var entity = new Professor();
      entity.setId(id);
      entity.setNome(nome);
      entity.setCpf(cpf);
      entity.setMatricula(matricula);
      entity.setEstado(estado);
      entity.setCursosLecionados(cursos);
      entity.setDisciplinasLecionadas(disciplinas);
      entity.setDataCriacao(dataTime);
      entity.setDataAtualizacao(dataTime);
      entity.setObservacao(observacao);

      final var violations = validator.validate(entity);

      assertTrue(violations.isEmpty());
      assertTrue(entity.isEstado());
      assertTrue(entity.getCursosLecionados().containsAll(cursos));
      assertTrue(entity.getDisciplinasLecionadas().containsAll(disciplinas));

      assertEquals(nome, entity.getNome());
      assertEquals(cpf, entity.getCpf());
      assertEquals(matricula, entity.getMatricula());
      assertEquals(dataTime, entity.getDataCriacao());
      assertEquals(dataTime, entity.getDataAtualizacao());
      assertEquals(observacao, entity.getObservacao());

      factory.close();
   }

   @ParameterizedTest
   @MethodSource("invalidFields")
   void testNotValidFields(final String nome, final String cpf, final String error) {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var entity = new Professor(nome, cpf);

      final var violations = validator.validate(entity);

      violations.forEach(e -> {
         assertEquals(error, e.getMessage());
      });

      factory.close();

   }

   static Stream<Arguments> invalidFields() {
      String fiveZero = "012345678901234567890123456789012345678901234567890";
      return Stream.of(
            arguments(null, cpf, "O nome é obrigatório"),
            arguments("   ", cpf, "O nome é obrigatório"),
            arguments("ex", cpf, "O nome do professor deve possuir no mínimo 3 e no máximo 50 caracteres"),
            arguments(fiveZero, cpf, "O nome do professor deve possuir no mínimo 3 e no máximo 50 caracteres"),
            arguments(nome, "", "CPF inválido"),
            arguments(nome, null, "CPF inválido"),
            arguments(nome, "000.111.222-33", "CPF inválido"));
   }
}
