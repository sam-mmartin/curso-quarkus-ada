package teste.application.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import teste.application.dto.Mensagem;
import teste.application.dto.disciplina.DisciplinaRequestDTO;
import teste.application.dto.disciplina.DisciplinaResponseDTO;
import teste.application.interfaces.mapper.DisciplinaMapper;
import teste.application.interfaces.services.ServiceGenerics;
import teste.application.interfaces.services.ServiceGenerics2;
import teste.domain.disciplina.Disciplina;
import teste.infrastructure.disciplina.DisciplinaRepositoryJDBC;

@RequestScoped
public class DisciplinaService
      implements ServiceGenerics<DisciplinaResponseDTO, DisciplinaRequestDTO>,
      ServiceGenerics2<DisciplinaResponseDTO, DisciplinaRequestDTO> {

   @Inject
   DisciplinaRepositoryJDBC repositorio;

   @Inject
   DisciplinaMapper disciplinaMapper;

   @Override
   public DisciplinaResponseDTO getById(int id) throws Exception {
      Disciplina disciplina = repositorio.findById((long) id);

      if (Objects.isNull(disciplina)) {
         return null;
      }

      return disciplinaMapper.toResource(disciplina);
   }

   @Override
   public List<DisciplinaResponseDTO> getAll() throws Exception {
      return disciplinaMapper.listToResource(repositorio.listAll());
   }

   @Override
   public DisciplinaResponseDTO getByName(String nomeDaDisciplina) throws Exception {
      Disciplina disciplina = repositorio.buscarPorDisciplina(nomeDaDisciplina);

      if (Objects.isNull(disciplina)) {
         return null;
      }

      return disciplinaMapper.toResource(disciplina);
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public Mensagem create(DisciplinaRequestDTO disciplinaDTO) throws Exception {
      LocalDateTime dateTime = LocalDateTime.now();
      Disciplina novo = disciplinaDTO.criarDisciplina();

      novo.setDataCriacao(dateTime);
      novo.setDataAtualizacao(dateTime);
      repositorio.persist(novo);

      Mensagem mensagem = new Mensagem(
            "Disciplina: " + disciplinaDTO.getNomeDaDisciplina() + " implantada com sucesso.");
      return mensagem;
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public Mensagem update(int id, DisciplinaRequestDTO disciplinaDTO) throws Exception {
      LocalDateTime dateTime = LocalDateTime.now();
      repositorio.update("nomeDaDisciplina = ?1, data_atualizacao = ?2 where id = ?3",
            disciplinaDTO.getNomeDaDisciplina(),
            dateTime,
            (long) id);

      Mensagem mensagem = new Mensagem("Cadastro atualizado com sucesso.");
      return mensagem;
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public void delete(int id) throws Exception {
      repositorio.deleteById((long) id);
   }

}
