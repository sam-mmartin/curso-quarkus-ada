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
import teste.application.dto.aluno.AlunoRequestDTO;
import teste.application.dto.aluno.AlunoResponseDTO;
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
      return Response.ok(service.getAll()).build();
   }

   @GET
   @Path("/{id}")
   public Response buscarAlunoPorId(@PathParam("id") int id) throws Exception {

      AlunoResponseDTO alunoDTO = service.getById(id);

      if (Objects.isNull(alunoDTO)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(alunoDTO).build();
   }

   @GET
   @Path("/matricula/{matricula}")
   public Response buscarAlunoPorMatricula(@PathParam("matricula") String matricula) throws Exception {
      AlunoResponseDTO alunoDTO = service.getByMatricula(matricula);

      if (Objects.isNull(alunoDTO)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(alunoDTO).build();
   }

   @POST
   public Response matricularAluno(AlunoRequestDTO alunoDTO) throws Exception {
      return Response.status(Response.Status.CREATED).entity(service.create(alunoDTO)).build();
   }

   @PUT
   @Path("/atualizar-cadastro/{matricula}")
   public Response atualizarCadastroAluno(@PathParam("matricula") String matricula, AlunoRequestDTO alunoDTO)
         throws Exception {
      Mensagem mensagem = service.update(matricula, alunoDTO);

      return Response.ok(mensagem).build();
   }

   @PUT
   @Path("/rematricular/{matricula}")
   public Response rematricularAluno(@PathParam("matricula") String matricula) throws Exception {
      Mensagem mensagem = service.rematricularAluno(matricula);

      return Response.ok(mensagem).build();
   }

   @PUT
   @Path("/cancelar-matricula/{matricula}")
   public Response cancelarMatriculaAluno(@PathParam("matricula") String matricula) throws Exception {
      Mensagem mensagem = service.cancelarMatricula(matricula);

      return Response.ok(mensagem).build();
   }
}
