package teste.mapeamento;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import teste.application.dto.mapeamento.NameResponseDTO;

public class NameResponseTest {

   private static final int id = 1;
   private static final String nome = "NOME";

   @Test
   void testConstructorAllArgs() {
      var response = new NameResponseDTO(id, nome);
      executeAssertions(response);
   }

   @Test
   void testConstructorDefault() {
      var response = new NameResponseDTO();
      response.setId(id);
      response.setName(nome);

      executeAssertions(response);
   }

   @Test
   void testEqualsAndHashCode() {
      var res_1 = new NameResponseDTO(id, nome);
      var res_2 = new NameResponseDTO(id, nome);

      assertEquals(res_1, res_2);
      assertEquals(res_1.hashCode(), res_2.hashCode());
   }

   private void executeAssertions(NameResponseDTO response) {
      assertEquals(id, response.getId());
      assertEquals(nome, response.getName());
   }

}
