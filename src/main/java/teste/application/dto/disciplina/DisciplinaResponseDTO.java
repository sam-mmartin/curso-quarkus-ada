package teste.application.dto.disciplina;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class DisciplinaResponseDTO {

   private int id;
   private String nomeDaDisciplina;
   private String dataCriacao;
   private String dataAtualizacao;
}
