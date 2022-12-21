package teste.application.interfaces.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import teste.application.dto.curso.CursoResponseDTO;
import teste.domain.curso.Curso;

@Mapper(config = QuarkusMappingConfig.class)
public interface CursoMapper {

   CursoResponseDTO toResource(Curso curso);

   List<CursoResponseDTO> listToResource(List<Curso> cursos);
}
