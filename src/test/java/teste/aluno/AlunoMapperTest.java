package teste.aluno;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import teste.application.dto.aluno.AlunoResponseDTO;
import teste.application.dto.aluno.AlunosByCursoResponseDTO;
import teste.application.interfaces.mapper.AlunoMapper;
import teste.application.interfaces.mapper.AlunoMapperImpl;
import teste.domain.VOs.matricula.Matricula;
import teste.domain.aluno.Aluno;
import teste.domain.curso.Curso;

public class AlunoMapperTest {

   private static final int ID_1 = 1;
   private static final int ID_2 = 2;
   private static final String NOME_1 = "Unknow Someone 1";
   private static final String NOME_2 = "Unknow Someone 2";
   private static final String CPF_1 = "384.777.090-09";
   private static final String CPF_2 = "384.777.090-09";
   private static final String MATRICULA_1 = "9999-09";
   private static final String MATRICULA_2 = "9999-10";
   private static final boolean ESTADO = true;
   private static final LocalDateTime DATA = LocalDateTime.now();
   private static final String DATAFORMATTER = DATA.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
   private static final Curso curso = new Curso("CURSO 1");
   private static final Aluno entity_1 = new Aluno(NOME_1, CPF_1, curso);
   private static final Aluno entity_2 = new Aluno(NOME_2, CPF_2, curso);

   private static final AlunoResponseDTO response_1 = new AlunoResponseDTO(
         ID_1, NOME_1, CPF_1, MATRICULA_1, "Ativo", "CURSO 1", DATAFORMATTER, DATAFORMATTER);
   private static final AlunoResponseDTO response_2 = new AlunoResponseDTO(
         ID_2, NOME_2, CPF_2, MATRICULA_2, "Ativo", "CURSO 1", DATAFORMATTER, DATAFORMATTER);

   private static final AlunosByCursoResponseDTO response_3 = new AlunosByCursoResponseDTO(
         NOME_1, MATRICULA_1, "Ativo");
   private static final AlunosByCursoResponseDTO response_4 = new AlunosByCursoResponseDTO(
         NOME_2, MATRICULA_2, "Ativo");

   private final AlunoMapper mapper = new AlunoMapperImpl();

   @Test
   public void toResource() {
      buildAluno();
      var actual = mapper.toResource(entity_1);
      Assertions.assertTrue(response_1.equals(actual));
   }

   @Test
   public void listToResource() {
      buildAluno();
      var list = List.of(entity_1, entity_2);
      var listResponse = List.of(response_1, response_2);
      var actual = mapper.listToResource(list);

      Assertions.assertTrue(actual.containsAll(listResponse));
   }

   @Test
   public void toResourceByCurso() {
      buildAluno();
      var actual = mapper.toResourceByCurso(entity_1);
      Assertions.assertTrue(response_3.equals(actual));
   }

   @Test
   public void listToResourceByCurso() {
      buildAluno();
      var list = List.of(entity_1, entity_2);
      var listResponse = List.of(response_3, response_4);
      var actual = mapper.listToResourceByCurso(list);

      Assertions.assertTrue(actual.containsAll(listResponse));
   }

   private void buildAluno() {
      entity_1.setId(ID_1);
      entity_1.setMatricula(new Matricula(MATRICULA_1));
      entity_1.setEstado(ESTADO);
      entity_1.setDataCriacao(DATA);
      entity_1.setDataAtualizacao(DATA);

      entity_2.setId(ID_2);
      entity_2.setMatricula(new Matricula(MATRICULA_2));
      entity_2.setEstado(ESTADO);
      entity_2.setDataCriacao(DATA);
      entity_2.setDataAtualizacao(DATA);
   }
}
