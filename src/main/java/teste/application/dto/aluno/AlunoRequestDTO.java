package teste.application.dto.aluno;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AlunoRequestDTO {

   @NotBlank(message = "O nome do aluno é obrigatório")
   private String nome;
   @NotBlank(message = "O CPF do aluno é obrigatório")
   private String cpf;
   @NotBlank(message = "O Curso é obrigatório")
   private String nomeDoCurso;

}
