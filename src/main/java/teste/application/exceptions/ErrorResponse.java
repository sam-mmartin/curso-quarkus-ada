package teste.application.exceptions;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

public class ErrorResponse {

   private String message;
   private boolean success;

   public ErrorResponse(String message) {
      this.success = true;
      this.message = message;
   }

   public ErrorResponse(String message, boolean success) {
      this.success = success;
      this.message = message;
   }

   public ErrorResponse(Set<? extends ConstraintViolation<?>> violations) {
      this.success = false;
      this.message = violations.stream()
            .map(cv -> cv.getMessage())
            .collect(Collectors.joining("; "));
   }

   public String getMessage() {
      return message;
   }

   public boolean isSuccess() {
      return success;
   }

}