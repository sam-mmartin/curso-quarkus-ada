package teste.application.interfaces.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import teste.application.dto.aluno.AlunoResponseDTO;
import teste.domain.aluno.Aluno;

@Mapper(config = QuarkusMappingConfig.class)
public interface AlunoMapper {

   @Mapping(target = "cpf", source = "cpf.numero")
   @Mapping(target = "matricula", source = "matricula.numero")
   AlunoResponseDTO toResource(Aluno aluno);

   List<AlunoResponseDTO> listToResource(List<Aluno> alunos);
}
