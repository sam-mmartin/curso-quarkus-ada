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

   private static final String nome = "Unknow 2";
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
   @MethodSource("invalidFields")
   public void camposInvalidos(final String name, final String cpf, final String curso, final String errorMessage) {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var request = new AlunoCursoRequestDTO(name, cpf, curso);

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

      Assertions.assertEquals(aluno1, aluno2);
      Assertions.assertEquals(aluno1.hashCode(), aluno2.hashCode());
   }

   @Test
   public void notEqualsAndHashCode() {
      var aluno1 = new AlunoCursoRequestDTO(nome, cpf, curso);
      var aluno3 = new AlunoCursoRequestDTO("nome", "cpf", "curso");

      Assertions.assertNotEquals(aluno1, aluno3);
      Assertions.assertNotEquals(aluno1.hashCode(), aluno3.hashCode());
   }

   @Test
   void dtoToString() {
      var aluno = new AlunoCursoRequestDTO(nome, cpf, curso);
      String toString = "AlunoCursoRequestDTO(nome=Unknow 2, cpf=830.173.730-10, curso=Alimentos)";

      Assertions.assertEquals(toString, aluno.toString());
   }

   private void exectuteAssertions(final Validator validator, final AlunoCursoRequestDTO request) {
      final var violations = validator.validate(request);

      Assertions.assertTrue(violations.isEmpty());
      Assertions.assertEquals(nome, request.getNome());
   }

   static Stream<Arguments> invalidFields() {
      return Stream.of(
            arguments(null, cpf, curso, "É necessário informar o nome"),
            arguments("", cpf, curso, "É necessário informar o nome"),
            arguments(nome, null, curso, "É necessário informar o CPF"),
            arguments(nome, "", curso, "É necessário informar o CPF"),
            arguments(nome, cpf, null, "É necessário informar o nome do curso"),
            arguments(nome, cpf, "", "É necessário informar o nome do curso"));
   }
}
