package teste.application.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import lombok.RequiredArgsConstructor;
import teste.application.dto.Mensagem;
import teste.application.dto.curso.CursoRequestDTO;
import teste.application.dto.disciplina.DisciplinaRequestDTO;
import teste.application.dto.professor.ProfessorMasterInfosResponseDTO;
import teste.application.dto.professor.ProfessorRequestDTO;
import teste.application.dto.professor.ProfessorPersonInfosResponseDTO;
import teste.application.exceptions.CustomConstraintException;
import teste.application.exceptions.ErrorResponse;
import teste.application.exceptions.NotFoundException;
import teste.application.interfaces.mapper.ProfessorMapper;
import teste.domain.curso.Curso;
import teste.domain.disciplina.Disciplina;
import teste.domain.mappeamento.CursoProfessor;
import teste.domain.mappeamento.ProfessorDisciplina;
import teste.domain.professor.Professor;
import teste.infrastructure.MatriculaResource;
import teste.infrastructure.curso.CursoRepositoryJDBC;
import teste.infrastructure.disciplina.DisciplinaRepositoryJDBC;
import teste.infrastructure.mapeamento.CursoProfessorRepository;
import teste.infrastructure.mapeamento.ProfessorDisciplinaRepository;
import teste.infrastructure.professor.ProfessorRepositoryJDBC;

@RequestScoped
@RequiredArgsConstructor
public class ProfessorService {

   // #region Injetores
   private final ProfessorRepositoryJDBC repositorio;
   private final DisciplinaRepositoryJDBC disciplinaRepositorio;
   private final CursoRepositoryJDBC cursoRepositorio;
   private final CursoProfessorRepository cursoProfessorRepository;
   private final ProfessorDisciplinaRepository repositorioPD;
   private final ProfessorMapper professorMapper;
   private final MatriculaResource mr;
   private final Validator validator;
   // #endregion

   public ProfessorPersonInfosResponseDTO getById(int id) throws Exception {
      return professorMapper.toResource(repositorio.buscarPorId(id));
   }

   public List<ProfessorPersonInfosResponseDTO> getAll() throws Exception {
      return professorMapper.listToResource(repositorio.listarTodos());
   }

   public ProfessorMasterInfosResponseDTO getByMatricula(String matricula) throws Exception {
      Professor professor = repositorio.buscarPorMatricula(matricula);

      if (Objects.isNull(professor)) {
         throw new NotFoundException("Professor não encontrado! Verifique a matrícula informada.");
      }

      return professorMapper.toResourceWithCursos(professor);
   }

   @Transactional(rollbackOn = Exception.class)
   public ProfessorPersonInfosResponseDTO create(ProfessorRequestDTO professorDTO) throws Exception {
      Set<ConstraintViolation<ProfessorRequestDTO>> violations = validator.validate(professorDTO);

      if (!violations.isEmpty()) {
         throw new CustomConstraintException(
               new ErrorResponse(violations).getMessage());
      }

      LocalDateTime datetime = LocalDateTime.now();
      var novo = professorMapper.toEntity(professorDTO);

      novo.setMatricula(mr.gerarMatricula());
      novo.setEstado(true);
      novo.setDataCriacao(datetime);
      novo.setDataAtualizacao(datetime);
      novo.setObservacao("Contrato iniciado");

      var professor = repositorio.contratar(novo);
      var response = professorMapper.toResource(professor);

      return response;
   }

   @Transactional(rollbackOn = Exception.class)
   public ProfessorPersonInfosResponseDTO updateCadastro(String matricula, ProfessorRequestDTO professorDTO)
         throws Exception {
      Set<ConstraintViolation<ProfessorRequestDTO>> violations = validator.validate(professorDTO);

      if (!violations.isEmpty()) {
         throw new CustomConstraintException(
               new ErrorResponse(violations).getMessage());
      }

      Professor professor = repositorio.buscarPorMatricula(matricula);
      LocalDateTime datetime = LocalDateTime.now();

      professor.setNome(professorDTO.getNome());
      professor.setCpf(professorDTO.getCpf());
      professor.setDataAtualizacao(datetime);
      professor.setObservacao("Atualizado informações do cadastro");

      var update = repositorio.atualizarProfessor(professor);
      var response = professorMapper.toResource(update);
      return response;
   }

   @Transactional(rollbackOn = Exception.class)
   public ProfessorMasterInfosResponseDTO teachDiscipline(String matricula, DisciplinaRequestDTO disciplinaDTO)
         throws Exception {
      LocalDateTime datetime = LocalDateTime.now();
      Professor professor = repositorio.buscarPorMatricula(matricula);

      if (Objects.isNull(professor)) {
         throw new NotFoundException("Professor não encontrado!");
      }

      Disciplina disciplina = disciplinaRepositorio.buscarPorDisciplina(disciplinaDTO.getNomeDaDisciplina());

      if (Objects.isNull(disciplina)) {
         throw new NotFoundException("Disciplina não encontrada!");
      }

      ProfessorDisciplina teaching = new ProfessorDisciplina(professor, disciplina, datetime, datetime);
      repositorioPD.persist(teaching);

      professor.setObservacao("Disciplina: "
            + disciplina.getNomeDaDisciplina()
            + " adicionada ao quadro de disciplinas do professor");
      professor = repositorio.atualizarProfessor(professor);

      return professorMapper.toResourceWithCursos(professor);
   }

   @Transactional(rollbackOn = Exception.class)
   public ProfessorMasterInfosResponseDTO stopTeachingDiscipline(String matricula, DisciplinaRequestDTO disciplinaDTO)
         throws Exception {
      LocalDateTime datetime = LocalDateTime.now();
      Professor professor = repositorio.buscarPorMatricula(matricula);

      if (Objects.isNull(professor)) {
         throw new NotFoundException("Professor não encontrado! Verifique a matrícula informada.");
      }

      Disciplina disciplina = disciplinaRepositorio.buscarPorDisciplina(disciplinaDTO.getNomeDaDisciplina());

      if (Objects.isNull(disciplina)) {
         throw new NotFoundException("Disciplina não encontrada!");
      }

      try {
         var toDelete = repositorioPD.buscarPorProfessorEDisciplina(professor.getId(), disciplina.getId());

         repositorioPD.delete(toDelete);

         professor.setObservacao("Disciplina: "
               + disciplina.getNomeDaDisciplina()
               + " removida do quadro de disciplinas do professor");
         professor.setDataAtualizacao(datetime);
         repositorio.atualizarProfessor(professor);

         return professorMapper.toResourceWithCursos(professor);
      } catch (NullPointerException e) {
         throw new NotFoundException("Professor não leciona a disciplina informada!");
      }
   }

   @Transactional(rollbackOn = Exception.class)
   public ProfessorMasterInfosResponseDTO addCurso(String matricula, CursoRequestDTO cursoDTO) throws Exception {
      LocalDateTime dateTime = LocalDateTime.now();
      Professor professor = repositorio.buscarPorMatricula(matricula);

      if (Objects.isNull(professor)) {
         throw new NotFoundException("Professor não encontrado!");
      }

      Curso curso = cursoRepositorio.buscarPorNomeDoCurso(cursoDTO.getNomeDoCurso());

      if (Objects.isNull(curso)) {
         throw new NotFoundException("Curso não encontrado!");
      }

      CursoProfessor novo = new CursoProfessor(curso, professor, dateTime, dateTime);
      cursoProfessorRepository.create(novo);

      return professorMapper.toResourceWithCursos(professor);
   }

   @Transactional(rollbackOn = Exception.class)
   public Mensagem removeCurso(String matricula, CursoRequestDTO cursoDTO)
         throws Exception {
      Professor professor = repositorio.buscarPorMatricula(matricula);
      Curso curso = cursoRepositorio.buscarPorNomeDoCurso(cursoDTO.getNomeDoCurso());

      if (Objects.isNull(professor)) {
         throw new NotFoundException("Professor não encontrado!");
      }

      if (Objects.isNull(curso)) {
         throw new NotFoundException("Curso não encontrado!");
      }

      professor.setObservacao("Professor deixou de integrar quadro de professores do curso: "
            + curso.getNomeDoCurso());

      repositorio.atualizarProfessor(professor);
      cursoProfessorRepository.delete(curso.getId(), professor.getId());

      Mensagem mensagem = new Mensagem("Professor: " + professor.getNome()
            + " Matricula: " + professor.getMatricula().getNumero()
            + ". Removido do quadro de professores do Curso: " + curso.getNomeDoCurso());
      return mensagem;
   }
}
