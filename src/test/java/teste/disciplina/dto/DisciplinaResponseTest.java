package teste.disciplina.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import teste.application.dto.disciplina.DisciplinaResponseDTO;

public class DisciplinaResponseTest {

   private static final int ID = 1;
   private static final String DISCIPLINA = "Disciplina";
   private static final String DATA = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

   @Test
   public void constructorAllArgs() {
      var request = new DisciplinaResponseDTO(ID, DISCIPLINA, DATA, DATA);
      executeAssertions(request);
   }

   @Test
   public void constructorDefault() {
      var request = new DisciplinaResponseDTO();
      request.setId(ID);
      request.setNomeDaDisciplina(DISCIPLINA);
      request.setDataCriacao(DATA);
      request.setDataAtualizacao(DATA);

      executeAssertions(request);
   }

   @Test
   public void equalsAndHashCode() {
      var request_1 = new DisciplinaResponseDTO(ID, DISCIPLINA, DATA, DATA);
      var request_2 = new DisciplinaResponseDTO(ID, DISCIPLINA, DATA, DATA);

      Assertions.assertTrue(request_1.equals(request_2) && request_2.equals(request_1));
      Assertions.assertTrue(request_1.hashCode() == request_2.hashCode());
   }

   private void executeAssertions(DisciplinaResponseDTO request) {
      Assertions.assertEquals(ID, request.getId());
      Assertions.assertEquals(DISCIPLINA, request.getNomeDaDisciplina());
      Assertions.assertEquals(DATA, request.getDataCriacao());
      Assertions.assertEquals(DATA, request.getDataAtualizacao());
   }
}
