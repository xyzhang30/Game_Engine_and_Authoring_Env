package oogasalad.view.api.exception;

/**
 * @author Alisha Zhang
 */
public class CreateNewUserException extends RuntimeException {

  /**
   * @param message, error message to be displayed by GUI
   */
  public CreateNewUserException(String message) {
    super(message);
  }

  /**
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the InvalidFileException
   */
  public CreateNewUserException(String message, Throwable cause) {
    super(message, cause);
  }

}
