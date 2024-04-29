package oogasalad.model.api.exception;

/**
 * @author Alisha Zhang
 */
public class MissingJsonGameInfoException extends RuntimeException {

  /**
   * @param message, error message to be displayed by GUI
   */
  public MissingJsonGameInfoException(String message) {
    super(message);
  }

  /**
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the InvalidFileException
   */
  public MissingJsonGameInfoException(String message, Throwable cause) {
    super(message, cause);
  }

}
