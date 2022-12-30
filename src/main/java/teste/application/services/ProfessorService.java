package teste.application.services;

import java.time.LocalDateTime;
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
import teste.application.dto.professor.ProfessorMasterInfosResponseDTO;
import teste.application.dto.professor.ProfessorRequestDTO;
import teste.application.dto.professor.ProfessorPersonInfosResponseDTO;
import teste.application.exceptions.CustomConstraintException;
import teste.application.exceptions.ErrorResponse;
import teste.application.exceptions.NotFoundException;
import teste.application.interfaces.mapper.ProfessorMapper;
import teste.application.interfaces.services.ServiceGenerics;
import teste.application.interfaces.services.ServiceCadastroMatricula;
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
public class ProfessorService implements ServiceGenerics<ProfessorPersonInfosResponseDTO, ProfessorRequestDTO>,
      ServiceCadastroMatricula<ProfessorMasterInfosResponseDTO, ProfessorRequestDTO> {

   // #region Injetores
   @Inject
   ProfessorRepositoryJDBC repositorio;

   @Inject
   DisciplinaRepositoryJDBC disciplinaRepositorio;

   @Inject
   CursoRepositoryJDBC cursoRepositorio;

   @Inject
   CursoProfessorRepository cursoProfessorRepository;

   @Inject
   ProfessorDisciplinaRepository repositorioPD;

   @Inject
   ProfessorMapper professorMapper;

   @Inject
   MatriculaResource mr;

   @Inject
   Validator validator;

   // #endregion

   @Override
   public ProfessorPersonInfosResponseDTO getById(int id) throws Exception {
      return professorMapper.toResource(repositorio.buscarPorId(id));
   }

   @Override
   public List<ProfessorPersonInfosResponseDTO> getAll() throws Exception {
      return professorMapper.listToResource(repositorio.listarTodos());
   }

   @Override
   public ProfessorMasterInfosResponseDTO getByMatricula(String matricula) throws Exception {
      Professor professor = repositorio.buscarPorMatricula(matricula);

      if (Objects.isNull(professor)) {
         throw new NotFoundException("Professor não encontrado!");
      }

      return professorMapper.toResourceWithCursos(professor);
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   @Valid
   public Mensagem create(ProfessorRequestDTO professorDTO) throws Exception {
      try {
         LocalDateTime datetime = LocalDateTime.now();

         Professor novo = new Professor(
               professorDTO.getNome(),
               professorDTO.getCpf(),
               mr.gerarMatricula(),
               true,
               datetime,
               datetime,
               "Contrato iniciado");

         repositorio.contratar(novo);
         Mensagem mensagem = new Mensagem("Realizado contrato do professor: "
               + novo.getNome()
               + ". Nº de matricula: "
               + novo.getMatricula().getNumero());

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
      LocalDateTime datetime = LocalDateTime.now();

      professor.setNome(professorDTO.getNome());
      professor.setCpf(professorDTO.getCpf());
      professor.setDataAtualizacao(datetime);
      professor.setObservacao("Atualizado informações do cadastro");

      Set<ConstraintViolation<Professor>> violations = validator.validate(professor);

      if (!violations.isEmpty()) {
         throw new CustomConstraintException(new ErrorResponse(violations).getMessage());
      }

      repositorio.atualizarProfessor(professor);
      Mensagem mensagem = new Mensagem("Cadastro atualizado com sucesso.");
      return mensagem;
   }

   @Transactional(rollbackOn = Exception.class)
   public Mensagem teachDiscipline(String matricula, DisciplinaRequestDTO disciplinaDTO) throws Exception {
      LocalDateTime datetime = LocalDateTime.now();
      Professor professor = repositorio.buscarPorMatricula(matricula);
      Disciplina disciplina = disciplinaRepositorio.buscarPorDisciplina(disciplinaDTO.getNomeDaDisciplina());

      if (Objects.isNull(professor)) {
         throw new NotFoundException("Professor não encontrado na base de dados!");
      }

      if (Objects.isNull(disciplina)) {
         throw new NotFoundException("Disciplina não encontrada!");
      }

      ProfessorDisciplina teaching = new ProfessorDisciplina(professor, disciplina, datetime, datetime);
      repositorioPD.persist(teaching);

      professor.setObservacao("Disciplina: "
            + disciplina.getNomeDaDisciplina()
            + " adicionada ao quadro de disciplinas do professor");
      professor.setDataAtualizacao(datetime);
      repositorio.atualizarProfessor(professor);

      Mensagem mensagem = new Mensagem("Disciplina "
            + disciplina.getNomeDaDisciplina()
            + " adicionada ao quadro de disciplinas lecionadas do professor: "
            + professor.getNome()
            + ". Matrícula: " + professor.getMatricula().getNumero());
      return mensagem;
   }

   @Transactional(rollbackOn = Exception.class)
   public Mensagem stopTeachingDiscipline(String matricula, DisciplinaRequestDTO disciplinaDTO) throws Exception {
      LocalDateTime datetime = LocalDateTime.now();
      Professor professor = repositorio.buscarPorMatricula(matricula);
      Disciplina disciplina = disciplinaRepositorio.buscarPorDisciplina(disciplinaDTO.getNomeDaDisciplina());

      if (Objects.isNull(professor)) {
         throw new NotFoundException("Professor não encontrado na base de dados!");
      }

      if (Objects.isNull(disciplina)) {
         throw new NotFoundException("Disciplina não encontrada!");
      }

      try {
         ProfessorDisciplina apagar = repositorioPD.find("professor_id = ?1 and disciplina_id = ?2", professor.getId(),
               disciplina.getId()).firstResult();

         repositorioPD.delete(apagar);

         professor.setObservacao("Disciplina: "
               + disciplina.getNomeDaDisciplina()
               + " removida do quadro de disciplinas do professor");
         professor.setDataAtualizacao(datetime);
         repositorio.atualizarProfessor(professor);

         Mensagem mensagem = new Mensagem("Disciplina "
               + disciplina.getNomeDaDisciplina()
               + " removida do quadro de disciplinas lecionadas do professor: "
               + professor.getNome()
               + ". Matrícula: " + professor.getMatricula().getNumero());
         return mensagem;
      } catch (NullPointerException e) {
         throw new NotFoundException("Professor não leciona a disciplina informada!");
      } catch (Exception e) {
         throw new Exception(e.getMessage());
      }
   }

   @Transactional(rollbackOn = Exception.class)
   public Mensagem addCurso(String matricula, CursoRequestDTO cursoDTO) throws Exception {
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
      professor.setDataAtualizacao(dateTime);

      Mensagem mensagem = new Mensagem("Professor: " + professor.getNome()
            + " Matricula: " + professor.getMatricula().getNumero()
            + ". Adicionado ao quadro de professores do Curso: " + curso.getNomeDoCurso());
      return mensagem;
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
