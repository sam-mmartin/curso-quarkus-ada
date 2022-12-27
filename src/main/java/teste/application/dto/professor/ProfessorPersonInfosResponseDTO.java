package teste.application.dto.professor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class ProfessorPersonInfosResponseDTO {

   private int id;
   private String nome;
   private String cpf;
   private String matricula;
   private String estado;
   private String dataCriacao;
   private String dataAtualizacao;

}
