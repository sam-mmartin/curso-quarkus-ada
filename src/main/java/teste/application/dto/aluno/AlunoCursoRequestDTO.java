package teste.application.dto.aluno;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AlunoCursoRequestDTO {

   @NotBlank(message = "O nome do aluno é obrigatório")
   private String nome;
   @NotBlank(message = "O CPF do aluno é obrigatório")
   private String cpf;
   @NotBlank(message = "É necessário informar o nome do curso")
   private String curso;
}
