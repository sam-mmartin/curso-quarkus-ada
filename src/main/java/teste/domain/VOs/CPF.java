package teste.domain.VOs;

public class CPF {

   private String numero;

   public CPF(String numero) {
      boolean cpf = numero.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}");

      if (numero == null || !cpf) {
         throw new IllegalArgumentException("CPF inválido");
      }

      this.numero = numero;
   }

   public String getNumero() {
      return numero;
   }

   @Override
   public String toString() {
      return numero;
   }
}
