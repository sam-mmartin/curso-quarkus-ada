package teste.infrastructure.mapeamento;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import teste.application.exceptions.CustomConstraintException;
import teste.application.exceptions.PersistenceErrorException;
import teste.domain.mappeamento.CursoProfessor;
import teste.domain.mappeamento.RepositoryCursoProfessor;

@RequestScoped
public class CursoProfessorRepository implements RepositoryCursoProfessor {

   @PersistenceContext
   EntityManager em;

   @Override
   @Transactional
   public void create(CursoProfessor cursoProfessor) throws Exception {
      try {
         em.persist(cursoProfessor);
      } catch (PersistenceException e) {
         throw new CustomConstraintException(e.getMessage());
      }
   }

   @Override
   @Transactional
   public void delete(long idCurso, int idProfessor) throws Exception {
      TypedQuery<CursoProfessor> query = em.createNamedQuery("CONSULTAR_POR_CURSO_PROFESSOR", CursoProfessor.class)
            .setParameter("curso_id", idCurso).setParameter("professor_id", idProfessor);

      try {
         CursoProfessor apagar = query.getSingleResult();
         em.remove(apagar);
      } catch (PersistenceException e) {
         throw new PersistenceErrorException(e.getMessage());
      }
   }

}
