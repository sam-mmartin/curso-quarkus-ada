package teste.application.resources;

import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
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
import teste.application.dto.curso.CursoRequestDTO;
import teste.application.dto.disciplina.DisciplinaRequestDTO;
import teste.application.dto.professor.ProfessorMasterInfosResponseDTO;
import teste.application.dto.professor.ProfessorRequestDTO;
import teste.application.dto.professor.ProfessorPersonInfosResponseDTO;
import teste.application.services.ProfessorService;

@RequestScoped
@Tag(name = "Professor")
@Path("/professores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfessorResource {

   @Inject
   ProfessorService service;

   @Inject
   Validator validator;

   @GET
   public Response listarTodosProfessores() throws Exception {
      return Response.ok(service.getAll()).build();
   }

   @GET
   @Path("/{id}")
   public Response buscarProfessorPorId(@PathParam("id") int id) throws Exception {

      ProfessorPersonInfosResponseDTO professorDTO = service.getById(id);

      if (Objects.isNull(professorDTO)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(professorDTO).build();
   }

   @GET
   @Path("/matricula/{matricula}")
   public Response buscarProfessorPorMatricula(@PathParam("matricula") String matricula) throws Exception {
      ProfessorMasterInfosResponseDTO professorDTO = service.getByMatricula(matricula);

      if (Objects.isNull(professorDTO)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(professorDTO).build();
   }

   @POST
   public Response contratarProfessor(ProfessorRequestDTO professorDTO) throws Exception {
      var response = service.create(professorDTO);

      return Response.status(Response.Status.CREATED).entity(response).build();
   }

   @PUT
   @Path("/{matricula}")
   public Response atualizarCadastroProfessor(@PathParam("matricula") String matricula,
         ProfessorRequestDTO professorDTO) throws Exception {
      var professor = service.updateCadastro(matricula, professorDTO);

      return Response.ok(professor).build();
   }

   @PUT
   @Path("/lecionar-disciplina/{matricula}")
   public Response lecionarDisciplina(@PathParam("matricula") String matricula,
         DisciplinaRequestDTO disciplinaRequestDTO) throws Exception {
      var professor = service.teachDiscipline(matricula, disciplinaRequestDTO);

      if (Objects.isNull(professor)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(professor).build();
   }

   @PUT
   @Path("/remover-disciplina/{matricula}")
   public Response pararDeLecionarDisciplina(@PathParam("matricula") String matricula,
         DisciplinaRequestDTO disciplinaRequestDTO) throws Exception {
      var professor = service.stopTeachingDiscipline(matricula, disciplinaRequestDTO);

      if (Objects.isNull(professor)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(professor).build();
   }

   @PUT
   @Path("/adicionar-curso/{matricula}")
   public Response adicionarCurso(@PathParam("matricula") String matricula,
         CursoRequestDTO cursoDTO) throws Exception {
      var professor = service.addCurso(matricula, cursoDTO);

      if (Objects.isNull(professor)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(professor).build();
   }

   @PUT
   @Path("/remover-curso/{matricula}")
   public Response removerCurso(@PathParam("matricula") String matricula,
         CursoRequestDTO cursoDTO) throws Exception {
      Mensagem mensagem = service.removeCurso(matricula, cursoDTO);

      if (Objects.isNull(mensagem)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(mensagem).build();
   }
}
