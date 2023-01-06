package teste.application.dto.professor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class ProfessorBasicInfoResponseDTO {

   private String nome;
   private String matricula;
}
