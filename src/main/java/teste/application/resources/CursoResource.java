package teste.application.resources;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import teste.application.dto.Mensagem;
import teste.application.dto.curso.GradeCurricularResponseDTO;
import teste.application.dto.curso.CursoProfessoresResponseDTO;
import teste.application.dto.curso.CursoRequestDTO;
import teste.application.dto.curso.CursoResponseDTO;
import teste.application.dto.disciplina.DisciplinaRequestDTO;
import teste.application.services.CursoService;

@RequestScoped
@Tag(name = "Curso")
@Path("/cursos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CursoResource {

   @Inject
   CursoService service;

   // #region GETS
   @GET
   public Response listarTodosCursos() throws Exception {
      return Response.ok(service.getAll()).build();
   }

   @GET
   @Path("/{id}")
   public Response buscarCursoPorId(@PathParam("id") int id) throws Exception {
      CursoResponseDTO cursoDTO = service.getById(id);

      if (Objects.isNull(cursoDTO)) {
         return Response.status((Response.Status.NOT_FOUND)).build();
      }

      return Response.ok(cursoDTO).build();
   }

   @GET
   @Path("/grade-curricular/{id}")
   public Response gradeCurricular(@PathParam("id") int id) throws Exception {
      GradeCurricularResponseDTO cursoDisciplina = service.getCursoAndDisciplinas(id);

      if (Objects.isNull(cursoDisciplina)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(cursoDisciplina).build();
   }

   @GET
   @Path("/cursos-disciplinas")
   public Response listarTodosCursosEDisciplinas() throws Exception {
      List<GradeCurricularResponseDTO> cursosDisciplinas = service.getAllCursosAndDisciplinas();

      return Response.ok(cursosDisciplinas).build();
   }

   @GET
   @Path("/buscar-por-nome/{nomeDoCurso}")
   public Response buscarCursoPorNome(@PathParam("nomeDoCurso") String nomeDoCurso) throws Exception {
      CursoResponseDTO cursoDTO = service.getByName(nomeDoCurso);

      if (Objects.isNull(cursoDTO)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(cursoDTO).build();
   }

   @GET
   @Path("/listar-professores/{id}")
   public Response listarProfessoresDoCurso(@PathParam("id") int id) throws Exception {
      CursoProfessoresResponseDTO lista = service.getCursoAndProfessores(id);

      return Response.ok(lista).build();
   }

   @GET
   @Path("/cursos-professores")
   public Response listarTodosCursosEProfessores() throws Exception {
      List<CursoProfessoresResponseDTO> listagem = service.getAllCursosAndProfessores();

      return Response.ok(listagem).build();
   }
   // #endregion

   @POST
   public Response implantarCurso(CursoRequestDTO cursoDTO) throws Exception {
      return Response.status(Response.Status.CREATED).entity(service.create(cursoDTO)).build();
   }

   @PUT
   @Path("/{id}")
   public Response atualizarCurso(@PathParam("id") int id, CursoRequestDTO cursoDTO) throws Exception {
      Mensagem mensagem = service.update(id, cursoDTO);
      return Response.ok(mensagem).build();
   }

   @PUT
   @Path("/adicionar-disciplina/{id}")
   public Response adicionarDisciplinaAoCurso(@PathParam("id") int idCurso,
         DisciplinaRequestDTO disciplinaDTO)
         throws Exception {
      Mensagem mensagem = service.addDisciplinaToCurso(idCurso, disciplinaDTO);
      return Response.ok(mensagem).build();
   }

   @PUT
   @Path("/adicionar-professor/{id}")
   public Response adicionarProfessorAoCurso(@PathParam("id") int id, String matricula)
         throws Exception {
      Mensagem mensagem = service.addProfessorToCurso(id, matricula);

      return Response.ok(mensagem).build();
   }

   @DELETE
   @Path("/{id}")
   public Response desimplantarCurso(@PathParam("id") int id) throws Exception {
      CursoResponseDTO cursoDTO = service.getById(id);

      if (Objects.isNull(cursoDTO)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      service.delete(id);
      return Response.status(Response.Status.NO_CONTENT).build();
   }

   // @DELETE
   // @Path("/remover-disciplina/{id_curso}-{id_disciplina}")
   // public Response removerDisciplinadoDoCurso(@PathParam("id_curso") long
   // idCurso,
   // @PathParam("id_disciplina") long idDisciplina) throws Exception {
   // Mensagem mensagem = service.removeDisciplinaFromCurso(idCurso, idDisciplina);

   // if (Objects.isNull(mensagem)) {
   // return Response.status(Response.Status.NOT_FOUND).build();
   // }

   // return Response.status(Response.Status.NO_CONTENT).entity(mensagem).build();
   // }

}
