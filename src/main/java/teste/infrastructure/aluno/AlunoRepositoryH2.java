package teste.infrastructure.aluno;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import teste.domain.aluno.Aluno;
import teste.domain.aluno.interfaces.RepositoryAluno;

@RequestScoped
public class AlunoRepositoryH2 implements RepositoryAluno {

   @PersistenceContext
   EntityManager em;

   @Override
   public List<Aluno> listarTodos() throws Exception {
      String nameQuery = "CONSULTAR_ALUNOS";
      TypedQuery<Aluno> query = em.createNamedQuery(nameQuery, Aluno.class);

      try {
         return query.getResultList();
      } catch (NoResultException e) {
         return new ArrayList<>();
      } catch (PersistenceException e) {
         throw new Exception(e);
      }
   }

   @Override
   public Aluno buscarPorMatricula(String matricula) {
      return null;
   }

   @Override
   public Aluno buscarPorId(int id) throws Exception {
      try {
         return em.find(Aluno.class, id);
      } catch (NoResultException e) {
         throw new Exception(e);
      }
   }

   @Override
   @Transactional
   public void matricular(Aluno aluno) throws Exception {
      try {
         em.persist(aluno);
      } catch (PersistenceException e) {
         throw new Exception(e);
      }
   }

   @Override
   public void rematricular(int id, Aluno aluno) {
      // TODO Auto-generated method stub

   }

   @Override
   public void atualizarCadastroDoAluno(int id, Aluno aluno) {
      // TODO Auto-generated method stub

   }

   @Override
   public void cancelarMatricula(int id) {
      // TODO Auto-generated method stub

   }

}
