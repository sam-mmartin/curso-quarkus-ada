package teste.application.services;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import teste.application.dto.AlunoDTO;
import teste.application.interfaces.services.ServicePessoa;
import teste.domain.VOs.CPF.CPF;
import teste.domain.VOs.Matricula.Matricula;
import teste.domain.aluno.Aluno;
import teste.infrastructure.aluno.AlunoRepositoryH2;

@RequestScoped
public class AlunoService implements ServicePessoa<Aluno, AlunoDTO> {

   @Inject
   AlunoRepositoryH2 repositorio;

   @Override
   public AlunoDTO getById(int id) throws Exception {
      Aluno aluno = repositorio.buscarPorId(id);

      if (Objects.isNull(aluno)) {
         return null;
      }

      return new AlunoDTO(aluno.getId(),
            aluno.getNome(),
            aluno.getCpf().getNumero(),
            aluno.isStatus());
   }

   @Override
   public List<Aluno> getAll() throws Exception {
      // System.out.println(repositorio.listarTodos());
      return repositorio.listarTodos();
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public Aluno save(AlunoDTO alunoDTO) throws Exception {
      Aluno novo = alunoDTO.criarAluno();
      novo.setMatricula(gerarMatricula());
      repositorio.matricular(novo);
      return repositorio.buscarPorId(novo.getId());
   }

   @Override
   public void remove(int id) {
      repositorio.cancelarMatricula(id);
   }

   @Override
   public void update(int id, AlunoDTO alunoDTO) throws Exception {
      Aluno aluno = repositorio.buscarPorId(id);
      aluno.setNome(alunoDTO.getNome());
      aluno.setCpf(new CPF(alunoDTO.getCpf()));
      aluno.setStatus(alunoDTO.isStatus());

      repositorio.atualizarCadastroDoAluno(id, aluno);
   }

   private Matricula gerarMatricula() {
      Random random = new Random();
      int number = random.nextInt(9000) + 1000;
      int validator = random.nextInt(1000);

      return new Matricula(number + "-" + validator);
   }

}
