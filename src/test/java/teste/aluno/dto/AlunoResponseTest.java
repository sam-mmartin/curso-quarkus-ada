package teste.aluno.dto;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import teste.application.dto.aluno.AlunoResponseDTO;

public class AlunoResponseTest {

   private static final int id = 1;
   private static final String nome = "Mallu Estácio";
   private static final String cpf = "830.173.730-10";
   private static final String matricula = "1000-000";
   private static final String estado = "ativo";
   private static final String curso = "Alimentos";
   private static final String dataCriacao = "27/12/2022 18:00:58";
   private static final String dataAtualizacao = "27/12/2022 18:00:5";

   @Test
   public void constructorAllArgs() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var response = new AlunoResponseDTO(
            id,
            nome,
            cpf,
            matricula,
            estado,
            curso,
            dataCriacao,
            dataAtualizacao);

      exectuteAssertions(validator, response);

      factory.close();
   }

   @Test
   public void constructorDefault() {
      final var factory = Validation.buildDefaultValidatorFactory();
      final var validator = factory.getValidator();

      var response = new AlunoResponseDTO();
      response.setId(id);
      response.setNome(nome);
      response.setCpf(cpf);
      response.setMatricula(matricula);
      response.setEstado(estado);
      response.setCurso(curso);
      response.setDataCriacao(dataCriacao);
      response.setDataAtualizacao(dataAtualizacao);

      exectuteAssertions(validator, response);

      factory.close();
   }

   @Test
   public void equalsAndHashCode() {
      var aluno1 = new AlunoResponseDTO(
            id,
            nome,
            cpf,
            matricula,
            estado,
            curso,
            dataCriacao,
            dataAtualizacao);
      var aluno2 = new AlunoResponseDTO(
            id,
            nome,
            cpf,
            matricula,
            estado,
            curso,
            dataCriacao,
            dataAtualizacao);

      Assertions.assertTrue(aluno1.equals(aluno2) && aluno2.equals(aluno1));
      Assertions.assertTrue(aluno1.hashCode() == aluno2.hashCode());
   }

   private void exectuteAssertions(final Validator validator, final AlunoResponseDTO response) {
      final var violations = validator.validate(response);

      Assertions.assertTrue(violations.isEmpty());
      Assertions.assertEquals(nome, response.getNome());
   }
}
