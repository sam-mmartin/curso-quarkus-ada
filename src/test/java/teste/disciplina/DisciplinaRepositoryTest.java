package teste.disciplina;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import teste.domain.disciplina.Disciplina;
import teste.infrastructure.disciplina.DisciplinaRepositoryJDBC;

@QuarkusTest
public class DisciplinaRepositoryTest {

   @Inject
   DisciplinaRepositoryJDBC repository;

   @Test
   public void getAll() {
      var entity_1 = new Disciplina("DISCIPLIA 1");
      var entity_2 = new Disciplina("DISCIPLIA 2");
      var entity_3 = new Disciplina("DISCIPLIA 3");

      save(repository, entity_1, entity_2, entity_3);

      var actual = repository.listAll();

      Assertions.assertEquals(12, actual.size());
      Assertions.assertEquals(entity_1.getNomeDaDisciplina(), actual.get(9).getNomeDaDisciplina());
      Assertions.assertEquals(entity_2.getNomeDaDisciplina(), actual.get(10).getNomeDaDisciplina());
      Assertions.assertEquals(entity_3.getNomeDaDisciplina(), actual.get(11).getNomeDaDisciplina());
   }

   @Transactional
   void save(DisciplinaRepositoryJDBC repository, Disciplina... entities) {
      for (Disciplina entity : entities) {
         repository.persist(entity);
      }
   }
}
