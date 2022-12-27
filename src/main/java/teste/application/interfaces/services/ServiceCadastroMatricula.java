package teste.application.interfaces.services;

import teste.application.dto.Mensagem;

public interface ServiceCadastroMatricula<T1, T2> {

   public Mensagem updateCadastro(String matricula, T2 objetoDTO) throws Exception;

   public T1 getByMatricula(String matricula) throws Exception;

}
