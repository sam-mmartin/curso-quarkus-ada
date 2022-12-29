package teste.application.interfaces.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import teste.application.dto.aluno.AlunoCursoRequestDTO;
import teste.application.dto.aluno.AlunoRequestDTO;
import teste.application.dto.aluno.AlunoResponseDTO;
import teste.application.dto.aluno.AlunosByCursoResponseDTO;
import teste.domain.aluno.Aluno;

@Mapper(config = QuarkusMappingConfig.class)
public interface AlunoMapper {

   @Mapping(target = "matricula", source = "matricula.numero")
   @Mapping(target = "estado", expression = "java(isStatus(aluno.isEstado()))")
   @Mapping(target = "curso", source = "cursoMatriculado.nomeDoCurso")
   @Mapping(target = "dataCriacao", expression = "java(dateTime(aluno.getDataCriacao()))")
   @Mapping(target = "dataAtualizacao", expression = "java(dateTime(aluno.getDataAtualizacao()))")
   AlunoResponseDTO toResource(Aluno aluno);

   @Mapping(target = "matricula", source = "matricula.numero")
   @Mapping(target = "estado", expression = "java(isStatus(aluno.isEstado()))")
   AlunosByCursoResponseDTO toResourceByCurso(Aluno aluno);

   List<AlunoResponseDTO> listToResource(List<Aluno> alunos);

   List<AlunosByCursoResponseDTO> listToResourceByCurso(List<Aluno> alunos);

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "matricula", ignore = true)
   @Mapping(target = "estado", ignore = true)
   @Mapping(target = "cursoMatriculado", ignore = true)
   @Mapping(target = "dataCriacao", ignore = true)
   @Mapping(target = "dataAtualizacao", ignore = true)
   Aluno toEntityUpdate(AlunoRequestDTO alunoDTO);

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "matricula", ignore = true)
   @Mapping(target = "estado", ignore = true)
   @Mapping(target = "cursoMatriculado", ignore = true)
   @Mapping(target = "dataCriacao", ignore = true)
   @Mapping(target = "dataAtualizacao", ignore = true)
   Aluno toEntityCreate(AlunoCursoRequestDTO alunoDTO);

   default String isStatus(boolean estado) {
      if (estado) {
         return "Ativo";
      } else {
         return "Inativo";
      }
   }

   default String dateTime(LocalDateTime date) {
      return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
   }
}
