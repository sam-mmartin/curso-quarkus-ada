package teste.aluno;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import teste.application.dto.Mensagem;
import teste.application.dto.aluno.AlunoCursoRequestDTO;
import teste.application.dto.aluno.AlunoRequestDTO;
import teste.application.dto.aluno.AlunoResponseDTO;
import teste.application.dto.aluno.AlunosByCursoResponseDTO;
import teste.application.exceptions.CustomConstraintException;
import teste.application.exceptions.NotFoundException;
import teste.application.interfaces.mapper.AlunoMapper;
import teste.application.services.AlunoService;
import teste.domain.VOs.matricula.Matricula;
import teste.domain.aluno.Aluno;
import teste.domain.curso.Curso;
import teste.infrastructure.aluno.AlunoRepositoryJDBC;
import teste.infrastructure.curso.CursoRepositoryJDBC;

public class AlunoServiceTest {

   private static final int ID = 1;

   private Aluno entity1 = Mockito.mock(Aluno.class);
   private Aluno entity2 = Mockito.mock(Aluno.class);
   private AlunoResponseDTO response1 = Mockito.mock(AlunoResponseDTO.class);
   private AlunoResponseDTO response2 = Mockito.mock(AlunoResponseDTO.class);
   private AlunosByCursoResponseDTO response3 = Mockito.mock(AlunosByCursoResponseDTO.class);
   private AlunosByCursoResponseDTO response4 = Mockito.mock(AlunosByCursoResponseDTO.class);

   private AlunoRepositoryJDBC repository = Mockito.mock(AlunoRepositoryJDBC.class);
   private AlunoMapper mapper = Mockito.mock(AlunoMapper.class);
   private CursoRepositoryJDBC cursoRepository = Mockito.mock(CursoRepositoryJDBC.class);
   private AlunoService service = new AlunoService(repository, cursoRepository, mapper);

   @Test
   void testGetById() throws Exception {
      given(repository.buscarPorId(ID)).willReturn(entity1);
      given(mapper.toResource(entity1)).willReturn(response1);

      var actual = service.getById(ID);
      assertEquals(response1, actual);
   }

   @Test
   void testGetByIdNotFound() throws Exception {
      assertThrows(NotFoundException.class,
            () -> service.getById(ID),
            "Aluno não encontrado!");
   }

   @Test
   void testGetAll() throws Exception {
      var listOfEntities = List.of(entity1, entity2);
      var listOfResponses = List.of(response1, response2);

      given(repository.listarTodos()).willReturn(listOfEntities);
      given(mapper.listToResource(listOfEntities)).willReturn(listOfResponses);

      var actual = service.getAll();

      assertEquals(2, actual.size());
      assertEquals(listOfResponses, actual);
   }

   @Test
   void testGetByMatricula() throws Exception {
      given(repository.buscarPorMatricula("0000")).willReturn(entity1);
      given(mapper.toResource(entity1)).willReturn(response1);

      var actual = service.getByMatricula("0000");

      assertEquals(response1, actual);
   }

   @Test
   void testGetAlunosByCurso() throws Exception {
      var listOfEntities = List.of(entity1, entity2);
      var listOfResponses = List.of(response3, response4);

      given(repository.listarAlunosPorCurso(1)).willReturn(listOfEntities);
      given(mapper.listToResourceByCurso(listOfEntities)).willReturn(listOfResponses);

      var actual = service.getAlunosByCurso(1);

      assertEquals(2, actual.size());
      assertEquals(listOfResponses, actual);
   }

   @Test
   void testCreate() throws Exception {
      var request = Mockito.mock(AlunoCursoRequestDTO.class);
      var entity = criaAluno();

      var response = new Mensagem(
            "Aluno: " + entity.getNome()
                  + " matriculado com sucesso no curso " + entity.getCursoMatriculado().getNomeDoCurso()
                  + ". Nº de matricula: 0000");

      given(mapper.toEntityCreate(request)).willReturn(entity);
      given(cursoRepository.buscarPorNomeDoCurso(request.getCurso())).willReturn(entity.getCursoMatriculado());

      var actual = service.create(request);

      assertEquals(response.getTexto(), actual.getTexto());
      verify(repository, times(1)).matricular(entity);
   }

   @Test
   void testUpdate() throws Exception {
      var request = Mockito.mock(AlunoRequestDTO.class);
      var expected = new Mensagem("Cadastro atualizado com sucesso.");

      given(repository.buscarPorMatricula("0000")).willReturn(entity1);

      var actual = service.updateCadastro("0000", request);

      assertEquals(expected.getTexto(), actual.getTexto());
      verify(repository, times(1)).atualizarCadastroDoAluno(entity1);
   }

   @Test
   void testUpdateAlunoNotFound() throws Exception {
      var request = Mockito.mock(AlunoRequestDTO.class);

      assertThrows(NotFoundException.class,
            () -> service.updateCadastro("0000", request),
            "Aluno não encontrado!");
      verify(repository, never()).atualizarCadastroDoAluno(any(Aluno.class));
   }

   @Test
   void testRematricular() throws Exception {
      var expected = new Mensagem("Rematrícula realizada com sucesso.");

      given(repository.buscarPorMatricula("0000")).willReturn(entity1);

      var actual = service.rematricularAluno("0000");

      assertEquals(expected.getTexto(), actual.getTexto());
      verify(repository, times(1)).rematricular(entity1);
   }

   @Test
   void testAlunoNaoPodeSerRematriculadoPorqueJaEstaMatriculado() throws Exception {
      var expected = new CustomConstraintException("Aluno já está matriculado!");

      given(repository.buscarPorMatricula("0000")).willReturn(entity1);
      given(entity1.isEstado()).willReturn(true);

      assertThrows(CustomConstraintException.class,
            () -> service.rematricularAluno("0000"),
            expected.getMessage());

      verify(repository, never()).rematricular(any(Aluno.class));
   }

   @Test
   void testAlunoNaoPodeSerRematriculadoPorqueMatriculaNaoFoiEncontrada() throws Exception {
      assertThrows(NotFoundException.class,
            () -> service.rematricularAluno("0000"),
            "Matrícula não encontrada!");

      verify(repository, never()).rematricular(any(Aluno.class));
   }

   @Test
   void testCancelarMatricula() throws Exception {
      var expected = new Mensagem("Matrícula: 0000 cancelada!");
      var entity = criaAluno();

      given(repository.buscarPorMatricula("0000")).willReturn(entity);

      var actual = service.cancelarMatricula("0000");

      assertEquals(expected.getTexto(), actual.getTexto());
      verify(repository, times(1)).cancelarMatricula(entity);
   }

   @Test
   void testMatriculaNaoPodeSerCanceladaPorqueAlunoNaoEstaMatriculado() throws Exception {
      given(repository.buscarPorMatricula("0000")).willReturn(entity1);

      assertThrows(CustomConstraintException.class,
            () -> service.cancelarMatricula("0000"),
            "Aluno: " + entity1.getNome() + " não está matriculado!");

      verify(repository, never()).cancelarMatricula(any(Aluno.class));
   }

   @Test
   void testMatriculaNaoPodeSerCanceladaPorqueMatriculaNaoFoiEncontrada() throws Exception {
      assertThrows(NotFoundException.class,
            () -> service.cancelarMatricula("0000"),
            "Matrícula não encontrada!");

      verify(repository, never()).cancelarMatricula(any(Aluno.class));
   }

   private Aluno criaAluno() {
      var curso = new Curso("Curso");
      var entity = new Aluno();
      entity.setCursoMatriculado(curso);
      entity.setMatricula(new Matricula("0000"));
      entity.setEstado(true);
      return entity;
   }
}
