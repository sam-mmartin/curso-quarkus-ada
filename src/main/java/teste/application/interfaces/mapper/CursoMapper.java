package teste.application.interfaces.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import teste.application.dto.curso.CursoProfessoresResponseDTO;
import teste.application.dto.curso.CursoResponseDTO;
import teste.application.dto.curso.GradeCurricularResponseDTO;
import teste.application.dto.mapeamento.NameResponseDTO;
import teste.application.dto.professor.ProfessorBasicInfoResponseDTO;
import teste.domain.curso.Curso;
import teste.domain.mappeamento.CursoDisciplina;
import teste.domain.mappeamento.CursoProfessor;

@Mapper(config = QuarkusMappingConfig.class)
public interface CursoMapper {

   CursoResponseDTO toResource(Curso curso);

   List<CursoResponseDTO> listToResource(List<Curso> cursos);

   @Mapping(target = "disciplinasDoCurso", expression = "java(mapToCursoDisciplina(curso.getDisciplinasDoCurso()))")
   GradeCurricularResponseDTO toResourceWithDisciplinas(Curso curso);

   List<GradeCurricularResponseDTO> listToResourceWithDisciplinas(List<Curso> cursos);

   @Mapping(target = "professoresDoCurso", expression = "java(mapToCursoProfessor(curso.getProfessoresLotados()))")
   CursoProfessoresResponseDTO toResourceWithProfessores(Curso curso);

   List<CursoProfessoresResponseDTO> listToResourceWithProfessores(List<Curso> cursos);

   // MAPs
   @Mapping(target = "id", source = "disciplina.id")
   @Mapping(target = "name", source = "disciplina.nomeDaDisciplina")
   NameResponseDTO mapToCursoDisciplina(CursoDisciplina cursoDisciplina);

   List<NameResponseDTO> mapToCursoDisciplina(List<CursoDisciplina> cursoDisciplinas);

   @Mapping(target = "nome", source = "professor.nome")
   @Mapping(target = "matricula", source = "professor.matricula.numero")
   ProfessorBasicInfoResponseDTO mapToCursoProfessor(CursoProfessor cursoProfessor);

   List<ProfessorBasicInfoResponseDTO> mapToCursoProfessor(List<CursoProfessor> cursoProfessores);

}
