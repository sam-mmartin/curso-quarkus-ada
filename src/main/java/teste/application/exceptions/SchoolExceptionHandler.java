package teste.application.exceptions;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SchoolExceptionHandler implements ExceptionMapper<Exception> {

   @Override
   public Response toResponse(Exception exception) {
      if (exception instanceof ConstraintViolationException) {
         return Response.status(400).entity(new ErrorResponse(exception.getMessage(), false)).build();
      }

      if (exception instanceof CustomConstraintException) {
         return Response.status(400).entity(new ErrorResponse(exception.getMessage(), false)).build();
      }

      return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(new ErrorResponse(exception.getMessage()))
            .build();
   }

}
