package teste.application.dto.professor;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import teste.application.dto.mapeamento.NameResponseDTO;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class ProfessorMasterInfosResponseDTO {

   private String nome;
   private String matricula;
   private String estado;
   private List<NameResponseDTO> cursos;
   private List<NameResponseDTO> disciplinas;
}
