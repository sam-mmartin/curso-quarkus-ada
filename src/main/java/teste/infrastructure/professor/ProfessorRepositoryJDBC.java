package teste.infrastructure.professor;

import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import teste.domain.professor.Professor;
import teste.domain.professor.RepositoryProfessor;

@RequestScoped
public class ProfessorRepositoryJDBC implements RepositoryProfessor {

   @PersistenceContext
   EntityManager em;

   @Override
   public List<Professor> listarTodos() throws Exception {
      String nameQuery = "CONSULTAR_PROFESSORES";
      TypedQuery<Professor> query = em.createNamedQuery(nameQuery, Professor.class);

      return query.getResultList();
   }

   @Override
   public Professor buscarPorMatricula(String matricula) {
      try {
         String nameQuery = "CONSULTAR_PROFESSOR_POR_MATRICULA";
         TypedQuery<Professor> query = em.createNamedQuery(nameQuery, Professor.class)
               .setParameter("matricula", matricula);

         return query.getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   @Override
   public Professor buscarPorId(int id) throws Exception {
      return em.find(Professor.class, id);
   }

   @Override
   @Transactional
   public Professor contratar(Professor professor) throws Exception {
      em.persist(professor);
      return professor;
   }

   @Override
   @Transactional
   public Professor atualizarProfessor(Professor professor) throws Exception {
      LocalDateTime dateTime = LocalDateTime.now();
      professor.setDataAtualizacao(dateTime);

      em.merge(professor);
      return professor;
   }

   @Override
   @Transactional
   public void demitir(Professor professor) throws Exception {
      em.remove(em.contains(professor) ? professor : em.merge(professor));
   }

}
