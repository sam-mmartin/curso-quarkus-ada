package teste.professor.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import teste.application.dto.mapeamento.NameResponseDTO;
import teste.application.dto.professor.ProfessorMasterInfosResponseDTO;

public class ProfessorMasterInfoResponseTest {

   private static final String nome = "NOME";
   private static final String matricula = "MATRICULA";
   private static final String estado = "ATIVO";
   private static final List<NameResponseDTO> cursos = new ArrayList<>() {
      {
         add(new NameResponseDTO(1, "CURSO 1"));
         add(new NameResponseDTO(2, "CURSO 2"));
      }
   };
   private static final List<NameResponseDTO> disciplinas = new ArrayList<>() {
      {
         add(new NameResponseDTO(1, "DISCIPLINA 1"));
         add(new NameResponseDTO(2, "DISCIPLINA 2"));
      }
   };

   @Test
   void constructorAllArgs() {
      var response = new ProfessorMasterInfosResponseDTO(
            nome, matricula, estado, cursos, disciplinas);

      executeAssertions(response);
   }

   @Test
   void constructorDefault() {
      var response = new ProfessorMasterInfosResponseDTO();
      response.setNome(nome);
      response.setMatricula(matricula);
      response.setEstado(estado);
      response.setCursos(cursos);
      response.setDisciplinas(disciplinas);
   }

   @Test
   void equalsAndHashCode() {
      var res_1 = new ProfessorMasterInfosResponseDTO(
            nome, matricula, estado, cursos, disciplinas);
      var res_2 = new ProfessorMasterInfosResponseDTO(
            nome, matricula, estado, cursos, disciplinas);

      assertEquals(res_1, res_2);
      assertEquals(res_1.hashCode(), res_2.hashCode());
   }

   private void executeAssertions(ProfessorMasterInfosResponseDTO response) {
      assertEquals(nome, response.getNome());
      assertEquals(matricula, response.getMatricula());
      assertEquals(estado, response.getEstado());
      assertTrue(response.getCursos().containsAll(cursos));
      assertTrue(response.getDisciplinas().containsAll(disciplinas));
   }
}
