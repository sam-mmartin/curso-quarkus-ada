package teste.application.interfaces.services;

import teste.application.dto.aluno.AlunoResponseDTO;

public interface ServiceAluno {

   public AlunoResponseDTO getAlunoByMatricula(String matricula) throws Exception;

   public AlunoResponseDTO rematricularAluno(String matricula) throws Exception;

   public AlunoResponseDTO cancelarMatricula(String matricula) throws Exception;
}
