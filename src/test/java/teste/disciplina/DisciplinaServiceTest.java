package teste.disciplina;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.inject.Inject;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;
import teste.application.dto.disciplina.DisciplinaRequestDTO;
import teste.application.dto.disciplina.DisciplinaResponseDTO;
import teste.application.services.DisciplinaService;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DisciplinaServiceTest {

   @Inject
   DisciplinaService service;

   private static final String[] disciplinas = {
         "MATEMÁTICA", "PORTUGUÊS", "BIOLOGIA" };
   private static final String dateTime = "2022-12-27T18:02:51.049854";
   private static final DisciplinaRequestDTO request = new DisciplinaRequestDTO("DISCIPLINA");

   @Test
   @Order(1)
   void getAll() throws Exception {
      var actual = service.getAll();

      assertEquals(9, actual.size());

      for (int i = 0; i < disciplinas.length; i++) {
         assertEquals(disciplinas[i], actual.get(i).getNomeDaDisciplina());
      }
   }

   @Test
   @Order(2)
   void getById() throws Exception {
      var actual = service.getById(1);

      executeAssertions(actual);
   }

   @Test
   @Order(3)
   void getByName() throws Exception {
      var actual = service.getByName("MATEMÁTICA");

      executeAssertions(actual);
   }

   @Test
   @Order(4)
   void create() throws Exception {
      var response = service.create(request);
      var actual = service.getByName("DISCIPLINA");

      assertEquals("Disciplina: DISCIPLINA implantada com sucesso.", response.getTexto());
      assertEquals("DISCIPLINA", actual.getNomeDaDisciplina());
   }

   @Test
   @Order(5)
   void update() throws Exception {
      var response = service.update(9, request);
      var actual = service.getById(9);

      assertEquals("Cadastro atualizado com sucesso.", response.getTexto());
      assertEquals("DISCIPLINA", actual.getNomeDaDisciplina());
   }

   @Test
   @Order(6)
   void delete() throws Exception {
      service.delete(9);
      var actual = service.getById(9);
      assertNull(actual);
   }

   private void executeAssertions(DisciplinaResponseDTO response) {
      assertEquals("MATEMÁTICA", response.getNomeDaDisciplina());
      assertEquals(dateTime, response.getDataCriacao());
      assertEquals(dateTime, response.getDataAtualizacao());
   }
}
