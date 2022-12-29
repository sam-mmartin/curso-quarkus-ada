package teste.aluno;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import teste.application.dto.aluno.AlunosByCursoResponseDTO;

public class AlunosByCursoResponse {

   private static final String nome = "Mallu Estácio";
   private static final String matricula = "1000-000";
   private static final String estado = "ativo";

   @Test
   public void constructorAllArgs() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var request = new AlunosByCursoResponseDTO(nome, matricula, estado);

      exectuteAssertions(validator, request);

      factory.close();
   }

   @Test
   public void constructorDefault() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var request = new AlunosByCursoResponseDTO();
      request.setNome(nome);
      request.setMatricula(matricula);
      request.setEstado(estado);

      exectuteAssertions(validator, request);

      factory.close();
   }

   @Test
   public void equalsAndHashCode() {
      var aluno1 = new AlunosByCursoResponseDTO(nome, matricula, estado);
      var aluno2 = new AlunosByCursoResponseDTO(nome, matricula, estado);

      Assertions.assertTrue(aluno1.equals(aluno2) && aluno2.equals(aluno1));
      Assertions.assertTrue(aluno1.hashCode() == aluno2.hashCode());
   }

   private void exectuteAssertions(final Validator validator, final AlunosByCursoResponseDTO request) {
      final var violations = validator.validate(request);

      Assertions.assertTrue(violations.isEmpty());
      Assertions.assertEquals(nome, request.getNome());
   }
}
