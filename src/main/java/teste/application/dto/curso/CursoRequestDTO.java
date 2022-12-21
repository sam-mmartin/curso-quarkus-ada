package teste.application.dto.curso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import teste.domain.curso.Curso;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CursoRequestDTO {

   private String nomeDoCurso;

   public Curso criarCurso() {
      return new Curso(nomeDoCurso);
   }

}
