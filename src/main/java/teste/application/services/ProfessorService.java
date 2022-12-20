package teste.application.services;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import teste.application.dto.Mensagem;
import teste.application.dto.professor.ProfessorRequestDTO;
import teste.application.dto.professor.ProfessorResponseDTO;
import teste.application.interfaces.mapper.ProfessorMapper;
import teste.application.interfaces.services.ServiceGenerics;
import teste.domain.VOs.CPF.CPF;
import teste.domain.professor.Professor;
import teste.infrastructure.professor.ProfessorRepositoryJDBC;

@RequestScoped
public class ProfessorService implements ServiceGenerics<ProfessorResponseDTO, ProfessorRequestDTO> {

   @Inject
   ProfessorRepositoryJDBC repositorio;

   @Inject
   ProfessorMapper professorMapper;

   @Override
   public ProfessorResponseDTO getById(int id) throws Exception {
      return professorMapper.toResource(repositorio.buscarPorId(id));
   }

   @Override
   public List<ProfessorResponseDTO> getAll() throws Exception {
      return professorMapper.listToResource(repositorio.listarTodos());
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public Mensagem create(ProfessorRequestDTO professorDTO) throws Exception {
      Professor novo = professorDTO.criaProfessor();

      repositorio.contratar(novo);
      Mensagem mensagem = new Mensagem(
            "Realizado contrato do professor: " + novo.getNome()
                  + ". Nº de matricula: " + novo.getMatricula().getNumero());
      return mensagem;
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public Mensagem update(String matricula, ProfessorRequestDTO professorDTO) throws Exception {
      Professor professor = repositorio.buscarPorMatricula(matricula);
      professor.setNome(professorDTO.getNome());
      professor.setCpf(new CPF(professorDTO.getCpf()));

      repositorio.atualizarCadastroDoProfessor(professor);
      Mensagem mensagem = new Mensagem("Cadastro atualizado com sucesso.");
      return mensagem;
   }

   @Override
   public ProfessorResponseDTO getByMatricula(String matricula) throws Exception {
      Professor professor = repositorio.buscarPorMatricula(matricula);

      if (Objects.isNull(professor)) {
         return null;
      }

      return professorMapper.toResource(professor);
   }

}
