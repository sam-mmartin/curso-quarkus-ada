package teste.application.services;

import java.util.List;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import teste.application.dto.Mensagem;
import teste.application.dto.aluno.AlunoCursoRequestDTO;
import teste.application.dto.aluno.AlunoRequestDTO;
import teste.application.dto.aluno.AlunoResponseDTO;
import teste.application.dto.aluno.AlunosByCursoResponseDTO;
import teste.application.exceptions.CustomConstraintException;
import teste.application.exceptions.ErrorResponse;
import teste.application.interfaces.mapper.AlunoMapper;
import teste.application.interfaces.services.ServiceAluno;
import teste.application.interfaces.services.ServiceGenerics;
import teste.application.interfaces.services.ServiceCadastroMatricula;
import teste.domain.aluno.Aluno;
import teste.domain.curso.Curso;
import teste.infrastructure.aluno.AlunoRepositoryJDBC;
import teste.infrastructure.curso.CursoRepositoryJDBC;

@RequestScoped
public class AlunoService implements ServiceAluno, ServiceGenerics<AlunoResponseDTO, AlunoCursoRequestDTO>,
      ServiceCadastroMatricula<AlunoResponseDTO, AlunoRequestDTO> {

   @Inject
   AlunoRepositoryJDBC repositorio;

   @Inject
   CursoRepositoryJDBC cursoRepositorio;

   @Inject
   AlunoMapper alunoMapper;

   @Override
   public AlunoResponseDTO getById(int id) throws Exception {
      Aluno aluno = repositorio.buscarPorId(id);

      if (Objects.isNull(aluno)) {
         return null;
      }

      return alunoMapper.toResource(aluno);
   }

   @Override
   public List<AlunoResponseDTO> getAll() throws Exception {
      return alunoMapper.listToResource(repositorio.listarTodos());
   }

   @Override
   public AlunoResponseDTO getByMatricula(String matricula) throws Exception {
      Aluno aluno = repositorio.buscarPorMatricula(matricula);

      if (Objects.isNull(aluno)) {
         return null;
      }

      return alunoMapper.toResource(aluno);
   }

   @Override
   public List<AlunosByCursoResponseDTO> getAlunosByCurso(int id) throws Exception {
      List<Aluno> alunosFromCurso = repositorio.listarAlunosPorCurso(id);

      return alunoMapper.listToResourceByCurso(alunosFromCurso);
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   @Valid
   public Mensagem create(AlunoCursoRequestDTO alunoDTO) throws Exception {
      Mensagem mensagem;

      try {
         Aluno novo = alunoMapper.toEntityCreate(alunoDTO);
         Curso curso = cursoRepositorio.buscarPorNomeDoCurso(alunoDTO.getCurso());
         novo.setCursoMatriculado(curso);

         repositorio.matricular(novo);
         mensagem = new Mensagem(
               "Aluno: " + novo.getNome()
                     + " matriculado com sucesso no curso " + curso.getNomeDoCurso()
                     + ". Nº de matricula: " + novo.getMatricula().getNumero());

      } catch (ConstraintViolationException e) {
         ErrorResponse result = new ErrorResponse(e.getConstraintViolations());
         mensagem = new Mensagem(result.getMessage());
      }

      return mensagem;
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   @Valid
   public Mensagem updateCadastro(String matricula, AlunoRequestDTO alunoDTO) throws Exception {
      Mensagem mensagem;

      try {
         Aluno aluno = repositorio.buscarPorMatricula(matricula);
         aluno.setNome(alunoDTO.getNome());
         aluno.setCpf(alunoDTO.getCpf());

         repositorio.atualizarCadastroDoAluno(aluno);
         mensagem = new Mensagem("Cadastro atualizado com sucesso.");
      } catch (ConstraintViolationException e) {
         ErrorResponse result = new ErrorResponse(e.getConstraintViolations());
         mensagem = new Mensagem(result.getMessage());
      }

      return mensagem;
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public Mensagem rematricularAluno(String matricula) throws Exception {
      Aluno aluno = repositorio.buscarPorMatricula(matricula);
      Mensagem mensagem = new Mensagem();

      if (Objects.isNull(aluno)) {
         mensagem.setTexto("Matrícula não encontrada!");
      }

      if (aluno.isEstado()) {
         throw new CustomConstraintException("Aluno já está matriculado!");
      } else {
         repositorio.rematricular(aluno);
         mensagem.setTexto("Rematrícula realizada com sucesso.");
      }

      return mensagem;
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public Mensagem cancelarMatricula(String matricula) throws Exception {
      Aluno aluno = repositorio.buscarPorMatricula(matricula);
      Mensagem mensagem = new Mensagem();

      if (Objects.isNull(aluno)) {
         mensagem.setTexto("Matrícula não encontrada!");
      }

      if (aluno.isEstado()) {
         repositorio.cancelarMatricula(aluno);
         mensagem.setTexto("Matrícula: " + aluno.getMatricula().getNumero() + " cancelada!");
      } else {
         throw new CustomConstraintException("Aluno: " + aluno.getNome() + " não está matriculado!");
      }

      return mensagem;
   }

}
