package teste.application.dto.curso;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import teste.application.dto.aluno.AlunosByCursoResponseDTO;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CursoAlunosResponseDTO {

   private long id;
   private String nomeDoCurso;
   private List<AlunosByCursoResponseDTO> alunosMatriculados;
}
