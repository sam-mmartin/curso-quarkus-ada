package teste.infrastructure.curso;

import javax.enterprise.context.RequestScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import teste.domain.curso.Curso;

@RequestScoped
public class CursoRepositoryJDBC implements PanacheRepository<Curso> {

   public Curso buscarPorNomeDoCurso(String nomeDoCurso) {
      return find("nomeDoCurso", nomeDoCurso).firstResult();
   }
}
