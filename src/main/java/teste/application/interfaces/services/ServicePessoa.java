package teste.application.interfaces.services;

import java.util.List;

public interface ServicePessoa<T1, T2> {

   public T2 getById(int id) throws Exception;

   public List<T1> getAll() throws Exception;

   public T1 save(T2 objeto) throws Exception;

   public void remove(int id) throws Exception;

   public void update(int id, T2 objeto) throws Exception;
}
