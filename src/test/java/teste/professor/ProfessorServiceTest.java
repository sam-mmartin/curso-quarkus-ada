package teste.professor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import teste.application.dto.curso.CursoRequestDTO;
import teste.application.dto.disciplina.DisciplinaRequestDTO;
import teste.application.dto.professor.ProfessorMasterInfosResponseDTO;
import teste.application.dto.professor.ProfessorPersonInfosResponseDTO;
import teste.application.dto.professor.ProfessorRequestDTO;
import teste.application.exceptions.NotFoundException;
import teste.application.interfaces.mapper.ProfessorMapper;
import teste.application.services.ProfessorService;
import teste.domain.VOs.matricula.Matricula;
import teste.domain.curso.Curso;
import teste.domain.disciplina.Disciplina;
import teste.domain.mappeamento.ProfessorDisciplina;
import teste.domain.professor.Professor;
import teste.infrastructure.MatriculaResource;
import teste.infrastructure.curso.CursoRepositoryJDBC;
import teste.infrastructure.disciplina.DisciplinaRepositoryJDBC;
import teste.infrastructure.mapeamento.CursoProfessorRepository;
import teste.infrastructure.mapeamento.ProfessorDisciplinaRepository;
import teste.infrastructure.professor.ProfessorRepositoryJDBC;

public class ProfessorServiceTest {

   private static final int ID = 1;
   private DisciplinaRequestDTO disciplinaReq = Mockito.mock(DisciplinaRequestDTO.class);

   private Professor entity1 = Mockito.mock(Professor.class);
   private Professor entity2 = Mockito.mock(Professor.class);

   private Disciplina disciplina = Mockito.mock(Disciplina.class);

   private ProfessorPersonInfosResponseDTO responsePI_1 = Mockito.mock(ProfessorPersonInfosResponseDTO.class);
   private ProfessorPersonInfosResponseDTO responsePI_2 = Mockito.mock(ProfessorPersonInfosResponseDTO.class);
   private ProfessorMasterInfosResponseDTO responseMI = Mockito.mock(ProfessorMasterInfosResponseDTO.class);

   private ProfessorRepositoryJDBC repository = Mockito.mock(ProfessorRepositoryJDBC.class);
   private DisciplinaRepositoryJDBC repositoryDisciplina = Mockito.mock(DisciplinaRepositoryJDBC.class);
   private CursoRepositoryJDBC cursoRepository = Mockito.mock(CursoRepositoryJDBC.class);
   private CursoProfessorRepository cursoProfessor = Mockito.mock(CursoProfessorRepository.class);
   private ProfessorDisciplinaRepository rpd = Mockito.mock(ProfessorDisciplinaRepository.class);
   private ProfessorMapper mapper = Mockito.mock(ProfessorMapper.class);
   private MatriculaResource mr = Mockito.mock(MatriculaResource.class);
   private Validator validator = Mockito.mock(Validator.class);

   private ProfessorService service = new ProfessorService(repository, repositoryDisciplina, cursoRepository,
         cursoProfessor, rpd, mapper, mr, validator);

   @Test
   void testGetById() throws Exception {
      given(repository.buscarPorId(ID)).willReturn(entity1);
      given(mapper.toResource(entity1)).willReturn(responsePI_1);

      var actual = service.getById(ID);
      assertEquals(responsePI_1, actual);
   }

   @Test
   void testGetByIdNotFound() throws Exception {
      given(repository.buscarPorId(ID)).willThrow(NoResultException.class);

      assertThrows(NoResultException.class,
            () -> service.getById(ID));
   }

   @Test
   void testGetAll() throws Exception {
      var listOfEntities = List.of(entity1, entity2);
      var listOfResposes = List.of(responsePI_1, responsePI_2);

      given(repository.listarTodos()).willReturn(listOfEntities);
      given(mapper.listToResource(listOfEntities)).willReturn(listOfResposes);

      var actual = service.getAll();

      assertEquals(2, actual.size());
      assertEquals(listOfResposes, actual);
   }

   @Test
   void testGetByMatricula() throws Exception {
      given(repository.buscarPorMatricula("0000")).willReturn(entity1);
      given(mapper.toResourceWithCursos(entity1)).willReturn(responseMI);

      var actual = service.getByMatricula("0000");

      assertEquals(responseMI, actual);
   }

   @Test
   void testGetByMatriculaNotFound() throws Exception {
      assertThrows(NotFoundException.class,
            () -> {
               given(repository.buscarPorMatricula("0000")).willReturn(null);
               service.getByMatricula("0000");
            },
            "Professor não encontrado! Verifique a matrícula informada.");
   }

   @Test
   void testCreate() throws Exception {
      var request = Mockito.mock(ProfessorRequestDTO.class);
      var entity = professorFactory();
      var response = new ProfessorPersonInfosResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getCpf(),
            "0000",
            "Ativo",
            entity.getDataCriacao().toString(),
            entity.getDataAtualizacao().toString());

      given(mapper.toEntity(request)).willReturn(entity);
      given(repository.contratar(entity)).willReturn(entity);
      given(mapper.toResource(entity)).willReturn(response);

      var actual = service.create(request);

      assertEquals(response, actual);
      verify(repository, times(1)).contratar(entity);
   }

   @ParameterizedTest
   @MethodSource("invalidFields")
   void testCreateInvalidFields(final String nome, final String cpf, final String error) throws Exception {
      var request = new ProfessorRequestDTO(nome, cpf);

      given(validator.validate(request)).willThrow(ConstraintViolationException.class);

      assertThrows(ConstraintViolationException.class,
            () -> service.create(request),
            error);

      verify(repository, never()).contratar(any(Professor.class));
   }

   @Test
   void testUpdateCadastro() throws Exception {
      var request = Mockito.mock(ProfessorRequestDTO.class);

      given(repository.buscarPorMatricula("0000")).willReturn(entity1);
      given(repository.atualizarProfessor(entity1)).willReturn(entity1);
      given(mapper.toResource(entity1)).willReturn(responsePI_1);

      var actual = service.updateCadastro("0000", request);

      assertEquals(responsePI_1, actual);
      verify(repository, times(1)).atualizarProfessor(entity1);
   }

   @Test
   void testUpdateCadastroNotFound() throws Exception {
      assertThrows(NoResultException.class,
            () -> {
               given(repository.buscarPorMatricula("0000")).willThrow(NoResultException.class);
               var request = Mockito.mock(ProfessorRequestDTO.class);
               service.updateCadastro("0000", request);
            },
            "No entity found for query");

      verify(repository, never()).atualizarProfessor(any(Professor.class));
   }

   @ParameterizedTest
   @MethodSource("invalidFields")
   void testUpdateCadastroInvalidFields(final String nome, final String cpf, final String error) throws Exception {
      assertThrows(ConstraintViolationException.class,
            () -> {
               var request = new ProfessorRequestDTO(nome, cpf);
               given(validator.validate(request)).willThrow(ConstraintViolationException.class);
               service.updateCadastro("0000", request);
            },
            error);

      verify(repository, never()).atualizarProfessor(any(Professor.class));
   }

   @Test
   void testTeachDiscipline() throws Exception {
      given(repository.buscarPorMatricula("0000")).willReturn(entity1);
      given(repositoryDisciplina.buscarPorDisciplina("null")).willReturn(disciplina);
      given(repository.atualizarProfessor(entity1)).willReturn(entity1);
      given(mapper.toResourceWithCursos(entity1)).willReturn(responseMI);

      var actual = service.teachDiscipline("0000", disciplinaReq);

      assertEquals(responseMI, actual);
      verify(repository, times(1)).atualizarProfessor(entity1);
   }

   @Test
   void testTeachDisciplineProfessorNotFound() {
      assertThrows(NotFoundException.class,
            () -> {
               given(repository.buscarPorMatricula("0000")).willReturn(null);
               service.teachDiscipline("0000", disciplinaReq);
            },
            "Professor não encontrado!");
   }

   @Test
   void testTeachDisciplineDisciplinaNotFound() {
      assertThrows(NotFoundException.class,
            () -> {
               given(repository.buscarPorMatricula("0000")).willReturn(entity1);
               given(repositoryDisciplina.buscarPorDisciplina(disciplinaReq.getNomeDaDisciplina())).willReturn(null);

               service.teachDiscipline("0000", disciplinaReq);
            },
            "Disciplina não encontrada!");
   }

   @Test
   void testStopTeachingDiscipline() throws Exception {
      var entity = Mockito.mock(ProfessorDisciplina.class);

      given(repository.buscarPorMatricula("0000")).willReturn(entity1);
      given(repositoryDisciplina.buscarPorDisciplina(disciplina.getNomeDaDisciplina())).willReturn(disciplina);
      given(rpd.buscarPorProfessorEDisciplina(0, 0)).willReturn(entity);
      given(repository.atualizarProfessor(entity1)).willReturn(entity1);
      given(mapper.toResourceWithCursos(entity1)).willReturn(responseMI);

      var actual = service.stopTeachingDiscipline("0000", disciplinaReq);

      assertEquals(responseMI, actual);
      verify(repository, times(1)).atualizarProfessor(entity1);
      verify(rpd, times(1)).delete(entity);
   }

   @Test
   void testStopTeachingDisciplineProfessorNotFound() throws Exception {
      assertThrows(NotFoundException.class,
            () -> {
               given(repository.buscarPorMatricula("0000")).willReturn(null);
               service.stopTeachingDiscipline("0000", disciplinaReq);
            },
            "Professor não encontrado! Verifique a matrícula informada.");
      verify(repository, never()).atualizarProfessor(any(Professor.class));
      verify(rpd, never()).delete(any(ProfessorDisciplina.class));
   }

   @Test
   void testStopTeachingDisciplineDisciplinaNotFound() throws Exception {
      assertThrows(NotFoundException.class,
            () -> {
               given(repository.buscarPorMatricula("0000")).willReturn(entity1);
               given(repositoryDisciplina.buscarPorDisciplina("null")).willReturn(null);
               service.stopTeachingDiscipline("0000", disciplinaReq);
            },
            "Disciplina não encontrada!");
      verify(repository, never()).atualizarProfessor(any(Professor.class));
      verify(rpd, never()).delete(any(ProfessorDisciplina.class));
   }

   @Test
   void testStopTeachingDisciplineProfessorNaoLecionaDisciplina() throws Exception {
      assertThrows(NotFoundException.class,
            () -> {
               given(repository.buscarPorMatricula("0000")).willReturn(entity1);
               given(repositoryDisciplina.buscarPorDisciplina("null")).willReturn(disciplina);
               given(rpd.buscarPorProfessorEDisciplina(0, 0)).willReturn(null);
               service.stopTeachingDiscipline("0000", disciplinaReq);
            },
            "Professor não leciona a disciplina informada!");
      verify(repository, never()).atualizarProfessor(any(Professor.class));
      verify(rpd, never()).delete(any(ProfessorDisciplina.class));
   }

   // @Test
   // void addCurso() throws Exception {
   // var curso = Mockito.mock(Curso.class);
   // var req = Mockito.mock(CursoRequestDTO.class);

   // given(repository.buscarPorMatricula("0000")).willReturn(entity1);
   // given(cursoRepository.buscarPorNomeDoCurso("null")).willReturn(curso);
   // given(mapper.toResourceWithCursos(entity1)).willReturn(responseMI);

   // var actual = service.addCurso("0000", req);

   // assertEquals(responseMI, actual);
   // }

   static Stream<Arguments> invalidFields() {
      return Stream.of(
            arguments(null, "546.212.310-81", "O nome é obrigatório"),
            arguments("   ", "546.212.310-81", "O nome é obrigatório"),
            arguments("NOME", "", "É necessário informar o CPF"),
            arguments("NOME", null, "É necessário informar o CPF"));
   }

   private Professor professorFactory() {
      var professor = new Professor();
      professor.setId(ID);
      professor.setNome("NOME");
      professor.setCpf("546.212.310-81");
      professor.setMatricula(new Matricula("0000"));
      professor.setEstado(true);
      professor.setDataCriacao(LocalDateTime.now());
      professor.setDataAtualizacao(LocalDateTime.now());
      professor.setObservacao("Observação");

      return professor;
   }
}
