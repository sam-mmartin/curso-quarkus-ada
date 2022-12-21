package teste.application.dto.disciplina;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import teste.domain.disciplina.Disciplina;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DisciplinaRequestDTO {

   private String nomeDaDisciplina;

   public Disciplina criarDisciplina() {
      return new Disciplina(nomeDaDisciplina);
   }
}
