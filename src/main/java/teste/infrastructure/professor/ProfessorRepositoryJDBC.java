package teste.infrastructure.professor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import teste.application.exceptions.CustomConstraintException;
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

      try {
         return query.getResultList();
      } catch (NoResultException e) {
         return new ArrayList<>();
      } catch (PersistenceException e) {
         throw new Exception(e);
      }
   }

   @Override
   public Professor buscarPorMatricula(String matricula) throws CustomConstraintException {
      String nameQuery = "CONSULTAR_PROFESSOR_POR_MATRICULA";
      TypedQuery<Professor> query = em.createNamedQuery(nameQuery, Professor.class)
            .setParameter("matricula", matricula);

      try {
         return query.getSingleResult();
      } catch (NoResultException e) {
         throw new CustomConstraintException(e.getMessage());
      } catch (PersistenceException e) {
         throw new CustomConstraintException(e.getMessage());
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
      em.persist(professor);
   }

   @Override
   @Transactional
   public void atualizarProfessor(Professor professor) throws Exception {
      try {
         LocalDateTime dateTime = LocalDateTime.now();
         professor.setDataAtualizacao(dateTime);

         em.merge(professor);
      } catch (PersistenceException e) {
         throw new CustomConstraintException(e.getMessage());
      }
   }

   @Override
   @Transactional
   public void demitir(Professor professor) throws Exception {
      em.remove(professor);
   }

}
