package teste.domain.mappeamento;

public interface RepositoryCursoProfessor {

   public void create(CursoProfessor cursoProfessor) throws Exception;

   public void delete(long idCurso, int idProfessor) throws Exception;

}
