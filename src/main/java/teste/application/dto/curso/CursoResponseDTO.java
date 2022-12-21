package teste.application.dto.curso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CursoResponseDTO {

   private int id;
   private String nomeDoCurso;
}
