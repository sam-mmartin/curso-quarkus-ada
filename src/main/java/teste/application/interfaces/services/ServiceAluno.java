package teste.application.interfaces.services;

import teste.application.dto.Mensagem;

public interface ServiceAluno {

   public Mensagem rematricularAluno(String matricula) throws Exception;

   public Mensagem cancelarMatricula(String matricula) throws Exception;
}
