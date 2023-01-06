package teste.professor.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import teste.application.dto.professor.ProfessorPersonInfosResponseDTO;

public class ProfessorPersonInfoResponseTest {

   private static final int id = 1;
   private static final String nome = "Unknow 2";
   private static final String cpf = "830.173.730-10";
   private static final String matricula = "1000-000";
   private static final String estado = "ativo";
   private static final String dataCriacao = "27/12/2022 18:00:58";
   private static final String dataAtualizacao = "27/12/2022 18:00:5";

   @Test
   void constructorAllArgs() {
      var response = new ProfessorPersonInfosResponseDTO(
            id, nome, cpf, matricula, estado, dataCriacao, dataAtualizacao);

      executeAssertions(response);
   }

   @Test
   void constructorDefault() {
      var response = new ProfessorPersonInfosResponseDTO();
      response.setId(id);
      response.setNome(nome);
      response.setCpf(cpf);
      response.setMatricula(matricula);
      response.setEstado(estado);
      response.setDataCriacao(dataCriacao);
      response.setDataAtualizacao(dataAtualizacao);
   }

   @Test
   void equalsAndHashCode() {
      var res_1 = new ProfessorPersonInfosResponseDTO(
            id, nome, cpf, matricula, estado, dataCriacao, dataAtualizacao);
      var res_2 = new ProfessorPersonInfosResponseDTO(
            id, nome, cpf, matricula, estado, dataCriacao, dataAtualizacao);

      assertEquals(res_1, res_2);
      assertEquals(res_1.hashCode(), res_2.hashCode());
   }

   private void executeAssertions(ProfessorPersonInfosResponseDTO response) {
      assertEquals(id, response.getId());
      assertEquals(nome, response.getNome());
      assertEquals(cpf, response.getCpf());
      assertEquals(matricula, response.getMatricula());
      assertEquals(estado, response.getEstado());
      assertEquals(dataCriacao, response.getDataCriacao());
      assertEquals(dataAtualizacao, response.getDataAtualizacao());
   }
}
