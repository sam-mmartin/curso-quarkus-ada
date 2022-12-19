package teste.application.resources;

import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import teste.application.dto.AlunoDTO;
import teste.application.services.AlunoService;

@RequestScoped
@Tag(name = "Aluno")
@Path("/alunos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlunoResource {

   @Inject
   AlunoService service;

   @GET
   public Response listarTodosAlunos() throws Exception {
      // System.out.println(service.getAll());
      return Response.ok(service.getAll()).build();
   }

   @GET
   @Path("/{id}")
   public Response buscarAlunoPorId(@PathParam("id") int id) throws Exception {

      AlunoDTO alunoDTO = service.getById(id);

      if (Objects.isNull(alunoDTO)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(alunoDTO).build();
   }

   @POST
   public Response matricularAluno(AlunoDTO alunoDTO) throws Exception {
      return Response.status(Response.Status.CREATED).entity(service.save(alunoDTO)).build();
   }
}
