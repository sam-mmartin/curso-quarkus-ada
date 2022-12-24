package teste.application.dto.aluno;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AlunoRequestDTO {

   @NotBlank(message = "É necessário informar o nome")
   private String nome;
   @NotBlank(message = "É necessário informar o CPF")
   private String cpf;

}
