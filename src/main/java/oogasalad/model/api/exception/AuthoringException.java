package oogasalad.model.api.exception;

/**
 * @author Alisha Zhang
 */
public class AuthoringException extends RuntimeException {

  /**
   * @param message, error message to be displayed by GUI
   */
  public AuthoringException(String message) {
    super(message);
  }

  /**
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the InvalidFileException
   */
  public AuthoringException(String message, Throwable cause) {
    super(message, cause);
  }

}
