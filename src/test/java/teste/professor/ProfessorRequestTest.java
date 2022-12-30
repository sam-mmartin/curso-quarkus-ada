package teste.professor;

import java.util.stream.Stream;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import teste.application.dto.professor.ProfessorRequestDTO;
import teste.application.exceptions.ErrorResponse;

public class ProfessorRequestTest {

   private static final String nome = "Sérgio Carlos Mário Ramos";
   private static final String cpf = "326.760.931-34";

   @Test
   public void constructorAllArgs() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var request = new ProfessorRequestDTO(nome, cpf);
      executeAssertions(validator, request);

      factory.close();
   }

   @Test
   public void constructorDefault() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var request = new ProfessorRequestDTO();
      request.setNome(nome);
      request.setCpf(cpf);

      executeAssertions(validator, request);

      factory.close();
   }

   @Test
   public void equalsAndHashCode() {
      var dto1 = new ProfessorRequestDTO(nome, cpf);
      var dto2 = new ProfessorRequestDTO(nome, cpf);

      Assertions.assertTrue(dto1.equals(dto2) && dto2.equals(dto1));
      Assertions.assertTrue(dto1.hashCode() == dto2.hashCode());
   }

   @ParameterizedTest
   @MethodSource("invalidFields")
   public void notValidFields(final String nome, final String cpf, final String errorMessage) {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var request = new ProfessorRequestDTO(nome, cpf);

      final var violations = validator.validate(request);
      ErrorResponse errors = new ErrorResponse(violations);

      Assertions.assertFalse(violations.isEmpty());
      Assertions.assertEquals(errorMessage, errors.getMessage());

      factory.close();
   }

   private void executeAssertions(Validator validator, ProfessorRequestDTO dto) {
      final var violations = validator.validate(dto);

      Assertions.assertTrue(violations.isEmpty());
      Assertions.assertEquals(nome, dto.getNome());
      Assertions.assertEquals(cpf, dto.getCpf());
   }

   static Stream<Arguments> invalidFields() {
      return Stream.of(
            arguments(null, cpf, "É necessário informar o nome"),
            arguments("", cpf, "É necessário informar o nome"),
            arguments(nome, null, "É necessário informar o CPF"),
            arguments(nome, "", "É necessário informar o CPF"));
   }
}
