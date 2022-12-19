package teste.application.services;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import teste.application.dto.aluno.AlunoRequestDTO;
import teste.application.dto.aluno.AlunoResponseDTO;
import teste.application.interfaces.services.ServiceAluno;
import teste.domain.VOs.CPF.CPF;
import teste.domain.VOs.Matricula.Matricula;
import teste.domain.aluno.Aluno;
import teste.infrastructure.aluno.AlunoRepositoryH2;

@RequestScoped
public class AlunoService implements ServiceAluno {

   @Inject
   AlunoRepositoryH2 repositorio;

   public AlunoResponseDTO getById(int id) throws Exception {
      Aluno aluno = repositorio.buscarPorId(id);

      if (Objects.isNull(aluno)) {
         return null;
      }

      return retornaDTO(aluno);
   }

   public List<Aluno> getAll() throws Exception {
      return repositorio.listarTodos();
   }

   @Transactional(rollbackOn = Exception.class)
   public Aluno create(AlunoRequestDTO alunoDTO) throws Exception {
      Aluno novo = alunoDTO.criarAluno();
      novo.setStatus(true);
      novo.setMatricula(gerarMatricula());

      repositorio.matricular(novo);
      return repositorio.buscarPorId(novo.getId());
   }

   public void update(int id, AlunoRequestDTO alunoDTO) throws Exception {
      Aluno aluno = repositorio.buscarPorId(id);
      aluno.setNome(alunoDTO.getNome());
      aluno.setCpf(new CPF(alunoDTO.getCpf()));

      repositorio.atualizarCadastroDoAluno(aluno);
   }

   private AlunoResponseDTO retornaDTO(Aluno aluno) {

      AlunoResponseDTO alunoDTO = new AlunoResponseDTO(
            aluno.getId(),
            aluno.getNome(),
            aluno.getCpf().getNumero(),
            aluno.getMatricula().getNumero(),
            aluno.isStatus());

      return alunoDTO;
   }

   private Matricula gerarMatricula() {
      Random random = new Random();
      int number = random.nextInt(9000) + 1000;
      int validator = random.nextInt(1000);

      return new Matricula(number + "-" + validator);
   }

   @Override
   public AlunoResponseDTO getAlunoByMatricula(String matricula) throws Exception {
      Aluno aluno = repositorio.buscarPorMatricula(matricula);

      if (Objects.isNull(aluno)) {
         return null;
      }

      return retornaDTO(aluno);
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public AlunoResponseDTO rematricularAluno(String matricula) throws Exception {
      Aluno aluno = repositorio.buscarPorMatricula(matricula);

      if (Objects.isNull(aluno)) {
         return null;
      }

      repositorio.rematricular(aluno.getId());
      return retornaDTO(aluno);
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public AlunoResponseDTO cancelarMatricula(String matricula) throws Exception {
      Aluno aluno = repositorio.buscarPorMatricula(matricula);

      if (Objects.isNull(aluno)) {
         return null;
      }

      repositorio.cancelarMatricula(aluno.getId());
      return retornaDTO(aluno);
   }

}
