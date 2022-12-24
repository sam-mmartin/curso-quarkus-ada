package teste.application.dto.professor;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProfessorRequestDTO {

   @NotBlank(message = "É necessário informar o nome")
   private String nome;
   @NotBlank(message = "É necessário informar o CPF")
   private String cpf;

}
