package teste.application.exceptions;

public class CustomConstraintException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public CustomConstraintException() {
   }

   public CustomConstraintException(String message) {
      super(message);
   }

   public CustomConstraintException(String message, Throwable cause) {
      super(message, cause);
   }

   public CustomConstraintException(Throwable cause) {
      super(cause);
   }

   public CustomConstraintException(String message, Throwable cause,
         boolean enableSuppression, boolean writableStackTrace) {

      super(message, cause, enableSuppression, writableStackTrace);
   }
}
