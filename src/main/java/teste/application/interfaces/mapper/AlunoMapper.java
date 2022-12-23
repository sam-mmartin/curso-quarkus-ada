package teste.application.interfaces.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import teste.application.dto.aluno.AlunoRequestDTO;
import teste.application.dto.aluno.AlunoResponseDTO;
import teste.domain.aluno.Aluno;

@Mapper(config = QuarkusMappingConfig.class)
public interface AlunoMapper {

   @Mapping(target = "matricula", source = "matricula.numero")
   @Mapping(target = "status", source = "estado")
   AlunoResponseDTO toResource(Aluno aluno);

   List<AlunoResponseDTO> listToResource(List<Aluno> alunos);

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "matricula", ignore = true)
   @Mapping(target = "estado", ignore = true)
   Aluno toEntity(AlunoRequestDTO alunoDTO);
}
