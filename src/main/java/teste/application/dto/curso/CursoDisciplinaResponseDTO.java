package teste.application.dto.curso;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import teste.application.dto.disciplina.DisciplinaResponseDTO;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CursoDisciplinaResponseDTO {

   private int id;
   private String nomeDoCurso;
   private List<DisciplinaResponseDTO> disciplinasDoCurso;
}
