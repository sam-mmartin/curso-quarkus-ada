package teste.application.resources;

import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
import teste.application.dto.professor.ProfessorRequestDTO;
import teste.application.dto.professor.ProfessorResponseDTO;
import teste.application.services.ProfessorService;

@RequestScoped
@Tag(name = "Professor")
@Path("/professores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfessorResource {

   @Inject
   ProfessorService service;

   @GET
   public Response listarTodosProfessores() throws Exception {
      return Response.ok(service.getAll()).build();
   }

   @GET
   @Path("/{id}")
   public Response buscarProfessorPorId(@PathParam("id") int id) throws Exception {

      ProfessorResponseDTO professorDTO = service.getById(id);

      if (Objects.isNull(professorDTO)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(professorDTO).build();
   }

   @GET
   @Path("/matricula/{matricula}")
   public Response buscarProfessorPorMatricula(@PathParam("matricula") String matricula) throws Exception {
      ProfessorResponseDTO professorDTO = service.getByMatricula(matricula);

      if (Objects.isNull(professorDTO)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(professorDTO).build();
   }

   @POST
   public Response contratarProfessor(ProfessorRequestDTO professorDTO) throws Exception {
      return Response.status(Response.Status.CREATED).entity(service.create(professorDTO)).build();
   }

   @PUT
   @Path("/atualizar-cadastro/{matricula}")
   public Response atualizarCadastroProfessor(@PathParam("matricula") String matricula,
         ProfessorRequestDTO professorDTO) throws Exception {
      Mensagem mensagem = service.updateCadastro(matricula, professorDTO);

      return Response.ok(mensagem).build();
   }
}
