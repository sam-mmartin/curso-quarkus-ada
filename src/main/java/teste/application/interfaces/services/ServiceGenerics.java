package teste.application.interfaces.services;

import java.util.List;

public interface ServiceGenerics<T1, T2> {

   public T2 getById(int id) throws Exception;

   public List<T1> getAll() throws Exception;

   public T1 create(T2 objeto) throws Exception;

   public void delete(int id) throws Exception;

   public void update(int id, T2 objeto) throws Exception;
}
