package teste.infrastructure.aluno;

import java.time.LocalDateTime;
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

import teste.domain.aluno.Aluno;
import teste.domain.aluno.RepositoryAluno;
import teste.infrastructure.MatriculaResource;

@RequestScoped
public class AlunoRepositoryJDBC implements RepositoryAluno {

   @PersistenceContext
   EntityManager em;

   @Inject
   MatriculaResource mr;

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
   public Aluno buscarPorMatricula(String matricula) throws Exception {
      String nameQuery = "CONSULTAR_ALUNO_POR_MATRICULA";
      TypedQuery<Aluno> query = em.createNamedQuery(nameQuery, Aluno.class)
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
   public Aluno buscarPorId(int id) throws Exception {
      try {
         return em.find(Aluno.class, id);
      } catch (NoResultException e) {
         throw new Exception(e);
      }
   }

   @Override
   public List<Aluno> listarAlunosPorCurso(int idCurso) throws Exception {
      String nameQuery = "CONSULTAR_ALUNOS_POR_CURSO";
      TypedQuery<Aluno> query = em.createNamedQuery(nameQuery, Aluno.class)
            .setParameter("curso_id", idCurso);

      try {
         return query.getResultList();
      } catch (NoResultException e) {
         return new ArrayList<>();
      } catch (PersistenceException e) {
         throw new Exception(e);
      }
   }

   @Override
   @Transactional
   public void matricular(Aluno aluno) throws Exception {
      try {
         LocalDateTime datetime = LocalDateTime.now();
         aluno.setEstado(true);
         aluno.setMatricula(mr.gerarMatricula());
         aluno.setDataCriacao(datetime);
         aluno.setDataAtualizacao(datetime);

         em.persist(aluno);
      } catch (PersistenceException e) {
         throw new Exception(e);
      }
   }

   @Override
   public void rematricular(Aluno aluno) throws Exception {
      aluno.setEstado(true);
      update(aluno);
   }

   @Override
   public void atualizarCadastroDoAluno(Aluno aluno) throws Exception {
      update(aluno);
   }

   @Override
   public void cancelarMatricula(Aluno aluno) throws Exception {
      aluno.setEstado(false);
      update(aluno);
   }

   private void update(Aluno aluno) throws Exception {
      try {
         LocalDateTime datetime = LocalDateTime.now();
         aluno.setDataAtualizacao(datetime);

         em.merge(aluno);
      } catch (PersistenceException e) {
         throw new Exception(e);
      }
   }

}
