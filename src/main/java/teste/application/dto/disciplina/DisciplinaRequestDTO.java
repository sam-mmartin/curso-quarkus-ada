package teste.application.dto.disciplina;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import teste.domain.disciplina.Disciplina;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class DisciplinaRequestDTO {

   private String nomeDaDisciplina;

   public Disciplina criarDisciplina() {
      return new Disciplina(nomeDaDisciplina);
   }
}
