package teste.infrastructure.mapeamento;

import javax.enterprise.context.RequestScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import teste.domain.mappeamento.CursoDisciplina;

@RequestScoped
public class CursoDisciplinaRepository implements PanacheRepository<CursoDisciplina> {

}
