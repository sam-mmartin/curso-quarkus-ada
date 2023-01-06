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

import teste.application.dto.aluno.AlunoRequestDTO;
import teste.application.exceptions.ErrorResponse;

public class AlunoRequestTest {

   private static final String name = "Unknow 2";
   private static final String cpf = "830.173.730-10";

   @Test
   public void constructorAllArgs() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var request = new AlunoRequestDTO(name, cpf);

      exectuteAssertions(validator, request);

      factory.close();
   }

   @Test
   public void constructorDefault() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var request = new AlunoRequestDTO();
      request.setNome(name);
      request.setCpf(cpf);

      exectuteAssertions(validator, request);

      factory.close();
   }

   @ParameterizedTest
   @MethodSource("invalidFields")
   public void nameNotBlank(final String name, final String cpf, final String errorMessage) {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var request = new AlunoRequestDTO(name, cpf);

      final var violations = validator.validate(request);
      ErrorResponse errors = new ErrorResponse(violations);

      Assertions.assertFalse(violations.isEmpty());
      Assertions.assertEquals(errorMessage, errors.getMessage());

      factory.close();
   }

   @Test
   public void equalsAndHashCode() {
      var aluno1 = new AlunoRequestDTO(name, cpf);
      var aluno2 = new AlunoRequestDTO(name, cpf);

      Assertions.assertTrue(aluno1.equals(aluno2) && aluno2.equals(aluno1));
      Assertions.assertTrue(aluno1.hashCode() == aluno2.hashCode());
   }

   static Stream<Arguments> invalidFields() {
      return Stream.of(
            arguments(null, cpf, "É necessário informar o nome"),
            arguments("", cpf, "É necessário informar o nome"),
            arguments(name, null, "É necessário informar o CPF"),
            arguments(name, "", "É necessário informar o CPF"));
   }

   private void exectuteAssertions(final Validator validator, final AlunoRequestDTO request) {
      final var violations = validator.validate(request);

      Assertions.assertTrue(violations.isEmpty());
      Assertions.assertEquals(name, request.getNome());
      Assertions.assertEquals(cpf, request.getCpf());
   }

}
