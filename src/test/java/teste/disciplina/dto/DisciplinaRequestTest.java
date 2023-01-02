package teste.disciplina.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import teste.application.dto.disciplina.DisciplinaRequestDTO;

public class DisciplinaRequestTest {

   private static final String DISCIPLINA = "Disciplina";

   @Test
   public void constructorAllArgs() {
      var request = new DisciplinaRequestDTO(DISCIPLINA);

      Assertions.assertEquals(DISCIPLINA, request.getNomeDaDisciplina());
   }

   @Test
   public void constructorDefault() {
      var request = new DisciplinaRequestDTO();
      request.setNomeDaDisciplina(DISCIPLINA);

      Assertions.assertEquals(DISCIPLINA, request.getNomeDaDisciplina());
   }

   @Test
   public void equalsAndHashCode() {
      var request_1 = new DisciplinaRequestDTO(DISCIPLINA);
      var request_2 = new DisciplinaRequestDTO(DISCIPLINA);

      Assertions.assertTrue(request_1.equals(request_2) && request_2.equals(request_1));
      Assertions.assertTrue(request_1.hashCode() == request_2.hashCode());
   }
}
