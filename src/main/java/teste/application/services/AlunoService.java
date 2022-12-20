package teste.application.services;

import java.util.List;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import teste.application.dto.Mensagem;
import teste.application.dto.aluno.AlunoRequestDTO;
import teste.application.dto.aluno.AlunoResponseDTO;
import teste.application.interfaces.mapper.AlunoMapper;
import teste.application.interfaces.services.ServiceAluno;
import teste.application.interfaces.services.ServiceGenerics;
import teste.domain.VOs.CPF.CPF;
import teste.domain.aluno.Aluno;
import teste.infrastructure.aluno.AlunoRepositoryJDBC;

@RequestScoped
public class AlunoService implements ServiceAluno, ServiceGenerics<AlunoResponseDTO, AlunoRequestDTO> {

   @Inject
   AlunoRepositoryJDBC repositorio;

   @Inject
   AlunoMapper alunoMapper;

   @Override
   public AlunoResponseDTO getById(int id) throws Exception {
      Aluno aluno = repositorio.buscarPorId(id);

      if (Objects.isNull(aluno)) {
         return null;
      }

      return alunoMapper.toResource(aluno);
   }

   @Override
   public List<AlunoResponseDTO> getAll() throws Exception {
      return alunoMapper.listToResource(repositorio.listarTodos());
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public Mensagem create(AlunoRequestDTO alunoDTO) throws Exception {
      Aluno novo = alunoDTO.criarAluno();

      repositorio.matricular(novo);
      Mensagem mensagem = new Mensagem(
            "Aluno: " + novo.getNome() + " matriculado com sucesso. "
                  + "Nº de matricula: " + novo.getMatricula().getNumero());
      return mensagem;
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public Mensagem update(String matricula, AlunoRequestDTO alunoDTO) throws Exception {
      Aluno aluno = repositorio.buscarPorMatricula(matricula);
      aluno.setNome(alunoDTO.getNome());
      aluno.setCpf(new CPF(alunoDTO.getCpf()));

      repositorio.atualizarCadastroDoAluno(aluno);
      Mensagem mensagem = new Mensagem("Cadastro atualizado com sucesso.");
      return mensagem;
   }

   @Override
   public AlunoResponseDTO getByMatricula(String matricula) throws Exception {
      Aluno aluno = repositorio.buscarPorMatricula(matricula);

      if (Objects.isNull(aluno)) {
         return null;
      }

      return alunoMapper.toResource(aluno);
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public Mensagem rematricularAluno(String matricula) throws Exception {
      Aluno aluno = repositorio.buscarPorMatricula(matricula);
      Mensagem mensagem = new Mensagem();

      if (Objects.isNull(aluno)) {
         mensagem.setTexto("Matrícula não encontrada!");
      } else {
         repositorio.rematricular(aluno.getId());
         mensagem.setTexto("Rematrícula realizada com sucesso.");
      }

      return mensagem;
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public Mensagem cancelarMatricula(String matricula) throws Exception {
      Aluno aluno = repositorio.buscarPorMatricula(matricula);
      Mensagem mensagem = new Mensagem();

      if (Objects.isNull(aluno)) {
         mensagem.setTexto("Matrícula não encontrada!");
      } else {
         repositorio.cancelarMatricula(aluno.getId());
         mensagem.setTexto("Matrícula: " + aluno.getMatricula().getNumero() + " cancelada!");
      }

      return mensagem;
   }

}
