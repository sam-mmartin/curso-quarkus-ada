package teste.aluno;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import teste.application.exceptions.ErrorResponse;
import teste.domain.VOs.matricula.Matricula;
import teste.domain.aluno.Aluno;
import teste.domain.curso.Curso;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AlunoEntityTest {

   private static final int id = 1;
   private static final String nome = "Mallu Estácio";
   private static final String cpf = "830.173.730-10";
   private static final Matricula matricula = new Matricula("1000-000");
   private static final boolean estado = true;
   private static final Curso curso = new Curso("Alimentos");

   private static final LocalDateTime dataCriacao = LocalDateTime.now();
   private static final LocalDateTime dataAtualizacao = LocalDateTime.now();

   @Test
   public void constructorAllArgs() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var entity = new Aluno(nome, cpf, curso);

      assertionsSomeArgs(validator, entity);

      factory.close();
   }

   @Test
   public void constructorDefault() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var entity = new Aluno();
      entity.setId(id);
      entity.setNome(nome);
      entity.setCpf(cpf);
      entity.setMatricula(matricula);
      entity.setEstado(estado);
      entity.setCursoMatriculado(curso);
      entity.setDataCriacao(dataCriacao);
      entity.setDataAtualizacao(dataAtualizacao);

      assertionsAllArgs(validator, entity);

      factory.close();
   }

   @ParameterizedTest
   @MethodSource("invalidFields")
   public void notValidFields(final String parameter, final String errorMessage) {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var entity = new Aluno();
      entity.setNome(parameter);
      entity.setCpf(cpf);
      var violations = validator.validate(entity);

      violations.forEach(error -> {
         assertEquals(errorMessage, error.getMessage());
      });
   }

   private void assertionsSomeArgs(final Validator validator, final Aluno entity) {
      final var violations = validator.validate(entity);

      Assertions.assertTrue(violations.isEmpty());
      Assertions.assertEquals(nome, entity.getNome());
      Assertions.assertEquals(cpf, entity.getCpf());
      Assertions.assertEquals(curso, entity.getCursoMatriculado());
   }

   private void assertionsAllArgs(final Validator validator, final Aluno entity) {
      final var violations = validator.validate(entity);

      Assertions.assertTrue(violations.isEmpty());
      Assertions.assertEquals(id, entity.getId());
      Assertions.assertEquals(nome, entity.getNome());
      Assertions.assertEquals(cpf, entity.getCpf());
      Assertions.assertEquals(matricula, entity.getMatricula());
      Assertions.assertEquals(estado, entity.isEstado());
      Assertions.assertEquals(curso, entity.getCursoMatriculado());
      Assertions.assertEquals(dataCriacao, entity.getDataCriacao());
      Assertions.assertEquals(dataAtualizacao, entity.getDataAtualizacao());
   }

   static Stream<Arguments> invalidFields() {
      return Stream.of(
            arguments(null, "O nome do aluno é obrigatório"),
            arguments("", "O nome do aluno é obrigatório"),
            arguments("ex", "O nome do aluno deve possuir no mínimo 3 e no máximo 50 caracteres"),
            arguments(null, "O CPF é obrigatório"),
            arguments("", "O CPF é obrigatório"),
            arguments("000.111.222-33", "CPF inválido"));
   }

}
