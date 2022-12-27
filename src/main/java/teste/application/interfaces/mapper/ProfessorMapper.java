package teste.application.interfaces.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import teste.application.dto.mapeamento.NameResponseDTO;
import teste.application.dto.professor.ProfessorMasterInfosResponseDTO;
import teste.application.dto.professor.ProfessorRequestDTO;
import teste.application.dto.professor.ProfessorPersonInfosResponseDTO;
import teste.domain.mappeamento.CursoProfessor;
import teste.domain.mappeamento.ProfessorDisciplina;
import teste.domain.professor.Professor;

@Mapper(config = QuarkusMappingConfig.class)
public interface ProfessorMapper {

   @Mapping(target = "matricula", source = "matricula.numero")
   @Mapping(target = "estado", expression = "java(isStatus(professor.isEstado()))")
   ProfessorPersonInfosResponseDTO toResource(Professor professor);

   @Mapping(target = "matricula", source = "matricula.numero")
   @Mapping(target = "estado", expression = "java(isStatus(professor.isEstado()))")
   @Mapping(target = "cursos", expression = "java(mapToCursoProfessor(professor.getCursosLecionados()))")
   @Mapping(target = "disciplinas", expression = "java(mapToProfessorDisciplina(professor.getDisciplinasLecionadas()))")
   ProfessorMasterInfosResponseDTO toResourceWithCursos(Professor professor);

   List<ProfessorPersonInfosResponseDTO> listToResource(List<Professor> professores);

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "matricula", ignore = true)
   @Mapping(target = "estado", ignore = true)
   @Mapping(target = "cursosLecionados", ignore = true)
   @Mapping(target = "disciplinasLecionadas", ignore = true)
   @Mapping(target = "observacao", ignore = true)
   @Mapping(target = "dataCriacao", ignore = true)
   @Mapping(target = "dataAtualizacao", ignore = true)
   Professor toEntity(ProfessorRequestDTO professorDTO);

   @Mapping(target = "id", source = "curso.id")
   @Mapping(target = "name", source = "curso.nomeDoCurso")
   NameResponseDTO mapToCursoProfessor(CursoProfessor cursoProfessor);

   List<NameResponseDTO> mapToCursoProfessor(List<CursoProfessor> cursoProfessor);

   @Mapping(target = "id", source = "disciplina.id")
   @Mapping(target = "name", source = "disciplina.nomeDaDisciplina")
   NameResponseDTO mapToProfessorDisciplina(ProfessorDisciplina professorDisciplina);

   List<NameResponseDTO> mapToProfessorDisciplina(List<ProfessorDisciplina> professorDisciplinas);

   default String isStatus(boolean estado) {
      if (estado) {
         return "Ativo";
      } else {
         return "Inativo";
      }
   }
}
