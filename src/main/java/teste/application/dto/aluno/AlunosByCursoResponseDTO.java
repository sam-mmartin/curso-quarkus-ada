package teste.application.dto.aluno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AlunosByCursoResponseDTO {

   private String nome;
   private String matricula;
   private String estado;
}
