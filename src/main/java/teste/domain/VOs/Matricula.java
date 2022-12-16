package teste.domain.VOs;

public class Matricula {

   private int numero;
   private int validador;

   public Matricula(int numero, int validador) {
      this.numero = numero;
      this.validador = validador;
   }

   public String getMatricula() {
      return numero + "-" + validador;
   }
}
