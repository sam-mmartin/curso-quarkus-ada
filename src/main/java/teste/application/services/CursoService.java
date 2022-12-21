package teste.application.services;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import teste.application.dto.Mensagem;
import teste.application.dto.curso.CursoRequestDTO;
import teste.application.dto.curso.CursoResponseDTO;
import teste.application.interfaces.mapper.CursoMapper;
import teste.application.interfaces.services.ServiceGenerics;
import teste.application.interfaces.services.ServiceGenerics2;
import teste.domain.curso.Curso;
import teste.infrastructure.curso.CursoRepositoryJDBC;

@RequestScoped
public class CursoService implements ServiceGenerics<CursoResponseDTO, CursoRequestDTO>,
      ServiceGenerics2<CursoResponseDTO, CursoRequestDTO> {

   @Inject
   CursoRepositoryJDBC repositorio;

   @Inject
   CursoMapper cursoMapper;

   @Override
   public CursoResponseDTO getById(int id) throws Exception {
      Curso curso = repositorio.findById((long) id);

      if (Objects.isNull(curso)) {
         return null;
      }

      return cursoMapper.toResource(curso);
   }

   @Override
   public List<CursoResponseDTO> getAll() throws Exception {
      return cursoMapper.listToResource(repositorio.listAll());
   }

   @Override
   @Transactional(rollbackOn = Exception.class)
   public Mensagem create(CursoRequestDTO cursoDTO) throws Exception {
      Curso novo = cursoDTO.criarCurso();

      repositorio.persist(novo);
      Mensagem mensagem = new Mensagem("Curso: " + cursoDTO.getNomeDoCurso() + " implantado com sucesso.");
      return mensagem;
   }

   @Override
   public Mensagem update(int id, CursoRequestDTO cursoDTO) throws Exception {
      repositorio.update("nomeDoCurso = ?1 where id = ?2", cursoDTO.getNomeDoCurso(), id);

      Mensagem mensagem = new Mensagem("Cadastro realizado com sucesso");
      return mensagem;
   }

   @Override
   public void delete(int id) throws Exception {
      repositorio.deleteById((long) id);
   }

   @Override
   public CursoResponseDTO getByName(String name) throws Exception {
      Curso curso = repositorio.buscarPorNomeDoCurso(name);

      if (Objects.isNull(curso)) {
         return null;
      }

      return cursoMapper.toResource(curso);
   }

}
