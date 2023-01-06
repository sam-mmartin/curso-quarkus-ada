package teste.professor.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import teste.application.dto.professor.ProfessorRequestDTO;
import teste.application.exceptions.ErrorResponse;

public class ProfessorRequestTest {

   private static final String nome = "Samuel";
   private static final String cpf = "546.212.310-81";

   @Test
   void constructorAllArgs() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();
      var request = new ProfessorRequestDTO(nome, cpf);

      exectuteAssertions(validator, request);
      factory.close();
   }

   @Test
   void constructorDefault() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();
      var request = new ProfessorRequestDTO();
      request.setNome(nome);
      request.setCpf(cpf);

      exectuteAssertions(validator, request);
      factory.close();
   }

   @Test
   void equalsAndHashCode() {
      var req_1 = new ProfessorRequestDTO(nome, cpf);
      var req_2 = new ProfessorRequestDTO(nome, cpf);

      assertEquals(req_1, req_2);
      assertEquals(req_1.hashCode(), req_2.hashCode());
   }

   @ParameterizedTest
   @MethodSource("invalidFields")
   void validatorNotBlank(final String nome, final String cpf, final String error) {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();
      var request = new ProfessorRequestDTO(nome, cpf);

      final var violations = validator.validate(request);
      ErrorResponse errors = new ErrorResponse(violations);

      assertFalse(violations.isEmpty());
      assertEquals(error, errors.getMessage());
   }

   static Stream<Arguments> invalidFields() {
      return Stream.of(
            arguments(null, cpf, "É necessário informar o nome"),
            arguments("", cpf, "É necessário informar o nome"),
            arguments(nome, null, "É necessário informar o CPF"),
            arguments(nome, "", "É necessário informar o CPF"));
   }

   private void exectuteAssertions(final Validator validator, final ProfessorRequestDTO request) {
      final var violations = validator.validate(request);

      assertTrue(violations.isEmpty());
      assertEquals(nome, request.getNome());
      assertEquals(cpf, request.getCpf());
   }
}
