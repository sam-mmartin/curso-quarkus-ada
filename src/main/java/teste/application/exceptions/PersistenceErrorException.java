package teste.application.exceptions;

public class PersistenceErrorException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public PersistenceErrorException() {
   }

   public PersistenceErrorException(String message) {
      super(message);
   }

   public PersistenceErrorException(String message, Throwable cause) {
      super(message, cause);
   }

   public PersistenceErrorException(Throwable cause) {
      super(cause);
   }

   public PersistenceErrorException(String message, Throwable cause,
         boolean enableSuppression, boolean writableStackTrace) {

      super(message, cause, enableSuppression, writableStackTrace);
   }
}
