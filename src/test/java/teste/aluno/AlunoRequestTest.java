package teste.aluno;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import teste.application.dto.aluno.AlunoRequestDTO;
import teste.application.exceptions.ErrorResponse;

@QuarkusTest
public class AlunoRequestTest {

   private static final String name = "Mallu Estácio";
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

   @Test
   public void nameNotBlank() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var request = new AlunoRequestDTO(" ", cpf);

      final var violations = validator.validate(request);
      ErrorResponse errors = new ErrorResponse(violations);

      Assertions.assertFalse(violations.isEmpty());
      Assertions.assertEquals("É necessário informar o nome", errors.getMessage());

      factory.close();
   }

   private void exectuteAssertions(final Validator validator, final AlunoRequestDTO request) {
      final var violations = validator.validate(request);

      Assertions.assertTrue(violations.isEmpty());
      Assertions.assertEquals(name, request.getNome());
      Assertions.assertEquals(cpf, request.getCpf());
   }

}
