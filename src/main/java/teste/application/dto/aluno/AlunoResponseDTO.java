package teste.application.dto.aluno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class AlunoResponseDTO {

   private int id;
   private String nome;
   private String cpf;
   private String matricula;
   private String estado;
   private String curso;
   private String dataCriacao;
   private String dataAtualizacao;

}
