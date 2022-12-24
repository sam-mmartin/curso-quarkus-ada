package teste.application.services;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;

import teste.application.dto.Mensagem;
import teste.application.dto.curso.CursoRequestDTO;
import teste.application.dto.disciplina.DisciplinaRequestDTO;
import teste.application.dto.professor.ProfessorRequestDTO;
import teste.application.dto.professor.ProfessorResponseDTO;
import teste.application.exceptions.CustomConstraintException;
import teste.application.exceptions.ErrorResponse;
import teste.application.interfaces.mapper.ProfessorMapper;
import teste.application.interfaces.services.ServiceGenerics;
import teste.application.interfaces.services.ServiceCadastroMatricula;
import teste.domain.curso.Curso;
import teste.domain.disciplina.Disciplina;
import teste.domain.professor.Professor;
import teste.infrastructure.MatriculaResource;
import teste.infrastructure.curso.CursoRepositoryJDBC;
import teste.infrastructure.disciplina.DisciplinaRepositoryJDBC;
import teste.infrastructure.professor.ProfessorRepositoryJDBC;

@RequestScoped
public class ProfessorService implements ServiceGenerics<ProfessorResponseDTO, ProfessorRequestDTO>,
      ServiceCadastroMatricula<ProfessorResponseDTO, ProfessorRequestDTO> {

   @Inject
   ProfessorRepositoryJDBC repositorio;

   @Inject
   DisciplinaRepositoryJDBC disciplinaRepositorio;

   @Inject
   CursoRepositoryJDBC cursoRepositorio;

   @Inject
   ProfessorMapper professorMapper;

   @Inject
   MatriculaResource mr;

   @Inject
   Validator validator;

   @Override
   public ProfessorResponseDTO getById(int id) throws Exception {
      return professorMapper.toResource(repositorio.buscarPorId(id));
   }

   @Override
   public List<ProfessorResponseDTO> getAll() throws Exception {
      return professorMapper.listToResource(repositorio.listarTodos());
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   @Valid
   public Mensagem create(ProfessorRequestDTO professorDTO) throws Exception {
      try {
         Professor novo = professorMapper.toEntity(professorDTO);
         novo.setEstado(true);
         novo.setMatricula(mr.gerarMatricula());

         repositorio.contratar(novo);
         Mensagem mensagem = new Mensagem(
               "Realizado contrato do professor: " + novo.getNome()
                     + ". Nº de matricula: " + novo.getMatricula().getNumero());

         return mensagem;
      } catch (ConstraintViolationException e) {
         throw new CustomConstraintException(new ErrorResponse(e.getConstraintViolations()).getMessage());
      }
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   @Valid
   public Mensagem updateCadastro(String matricula, ProfessorRequestDTO professorDTO) throws Exception {
      Professor professor = repositorio.buscarPorMatricula(matricula);
      professor.setNome(professorDTO.getNome());
      professor.setCpf(professorDTO.getCpf());

      Set<ConstraintViolation<Professor>> violations = validator.validate(professor);

      if (!violations.isEmpty()) {
         throw new CustomConstraintException(new ErrorResponse(violations).getMessage());
      }

      repositorio.atualizarCadastroDoProfessor(professor);
      Mensagem mensagem = new Mensagem("Cadastro atualizado com sucesso.");
      return mensagem;
   }

   @Override
   public ProfessorResponseDTO getByMatricula(String matricula) throws Exception {
      Professor professor = repositorio.buscarPorMatricula(matricula);

      if (Objects.isNull(professor)) {
         return null;
      }

      return professorMapper.toResource(professor);
   }

   @Transactional(rollbackOn = Exception.class)
   public Mensagem teachDiscipline(String matricula, DisciplinaRequestDTO disciplinaDTO) throws Exception {
      Professor professor = repositorio.buscarPorMatricula(matricula);
      Disciplina disciplina = disciplinaRepositorio.buscarPorDisciplina(disciplinaDTO.getNomeDaDisciplina());

      if (Objects.isNull(professor) || Objects.isNull(disciplina)) {
         return null;
      }

      repositorio.lecionarDisciplina(professor.getId(), disciplina);
      Mensagem mensagem = new Mensagem("Disciplina "
            + disciplina.getNomeDaDisciplina()
            + " adicionada ao quadro de disciplinas lecionadas do professor: "
            + professor.getNome()
            + ". Matrícula: " + professor.getMatricula().getNumero());
      return mensagem;
   }

   @Transactional(rollbackOn = Exception.class)
   public Mensagem stopTeachingDiscipline(String matricula, DisciplinaRequestDTO disciplinaDTO) throws Exception {
      Professor professor = repositorio.buscarPorMatricula(matricula);
      Disciplina disciplina = disciplinaRepositorio.buscarPorDisciplina(disciplinaDTO.getNomeDaDisciplina());

      if (Objects.isNull(professor) || Objects.isNull(disciplina)) {
         return null;
      }

      repositorio.pararDeLecionarDisciplina(professor.getId(), disciplina);
      Mensagem mensagem = new Mensagem("Disciplina "
            + disciplina.getNomeDaDisciplina()
            + " removida do quadro de disciplinas lecionadas do professor: "
            + professor.getNome()
            + ". Matrícula: " + professor.getMatricula().getNumero());
      return mensagem;
   }

   @Transactional(rollbackOn = Exception.class)
   public Mensagem addCurso(String matricula, CursoRequestDTO cursoDTO) throws Exception {
      Professor professor = repositorio.buscarPorMatricula(matricula);
      Curso curso = cursoRepositorio.buscarPorNomeDoCurso(cursoDTO.getNomeDoCurso());

      if (Objects.isNull(professor) || Objects.isNull(curso)) {
         return null;
      }

      repositorio.adicionarCurso(professor.getId(), curso);
      ;
      Mensagem mensagem = new Mensagem("Professor: " + professor.getNome()
            + " Matricula: " + professor.getMatricula().getNumero()
            + ". Adicionado ao quadro de professores do Curso" + curso.getNomeDoCurso());
      return mensagem;
   }

   @Transactional(rollbackOn = Exception.class)
   public Mensagem removeCurso(String matricula, CursoRequestDTO cursoDTO) throws Exception {
      Professor professor = repositorio.buscarPorMatricula(matricula);
      Curso curso = cursoRepositorio.buscarPorNomeDoCurso(cursoDTO.getNomeDoCurso());

      if (Objects.isNull(professor) || Objects.isNull(curso)) {
         return null;
      }

      repositorio.removerCurso(professor.getId(), curso);
      ;
      Mensagem mensagem = new Mensagem("Professor: " + professor.getNome()
            + " Matricula: " + professor.getMatricula().getNumero()
            + ". Removido do quadro de professores do Curso" + curso.getNomeDoCurso());
      return mensagem;
   }
}
