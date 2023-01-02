package teste.aluno.dto;

import java.util.stream.Stream;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import teste.application.dto.aluno.AlunoCursoRequestDTO;
import teste.application.exceptions.ErrorResponse;

public class AlunoCursoRequestTest {

   private static final String nome = "Mallu Estácio";
   private static final String cpf = "830.173.730-10";
   private static final String curso = "Alimentos";

   @Test
   public void constructorAllArgs() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var request = new AlunoCursoRequestDTO(
            nome, cpf, curso);

      exectuteAssertions(validator, request);

      factory.close();
   }

   @Test
   public void constructorDefault() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var request = new AlunoCursoRequestDTO();
      request.setNome(nome);
      request.setCpf(cpf);
      request.setCurso(curso);

      exectuteAssertions(validator, request);

      factory.close();
   }

   @ParameterizedTest
   @MethodSource("invalidNameFields")
   public void nameNotBlank(final String name, final String errorMessage) {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var request = new AlunoCursoRequestDTO(name, cpf, curso);

      final var violations = validator.validate(request);
      ErrorResponse errors = new ErrorResponse(violations);

      Assertions.assertFalse(violations.isEmpty());
      Assertions.assertEquals(errorMessage, errors.getMessage());

      factory.close();
   }

   @ParameterizedTest
   @MethodSource("invalidCpfFields")
   public void cpfNotBlank(final String cpf, final String errorMessage) {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var request = new AlunoCursoRequestDTO(nome, cpf, curso);

      final var violations = validator.validate(request);
      ErrorResponse errors = new ErrorResponse(violations);

      Assertions.assertFalse(violations.isEmpty());
      Assertions.assertEquals(errorMessage, errors.getMessage());

      factory.close();
   }

   @ParameterizedTest
   @MethodSource("invalidCursoFields")
   public void cursoNotBlank(final String curso, final String errorMessage) {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var request = new AlunoCursoRequestDTO(nome, cpf, curso);

      final var violations = validator.validate(request);
      ErrorResponse errors = new ErrorResponse(violations);

      Assertions.assertFalse(violations.isEmpty());
      Assertions.assertEquals(errorMessage, errors.getMessage());

      factory.close();
   }

   @Test
   public void equalsAndHashCode() {
      var aluno1 = new AlunoCursoRequestDTO(nome, cpf, curso);
      var aluno2 = new AlunoCursoRequestDTO(nome, cpf, curso);

      Assertions.assertTrue(aluno1.equals(aluno2) && aluno2.equals(aluno1));
      Assertions.assertTrue(aluno1.hashCode() == aluno2.hashCode());
   }

   private void exectuteAssertions(final Validator validator, final AlunoCursoRequestDTO request) {
      final var violations = validator.validate(request);

      Assertions.assertTrue(violations.isEmpty());
      Assertions.assertEquals(nome, request.getNome());
   }

   static Stream<Arguments> invalidNameFields() {
      return Stream.of(
            arguments(null, "É necessário informar o nome"),
            arguments("", "É necessário informar o nome"));
   }

   static Stream<Arguments> invalidCpfFields() {
      return Stream.of(
            arguments(null, "É necessário informar o CPF"),
            arguments("", "É necessário informar o CPF"));
   }

   static Stream<Arguments> invalidCursoFields() {
      return Stream.of(
            arguments(null, "É necessário informar o nome do curso"),
            arguments("", "É necessário informar o nome do curso"));
   }
}
