package teste.infrastructure.disciplina;

import javax.enterprise.context.RequestScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import teste.domain.disciplina.Disciplina;

@RequestScoped
public class DisciplinaRepositoryJDBC implements PanacheRepository<Disciplina> {

   public Disciplina buscarPorDisciplina(String nomeDaDisciplina) {
      return find("nomeDaDisciplina", nomeDaDisciplina).firstResult();
   }

}
