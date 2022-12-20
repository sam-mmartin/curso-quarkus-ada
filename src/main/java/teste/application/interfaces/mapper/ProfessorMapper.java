package teste.application.interfaces.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import teste.application.dto.professor.ProfessorResponseDTO;
import teste.domain.professor.Professor;

@Mapper(config = QuarkusMappingConfig.class)
public interface ProfessorMapper {

   @Mapping(target = "cpf", source = "cpf.numero")
   @Mapping(target = "matricula", source = "matricula.numero")
   ProfessorResponseDTO toResource(Professor professor);

   List<ProfessorResponseDTO> listToResource(List<Professor> professores);
}
