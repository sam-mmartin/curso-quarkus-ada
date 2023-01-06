package teste.application.resources;

import java.util.Set;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
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
import teste.application.dto.aluno.AlunoCursoRequestDTO;
import teste.application.dto.aluno.AlunoRequestDTO;
import teste.application.dto.aluno.AlunoResponseDTO;
import teste.application.exceptions.ErrorResponse;
import teste.application.services.AlunoService;

@RequestScoped
@Tag(name = "Aluno")
@Path("/alunos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlunoResource {

   @Inject
   AlunoService service;

   @Inject
   Validator validator;

   @GET
   public Response listarTodosAlunos() throws Exception {
      return Response.ok(service.getAll()).build();
   }

   @GET
   @Path("/{id}")
   public Response buscarAlunoPorId(@PathParam("id") int id) throws Exception {
      AlunoResponseDTO alunoDTO = service.getById(id);
      return Response.ok(alunoDTO).build();
   }

   @GET
   @Path("/matricula/{matricula}")
   public Response buscarAlunoPorMatricula(@PathParam("matricula") String matricula) throws Exception {
      AlunoResponseDTO alunoDTO = service.getByMatricula(matricula);
      return Response.ok(alunoDTO).build();
   }

   @GET
   @Path("/listar-por-curso/{id_curso}")
   public Response listarTodosAlunosDeUmCurso(@PathParam("id_curso") int idCurso) throws Exception {
      return Response.ok(service.getAlunosByCurso(idCurso)).build();
   }

   @POST
   public Response matricularAluno(AlunoCursoRequestDTO alunoDTO) throws Exception {
      Set<ConstraintViolation<AlunoCursoRequestDTO>> violations = validator.validate(alunoDTO);

      if (!violations.isEmpty()) {
         return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(violations)).build();
      }

      return Response.status(Response.Status.CREATED).entity(service.create(alunoDTO)).build();
   }

   @PUT
   @Path("/{matricula}")
   public Response atualizarCadastroAluno(@PathParam("matricula") String matricula, AlunoRequestDTO alunoDTO)
         throws Exception {
      Set<ConstraintViolation<AlunoRequestDTO>> violations = validator.validate(alunoDTO);

      if (!violations.isEmpty()) {
         return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(violations)).build();
      }

      Mensagem mensagem = service.updateCadastro(matricula, alunoDTO);
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

   @DELETE
   @Path("/{id}")
   public Response apagarRegistroAcademico(@PathParam("id") int id) throws Exception {
      service.apagarAluno(id);

      return Response.noContent().build();
   }
}
