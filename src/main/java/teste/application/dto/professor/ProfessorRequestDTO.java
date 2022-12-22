package teste.application.dto.professor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProfessorRequestDTO {

   private String nome;
   private String cpf;

}
