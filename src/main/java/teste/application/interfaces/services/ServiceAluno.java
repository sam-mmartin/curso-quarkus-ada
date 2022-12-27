package teste.application.interfaces.services;

import java.util.List;

import teste.application.dto.Mensagem;
import teste.application.dto.aluno.AlunosByCursoResponseDTO;

public interface ServiceAluno {

   public Mensagem rematricularAluno(String matricula) throws Exception;

   public Mensagem cancelarMatricula(String matricula) throws Exception;

   public List<AlunosByCursoResponseDTO> getAlunosByCurso(int idCurso) throws Exception;
}
