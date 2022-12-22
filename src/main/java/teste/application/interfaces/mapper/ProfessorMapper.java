package teste.application.interfaces.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import teste.application.dto.professor.ProfessorRequestDTO;
import teste.application.dto.professor.ProfessorResponseDTO;
import teste.domain.professor.Professor;

@Mapper(config = QuarkusMappingConfig.class)
public interface ProfessorMapper {

   @Mapping(target = "matricula", source = "matricula.numero")
   ProfessorResponseDTO toResource(Professor professor);

   List<ProfessorResponseDTO> listToResource(List<Professor> professores);

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "matricula", ignore = true)
   @Mapping(target = "status", ignore = true)
   Professor toEntity(ProfessorRequestDTO professorDTO);
}
