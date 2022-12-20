package teste.application.interfaces.services;

import java.util.List;

import teste.application.dto.Mensagem;

public interface ServiceGenerics<T1, T2> {

   public T1 getById(int id) throws Exception;

   public List<T1> getAll() throws Exception;

   public Mensagem create(T2 objetoDTO) throws Exception;

   public Mensagem update(String matricula, T2 objetoDTO) throws Exception;

   public T1 getByMatricula(String matricula) throws Exception;
}
