package teste.professor.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import teste.application.dto.professor.ProfessorBasicInfoResponseDTO;

public class ProfessorBasicInfoResponseTest {

   private static final String nome = "NOME";
   private static final String matricula = "MATRICULA";

   @Test
   void testContructorAllArgs() {
      var response = new ProfessorBasicInfoResponseDTO(nome, matricula);
      executeAssertions(response);
   }

   @Test
   void testConstructorDefault() {
      var response = new ProfessorBasicInfoResponseDTO();
      response.setNome(nome);
      response.setMatricula(matricula);

      executeAssertions(response);
   }

   @Test
   void testEqualsAndHashCode() {
      var res_1 = new ProfessorBasicInfoResponseDTO(nome, matricula);
      var res_2 = new ProfessorBasicInfoResponseDTO(nome, matricula);

      assertEquals(res_1, res_2);
      assertEquals(res_1.hashCode(), res_2.hashCode());
   }

   private void executeAssertions(ProfessorBasicInfoResponseDTO response) {
      assertEquals(nome, response.getNome());
      assertEquals(matricula, response.getMatricula());
   }
}
