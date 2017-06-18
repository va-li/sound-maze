package virtual.exceptions;

/**
 * Created by Valentin on 17.06.2017.
 * TODO documentation
 */
public class UnableToMoveException extends Exception {

  public UnableToMoveException() {
  }

  public UnableToMoveException(String message) {
    super(message);
  }

  public UnableToMoveException(String message, Throwable cause) {
    super(message, cause);
  }
}
