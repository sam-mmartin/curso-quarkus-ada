package teste.application.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import teste.application.dto.Mensagem;
import teste.application.dto.curso.GradeCurricularResponseDTO;
import teste.application.dto.curso.CursoAlunosResponseDTO;
import teste.application.dto.curso.CursoProfessoresResponseDTO;
import teste.application.dto.curso.CursoRequestDTO;
import teste.application.dto.curso.CursoResponseDTO;
import teste.application.dto.disciplina.DisciplinaRequestDTO;
import teste.application.exceptions.NotFoundException;
import teste.application.interfaces.mapper.CursoMapper;
import teste.application.interfaces.services.ServiceGenerics;
import teste.application.interfaces.services.ServiceGenerics2;
import teste.domain.curso.Curso;
import teste.domain.disciplina.Disciplina;
import teste.domain.mappeamento.CursoDisciplina;
import teste.domain.mappeamento.CursoProfessor;
import teste.domain.professor.Professor;
import teste.infrastructure.curso.CursoRepositoryJDBC;
import teste.infrastructure.disciplina.DisciplinaRepositoryJDBC;
import teste.infrastructure.mapeamento.CursoDisciplinaRepository;
import teste.infrastructure.mapeamento.CursoProfessorRepository;
import teste.infrastructure.professor.ProfessorRepositoryJDBC;

@RequestScoped
public class CursoService implements ServiceGenerics<CursoResponseDTO, CursoRequestDTO>,
      ServiceGenerics2<CursoResponseDTO, CursoRequestDTO> {

   // #region Injects
   @Inject
   CursoRepositoryJDBC repositorio;

   @Inject
   DisciplinaRepositoryJDBC disciplinaRepository;

   @Inject
   CursoDisciplinaRepository repositorioCD;

   @Inject
   CursoProfessorRepository repositorioCP;

   @Inject
   ProfessorRepositoryJDBC repositorioProfessor;

   @Inject
   CursoMapper cursoMapper;

   // #endregion

   // #region GETs

   @Override
   public CursoResponseDTO getById(int id) throws Exception {
      Curso curso = repositorio.findById((long) id);

      if (Objects.isNull(curso)) {
         return null;
      }

      return cursoMapper.toResource(curso);
   }

   @Override
   public List<CursoResponseDTO> getAll() throws Exception {
      return cursoMapper.listToResource(repositorio.listAll());
   }

   @Override
   public CursoResponseDTO getByName(String name) throws Exception {
      Curso curso = repositorio.buscarPorNomeDoCurso(name);

      if (Objects.isNull(curso)) {
         return null;
      }

      return cursoMapper.toResource(curso);
   }

   public GradeCurricularResponseDTO getCursoAndDisciplinas(int id) throws Exception {
      Curso curso = repositorio.findById((long) id);

      if (Objects.isNull(curso)) {
         return null;
      }

      return cursoMapper.toResourceWithDisciplinas(curso);
   }

   public List<GradeCurricularResponseDTO> getAllCursosAndDisciplinas() throws Exception {
      List<Curso> cursos = repositorio.listAll();

      return cursoMapper.listToResourceWithDisciplinas(cursos);
   }

   public CursoProfessoresResponseDTO getCursoAndProfessores(int id) throws Exception {
      Curso curso = repositorio.findById((long) id);

      if (Objects.isNull(curso)) {
         throw new NotFoundException("Curso não encontrado!");
      }

      return cursoMapper.toResourceWithProfessores(curso);
   }

   public List<CursoProfessoresResponseDTO> getAllCursosAndProfessores() throws Exception {
      List<Curso> cursos = repositorio.listAll();

      return cursoMapper.listToResourceWithProfessores(cursos);
   }

   public CursoAlunosResponseDTO getCursoAndAlunos(long id) throws Exception {
      Curso curso = repositorio.findById(id);

      if (Objects.isNull(curso)) {
         throw new NotFoundException("Curso não encontrado!");
      }

      return cursoMapper.toResourceWithAlunos(curso);
   }

   public List<CursoAlunosResponseDTO> getAllCursosAndAlunos() throws Exception {
      List<Curso> cursos = repositorio.listAll();

      return cursoMapper.listToResourceWithAlunos(cursos);
   }

   // #endregion

   @Override
   @Transactional(rollbackOn = Exception.class)
   public Mensagem create(CursoRequestDTO cursoDTO) throws Exception {
      LocalDateTime dateTime = LocalDateTime.now();

      Curso novo = cursoDTO.criarCurso();
      novo.setObservacao("Curso implantado");
      novo.setDataCriacao(dateTime);
      novo.setDataAtualizacao(dateTime);

      repositorio.persist(novo);
      Mensagem mensagem = new Mensagem("Curso: " + cursoDTO.getNomeDoCurso() + " implantado com sucesso.");
      return mensagem;
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public Mensagem update(int id, CursoRequestDTO cursoDTO) throws Exception {
      LocalDateTime dateTime = LocalDateTime.now();
      repositorio.update("nomeDoCurso = ?1, data_atualizacao = ?2 where id = ?3",
            cursoDTO.getNomeDoCurso(),
            dateTime,
            (long) id);

      Mensagem mensagem = new Mensagem("Atualização do cadastro realizado com sucesso");
      return mensagem;
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public void delete(int id) throws Exception {
      repositorio.deleteById((long) id);
   }

   @Transactional(rollbackOn = Exception.class)
   public Mensagem addProfessorToCurso(int id, String matricula) throws Exception {
      LocalDateTime dateTime = LocalDateTime.now();
      Curso curso = repositorio.findById((long) id);
      Professor professor = repositorioProfessor.buscarPorMatricula(matricula);

      if (Objects.isNull(curso)) {
         throw new NotFoundException("Curso não encontrado!");
      }

      if (Objects.isNull(professor)) {
         throw new NotFoundException("Professor não encontrado!");
      }

      CursoProfessor novo = new CursoProfessor(curso, professor, dateTime, dateTime);
      repositorioCP.create(novo);

      curso.setObservacao("Professor: "
            + professor.getNome()
            + ", matricula: "
            + professor.getMatricula().getNumero()
            + ", adicionado ao quadro de professores.");
      curso.setDataAtualizacao(dateTime);
      repositorio.persistAndFlush(curso);

      Mensagem mensagem = new Mensagem("Professor: "
            + professor.getNome()
            + " Matricula: "
            + professor.getMatricula().getNumero()
            + ". Adicionado ao quadro de professores do Curso: "
            + curso.getNomeDoCurso());
      return mensagem;
   }

   @Transactional(rollbackOn = Exception.class)
   public Mensagem addDisciplinaToCurso(int id, DisciplinaRequestDTO disciplinaDTO) throws Exception {
      LocalDateTime dateTime = LocalDateTime.now();
      Curso curso = repositorio.findById((long) id);

      if (Objects.isNull(curso)) {
         throw new NotFoundException("Curso não encontrado!");
      }

      Disciplina disciplina = disciplinaRepository.buscarPorDisciplina(disciplinaDTO.getNomeDaDisciplina());

      if (Objects.isNull(disciplina)) {
         throw new NotFoundException("Curso não encontrado!");
      }

      CursoDisciplina novo = new CursoDisciplina(curso, disciplina, dateTime, dateTime);
      repositorioCD.persist(novo);

      curso.setObservacao("Disciplina: "
            + disciplina.getNomeDaDisciplina()
            + " adicionada ao quadro de disciplinas do curso");
      curso.setDataAtualizacao(dateTime);
      repositorio.persist(curso);

      Mensagem mensagem = new Mensagem(
            "Disciplina: " + disciplina.getNomeDaDisciplina() + " adicionada ao curso: "
                  + curso.getNomeDoCurso());
      return mensagem;
   }

}
