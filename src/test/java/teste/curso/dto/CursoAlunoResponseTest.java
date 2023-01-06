package teste.curso.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import teste.application.dto.aluno.AlunosByCursoResponseDTO;
import teste.application.dto.curso.CursoAlunosResponseDTO;

public class CursoAlunoResponseTest {

   private static final long id = 1;
   private static final String nome = "NOME";
   private static final List<AlunosByCursoResponseDTO> alunos = new ArrayList<>() {
      {
         add(new AlunosByCursoResponseDTO("NOME", "MATRICULA", "ESTADO"));
      }
   };

   @Test
   void testConstructorAllArgs() {
      var response = new CursoAlunosResponseDTO(
            id, nome, alunos);

      executeAssertions(response);
   }

   @Test
   void testConstructorDefault() {
      var response = new CursoAlunosResponseDTO();
      response.setId(id);
      response.setNomeDoCurso(nome);
      response.setAlunosMatriculados(alunos);
   }

   @Test
   void testEqualsAndHashCode() {
      var res_1 = new CursoAlunosResponseDTO(
            id, nome, alunos);
      var res_2 = new CursoAlunosResponseDTO(
            id, nome, alunos);

      assertEquals(res_1, res_2);
      assertEquals(res_1.hashCode(), res_2.hashCode());

   }

   private void executeAssertions(CursoAlunosResponseDTO response) {
      assertEquals(id, response.getId());
      assertEquals(nome, response.getNomeDoCurso());
      assertTrue(response.getAlunosMatriculados().containsAll(alunos));
   }
}
