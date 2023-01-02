package teste.disciplina;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import teste.domain.disciplina.Disciplina;

public class DisciplinaEntityTest {

   private static final LocalDateTime DATANOW = LocalDateTime.now();

   @Test
   public void constructorAllArgs() {
      var entity = new Disciplina("DISCIPLINA");
      Assertions.assertEquals("DISCIPLINA", entity.getNomeDaDisciplina());
   }

   @Test
   public void constructorDefault() {
      var entity = new Disciplina();
      entity.setId(1);
      entity.setNomeDaDisciplina("DISCIPLINA");
      entity.setDataCriacao(DATANOW);
      entity.setDataAtualizacao(DATANOW);

      Assertions.assertEquals(1, entity.getId());
      Assertions.assertEquals("DISCIPLINA", entity.getNomeDaDisciplina());
      Assertions.assertEquals(DATANOW, entity.getDataCriacao());
      Assertions.assertEquals(DATANOW, entity.getDataAtualizacao());
   }
}
