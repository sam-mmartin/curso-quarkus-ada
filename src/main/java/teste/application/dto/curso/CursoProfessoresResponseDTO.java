package teste.application.dto.curso;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import teste.application.dto.professor.ProfessorBasicInfoResponseDTO;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CursoProfessoresResponseDTO {

   private int id;
   private String nomeDoCurso;
   private List<ProfessorBasicInfoResponseDTO> professoresDoCurso;
}
