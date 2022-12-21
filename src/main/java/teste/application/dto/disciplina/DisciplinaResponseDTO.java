package teste.application.dto.disciplina;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DisciplinaResponseDTO {

   private int id;
   private String nomeDaDisciplina;
}
