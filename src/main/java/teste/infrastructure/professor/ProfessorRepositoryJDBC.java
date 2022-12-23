package teste.infrastructure.professor;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import teste.domain.curso.Curso;
import teste.domain.disciplina.Disciplina;
import teste.domain.professor.Professor;
import teste.domain.professor.RepositoryProfessor;
import teste.infrastructure.MatriculaResource;

@RequestScoped
public class ProfessorRepositoryJDBC implements RepositoryProfessor {

   @PersistenceContext
   EntityManager em;

   @Inject
   MatriculaResource mr;

   @Override
   public List<Professor> listarTodos() throws Exception {
      String nameQuery = "CONSULTAR_PROFESSORES";
      TypedQuery<Professor> query = em.createNamedQuery(nameQuery, Professor.class);

      try {
         return query.getResultList();
      } catch (NoResultException e) {
         return new ArrayList<>();
      } catch (PersistenceException e) {
         throw new Exception(e);
      }
   }

   @Override
   public Professor buscarPorMatricula(String matricula) throws Exception {
      String nameQuery = "CONSULTAR_PROFESSOR_POR_MATRICULA";
      TypedQuery<Professor> query = em.createNamedQuery(nameQuery, Professor.class)
            .setParameter("matricula", matricula);

      try {
         return query.getSingleResult();
      } catch (NoResultException e) {
         throw new Exception(e);
      } catch (PersistenceException e) {
         throw new Exception(e);
      }
   }

   @Override
   public Professor buscarPorId(int id) throws Exception {
      try {
         return em.find(Professor.class, id);
      } catch (NoResultException e) {
         throw new Exception(e);
      }
   }

   @Override
   @Transactional
   public void contratar(Professor professor) throws Exception {
      try {
         professor.setEstado(true);
         professor.setMatricula(mr.gerarMatricula());

         em.persist(professor);
      } catch (PersistenceException e) {
         throw new Exception(e);
      }
   }

   @Override
   @Transactional
   public void atualizarCadastroDoProfessor(Professor professor) throws Exception {
      update(professor);
   }

   @Override
   @Transactional
   public void demitir(Professor professor) throws Exception {
      em.remove(professor);
   }

   @Transactional
   private void update(Professor professor) throws Exception {
      try {
         em.merge(professor);
      } catch (PersistenceException e) {
         throw new Exception(e);
      }
   }

   @Override
   public void lecionarDisciplina(int id, Disciplina disciplina) throws Exception {
      Professor professor = em.find(Professor.class, id);
      List<Disciplina> disciplinas = professor.getDisciplinasLecionadas();
      disciplinas.add(disciplina);
      setDisciplinas(professor, disciplinas);
   }

   @Override
   public void pararDeLecionarDisciplina(int id, Disciplina disciplina) throws Exception {
      Professor professor = em.find(Professor.class, id);
      List<Disciplina> disciplinas = professor.getDisciplinasLecionadas();
      disciplinas.remove(disciplina);
      setDisciplinas(professor, disciplinas);
   }

   @Override
   public void adicionarCurso(int id, Curso curso) throws Exception {
      Professor professor = em.find(Professor.class, id);
      List<Curso> cursos = professor.getCursosLecionados();
      cursos.add(curso);
      setCursos(professor, cursos);
   }

   @Override
   public void removerCurso(int id, Curso curso) throws Exception {
      Professor professor = em.find(Professor.class, id);
      List<Curso> cursos = professor.getCursosLecionados();
      cursos.remove(curso);
      setCursos(professor, cursos);
   }

   private void setDisciplinas(Professor professor, List<Disciplina> disciplinas) throws Exception {
      professor.setDisciplinasLecionadas(disciplinas);
      update(professor);
   }

   private void setCursos(Professor professor, List<Curso> cursos) throws Exception {
      professor.setCursosLecionados(cursos);
      update(professor);
   }

}
