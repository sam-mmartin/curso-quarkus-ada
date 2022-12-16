package teste.application.services;

import teste.domain.aluno.interfaces.RepositoryAluno;

public class ServiceAluno {

   private final RepositoryAluno repositorio;

   public ServiceAluno(RepositoryAluno repositorio) {
      this.repositorio = repositorio;
   }

}
