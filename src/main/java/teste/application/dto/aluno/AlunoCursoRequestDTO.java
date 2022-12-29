package teste.application.dto.aluno;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class AlunoCursoRequestDTO {

   @NotBlank(message = "É necessário informar o nome")
   private String nome;
   @NotBlank(message = "É necessário informar o CPF")
   private String cpf;
   @NotBlank(message = "É necessário informar o nome do curso")
   private String curso;
}
