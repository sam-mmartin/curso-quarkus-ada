package teste.aluno;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;
import teste.application.dto.aluno.AlunoCursoRequestDTO;
import teste.application.dto.aluno.AlunoRequestDTO;
import teste.application.dto.aluno.AlunoResponseDTO;
import teste.application.services.AlunoService;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlunoServiceTest {

   private static final String NOME = "Juliana Cintra";
   private static final String NOME_1 = "Unknow Someone";
   private static final String CPF = "546.212.310-81";
   private static final String MATRICULA = "1010-100";
   private static final String CURSO = "ANÁLISES QUÍMICA";
   private static final String[] PARAMETERS = { NOME, CPF, MATRICULA, CURSO };

   @Inject
   AlunoService service;

   @Test
   @Order(1)
   public void getById() throws Exception {
      var actual = service.getById(1);
      executeAssertions(PARAMETERS, actual);
   }

   @Test
   @Order(2)
   public void getAll() throws Exception {
      var actual = service.getAll();

      Assertions.assertTrue(actual.size() == 3);
      executeAssertions(PARAMETERS, actual.get(0));
   }

   @Test
   @Order(3)
   public void getByMatricula() throws Exception {
      var actual = service.getByMatricula(MATRICULA);
      executeAssertions(PARAMETERS, actual);
   }

   @Test
   @Order(4)
   public void getAlunosByCurso() throws Exception {
      var actual = service.getAlunosByCurso(1);

      Assertions.assertTrue(actual.size() == 3);
      Assertions.assertEquals(NOME, actual.get(0).getNome());
      Assertions.assertEquals(MATRICULA, actual.get(0).getMatricula());
      Assertions.assertEquals("Ativo", actual.get(0).getEstado());
   }

   @Test
   @Order(5)
   public void create() throws Exception {
      var request = new AlunoCursoRequestDTO(NOME_1, CPF, CURSO);
      var response = service.create(request);
      var actual = service.getById(4);
      var substrings = response.getTexto().split(":");
      String[] parameters = { NOME_1, CPF, substrings[2].trim(), CURSO };

      executeAssertions(parameters, actual);
   }

   @Test
   @Order(6)
   public void update() throws Exception {
      var request = new AlunoRequestDTO(NOME_1, CPF);
      var response = service.updateCadastro(MATRICULA, request);
      var actual = service.getById(1);
      PARAMETERS[0] = NOME_1;

      Assertions.assertEquals("Cadastro atualizado com sucesso.", response.getTexto());
      executeAssertions(PARAMETERS, actual);
   }

   @Test
   @Order(7)
   public void rematricular() throws Exception {
      var response = service.rematricularAluno("1010-300");

      Assertions.assertEquals("Rematrícula realizada com sucesso.", response.getTexto());
   }

   @Test
   @Order(8)
   public void cancelarMatricula() throws Exception {
      var response = service.cancelarMatricula(MATRICULA);

      Assertions.assertEquals("Matrícula: " + MATRICULA + " cancelada!", response.getTexto());
   }

   private void executeAssertions(String[] parameters, AlunoResponseDTO aluno) {
      Assertions.assertEquals(parameters[0], aluno.getNome());
      Assertions.assertEquals(parameters[1], aluno.getCpf());
      Assertions.assertEquals(parameters[2], aluno.getMatricula());
      Assertions.assertEquals(parameters[3], aluno.getCurso());
   }
}
