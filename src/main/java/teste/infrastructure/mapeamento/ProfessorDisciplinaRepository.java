package teste.infrastructure.mapeamento;

import javax.enterprise.context.RequestScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import teste.domain.mappeamento.ProfessorDisciplina;

@RequestScoped
public class ProfessorDisciplinaRepository implements PanacheRepository<ProfessorDisciplina> {

}
