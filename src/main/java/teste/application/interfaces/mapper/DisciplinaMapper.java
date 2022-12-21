package teste.application.interfaces.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import teste.application.dto.disciplina.DisciplinaResponseDTO;
import teste.domain.disciplina.Disciplina;

@Mapper(config = QuarkusMappingConfig.class)
public interface DisciplinaMapper {

   DisciplinaResponseDTO toResource(Disciplina disciplina);

   List<DisciplinaResponseDTO> listToResource(List<Disciplina> disciplinas);

}
