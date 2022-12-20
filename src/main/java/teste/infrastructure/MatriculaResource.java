package teste.infrastructure;

import java.util.Random;

import javax.enterprise.context.RequestScoped;

import teste.domain.VOs.Matricula.Matricula;
import teste.domain.VOs.Matricula.MatriculaService;

@RequestScoped
public class MatriculaResource implements MatriculaService {

   @Override
   public Matricula gerarMatricula() {
      Random random = new Random();
      int number = random.nextInt(9000) + 1000;
      int validator = random.nextInt(1000);

      return new Matricula(number + "-" + validator);
   }
}
