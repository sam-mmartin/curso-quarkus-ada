package teste.application.interfaces.services;

import teste.application.dto.Mensagem;

public interface ServiceGenerics2<T1, T2> {

   public Mensagem update(int id, T2 objectDTO) throws Exception;

   public void delete(int id) throws Exception;

   public T1 getByName(String name) throws Exception;
}
