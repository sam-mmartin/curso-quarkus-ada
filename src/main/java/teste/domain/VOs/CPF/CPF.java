package teste.domain.VOs.CPF;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CPF {

   private String numero;

   public CPF(String numero) {
      boolean cpf = numero.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}");

      if (numero == null || !cpf) {
         throw new IllegalArgumentException("CPF inválido");
      }

      this.numero = numero;
   }

}
