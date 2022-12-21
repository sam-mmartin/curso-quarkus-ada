package teste.application.services;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import teste.application.dto.Mensagem;
import teste.application.dto.curso.CursoDisciplinaResponseDTO;
import teste.application.dto.curso.CursoRequestDTO;
import teste.application.dto.curso.CursoResponseDTO;
import teste.application.dto.disciplina.DisciplinaRequestDTO;
import teste.application.dto.disciplina.DisciplinaResponseDTO;
import teste.application.interfaces.mapper.CursoDisciplinaMapper;
import teste.application.interfaces.mapper.CursoMapper;
import teste.application.interfaces.mapper.DisciplinaMapper;
import teste.application.interfaces.services.ServiceGenerics;
import teste.application.interfaces.services.ServiceGenerics2;
import teste.domain.curso.Curso;
import teste.domain.disciplina.Disciplina;
import teste.infrastructure.curso.CursoRepositoryJDBC;
import teste.infrastructure.disciplina.DisciplinaRepositoryJDBC;

@RequestScoped
public class CursoService implements ServiceGenerics<CursoResponseDTO, CursoRequestDTO>,
      ServiceGenerics2<CursoResponseDTO, CursoRequestDTO> {

   @Inject
   CursoRepositoryJDBC repositorio;

   @Inject
   DisciplinaRepositoryJDBC disciplinaRepository;

   @Inject
   CursoMapper cursoMapper;

   @Inject
   DisciplinaMapper disciplinaMapper;

   @Inject
   CursoDisciplinaMapper cursoDisciplinaMapper;

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
   @Transactional(rollbackOn = Exception.class)
   public Mensagem create(CursoRequestDTO cursoDTO) throws Exception {
      Curso novo = cursoDTO.criarCurso();

      repositorio.persist(novo);
      Mensagem mensagem = new Mensagem("Curso: " + cursoDTO.getNomeDoCurso() + " implantado com sucesso.");
      return mensagem;
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public Mensagem update(int id, CursoRequestDTO cursoDTO) throws Exception {
      repositorio.update("nomeDoCurso = ?1 where id = ?2", cursoDTO.getNomeDoCurso(), (long) id);

      Mensagem mensagem = new Mensagem("Cadastro realizado com sucesso");
      return mensagem;
   }

   @Override
   public void delete(int id) throws Exception {
      repositorio.deleteById((long) id);
   }

   @Override
   public CursoResponseDTO getByName(String name) throws Exception {
      Curso curso = repositorio.buscarPorNomeDoCurso(name);

      if (Objects.isNull(curso)) {
         return null;
      }

      return cursoMapper.toResource(curso);
   }

   @Transactional(rollbackOn = Exception.class)
   public Mensagem addDisciplinaToCurso(int id, DisciplinaRequestDTO disciplinaDTO) throws Exception {
      Curso curso = repositorio.findById((long) id);

      if (Objects.isNull(curso)) {
         return null;
      }

      Disciplina disciplina = disciplinaRepository.buscarPorDisciplina(disciplinaDTO.getNomeDaDisciplina());
      List<Disciplina> disciplinas = curso.getDisciplinasDoCurso();
      disciplinas.add(disciplina);
      repositorio.persist(curso);

      Mensagem mensagem = new Mensagem(
            "Disciplina: " + disciplina.getNomeDaDisciplina() + " adicionada ao curso: " + curso.getNomeDoCurso());
      return mensagem;
   }

   public List<DisciplinaResponseDTO> getDisciplinasFromCurso(int id) throws Exception {
      Curso curso = repositorio.findById((long) id);

      if (Objects.isNull(curso)) {
         return null;
      }

      List<Disciplina> disciplinas = curso.getDisciplinasDoCurso();
      return disciplinaMapper.listToResource(disciplinas);
   }

   public CursoDisciplinaResponseDTO getCursoAndDisciplinas(int id) throws Exception {
      Curso curso = repositorio.findById((long) id);

      if (Objects.isNull(curso)) {
         return null;
      }

      return cursoDisciplinaMapper.toResource(curso);
   }

   public List<CursoDisciplinaResponseDTO> getAllCursosAndDisciplinas() throws Exception {
      List<Curso> cursos = repositorio.listAll();

      return cursoDisciplinaMapper.listToResource(cursos);
   }
}
