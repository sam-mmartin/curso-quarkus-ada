package teste.application.interfaces.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import teste.application.dto.curso.CursoDisciplinaResponseDTO;
import teste.domain.curso.Curso;

@Mapper(config = QuarkusMappingConfig.class)
public interface CursoDisciplinaMapper {

   CursoDisciplinaResponseDTO toResource(Curso curso);

   List<CursoDisciplinaResponseDTO> listToResource(List<Curso> cursos);
}
