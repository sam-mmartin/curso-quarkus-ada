package teste.application.dto.curso;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import teste.application.dto.mapeamento.NameResponseDTO;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GradeCurricularResponseDTO {

   private int id;
   private String nomeDoCurso;
   private List<NameResponseDTO> disciplinasDoCurso;
}
