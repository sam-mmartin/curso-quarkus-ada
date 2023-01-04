package teste.infrastructure.aluno;

import java.time.LocalDateTime;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

      return query.getResultList();
   }

   @Override
   public Aluno buscarPorMatricula(String matricula) throws Exception {
      String nameQuery = "CONSULTAR_ALUNO_POR_MATRICULA";
      TypedQuery<Aluno> query = em.createNamedQuery(nameQuery, Aluno.class)
            .setParameter("matricula", matricula);

      return query.getSingleResult();
   }

   @Override
   public Aluno buscarPorId(int id) throws Exception {
      return em.find(Aluno.class, id);
   }

   @Override
   public List<Aluno> listarAlunosPorCurso(int idCurso) throws Exception {
      String nameQuery = "CONSULTAR_ALUNOS_POR_CURSO";
      TypedQuery<Aluno> query = em.createNamedQuery(nameQuery, Aluno.class)
            .setParameter("curso_id", idCurso);

      return query.getResultList();
   }

   @Override
   @Transactional
   public Aluno matricular(Aluno aluno) throws Exception {
      LocalDateTime datetime = LocalDateTime.now();
      aluno.setEstado(true);
      aluno.setMatricula(mr.gerarMatricula());
      aluno.setDataCriacao(datetime);
      aluno.setDataAtualizacao(datetime);

      em.persist(aluno);

      return aluno;
   }

   @Override
   public Aluno rematricular(Aluno aluno) throws Exception {
      aluno.setEstado(true);
      return update(aluno);
   }

   @Override
   public Aluno atualizarCadastroDoAluno(Aluno aluno) throws Exception {
      return update(aluno);
   }

   @Override
   public Aluno cancelarMatricula(Aluno aluno) throws Exception {
      aluno.setEstado(false);
      return update(aluno);
   }

   public void apagarRegistro(Aluno aluno) throws Exception {
      em.remove(aluno);
   }

   private Aluno update(Aluno aluno) throws Exception {
      LocalDateTime datetime = LocalDateTime.now();
      aluno.setDataAtualizacao(datetime);

      em.merge(aluno);

      return aluno;
   }

}
