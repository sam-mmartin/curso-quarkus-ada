package teste.disciplina;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import teste.application.dto.disciplina.DisciplinaResponseDTO;
import teste.application.interfaces.mapper.DisciplinaMapper;
import teste.application.interfaces.mapper.DisciplinaMapperImpl;
import teste.domain.disciplina.Disciplina;

public class DisciplinaMapperTest {

   private static final int ID_1 = 1;
   private static final int ID_2 = 2;
   private static final String NOME_1 = "Unknow Someone 1";
   private static final String NOME_2 = "Unknow Someone 2";
   private static final LocalDateTime DATA = LocalDateTime.now();
   private static final String DATAFORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(DATA);

   private static Disciplina entity_1;
   private static Disciplina entity_2;
   private static final DisciplinaResponseDTO dto_1 = new DisciplinaResponseDTO(
         ID_1, NOME_1, DATAFORMATTER, DATAFORMATTER);
   private static final DisciplinaResponseDTO dto_2 = new DisciplinaResponseDTO(
         ID_2, NOME_2, DATAFORMATTER, DATAFORMATTER);

   private static final DisciplinaMapper mapper = new DisciplinaMapperImpl();

   @Test
   public void toResource() {
      buildDisciplina();
      var actual = mapper.toResource(entity_1);
      Assertions.assertEquals(dto_1, actual);
   }

   @Test
   public void listToResource() {
      buildDisciplina();
      var listEntities = List.of(entity_1, entity_2);
      var listResponse = List.of(dto_1, dto_2);
      var actual = mapper.listToResource(listEntities);

      Assertions.assertTrue(actual.containsAll(listResponse));
   }

   private void buildDisciplina() {
      entity_1 = new Disciplina();
      entity_1.setId(ID_1);
      entity_1.setNomeDaDisciplina(NOME_1);
      entity_1.setDataCriacao(DATA);
      entity_1.setDataAtualizacao(DATA);

      entity_2 = new Disciplina();
      entity_2.setId(ID_2);
      entity_2.setNomeDaDisciplina(NOME_2);
      entity_2.setDataCriacao(DATA);
      entity_2.setDataAtualizacao(DATA);
   }
}
