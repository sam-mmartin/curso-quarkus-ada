package teste.application.resources;

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
import teste.application.dto.disciplina.DisciplinaRequestDTO;
import teste.application.dto.disciplina.DisciplinaResponseDTO;
import teste.application.services.DisciplinaService;

@RequestScoped
@Tag(name = "Disciplina")
@Path("/disciplinas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DisciplinaResource {

   @Inject
   DisciplinaService service;

   @GET
   public Response listarTodasDisciplinas() throws Exception {
      return Response.ok(service.getAll()).build();
   }

   @GET
   @Path("/{id}")
   public Response buscarDisciplinaPorId(@PathParam("id") int id) throws Exception {
      DisciplinaResponseDTO disciplinaDTO = service.getById(id);

      if (Objects.isNull(disciplinaDTO)) {
         return Response.status((Response.Status.NOT_FOUND)).build();
      }

      return Response.ok(disciplinaDTO).build();
   }

   @POST
   public Response implantarDisciplina(DisciplinaRequestDTO disciplinaDTO) throws Exception {
      return Response.status(Response.Status.CREATED).entity(service.create(disciplinaDTO)).build();
   }

   @PUT
   @Path("/{id}")
   public Response atualizarDisciplina(@PathParam("id") int id, DisciplinaRequestDTO disciplinaDTO) throws Exception {
      Mensagem mensagem = service.update(id, disciplinaDTO);
      return Response.ok(mensagem).build();
   }

   @DELETE
   @Path("/{id}")
   public Response desimplantarDisciplina(@PathParam("id") int id) throws Exception {
      DisciplinaResponseDTO disciplina = service.getById(id);

      if (Objects.isNull(disciplina)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      service.delete(id);
      return Response.status(Response.Status.NO_CONTENT).build();
   }

   @GET
   @Path("/buscar-por-nome/{nomeDaDisciplina}")
   public Response buscarDisciplinaPorNome(@PathParam("nomeDaDisciplina") String nomeDaDisciplina) throws Exception {
      DisciplinaResponseDTO disciplina = service.getByName(nomeDaDisciplina);

      if (Objects.isNull(disciplina)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(disciplina).build();
   }
}
