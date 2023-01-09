package teste.infrastructure.mapeamento;

import javax.enterprise.context.RequestScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import teste.domain.mappeamento.ProfessorDisciplina;

@RequestScoped
public class ProfessorDisciplinaRepository implements PanacheRepository<ProfessorDisciplina> {

   public ProfessorDisciplina buscarPorProfessorEDisciplina(int idProfessor, long idDisciplina) {
      return find("professor_id = ?1 and disciplina_id = ?2", idProfessor, idDisciplina).firstResult();
   }

}
