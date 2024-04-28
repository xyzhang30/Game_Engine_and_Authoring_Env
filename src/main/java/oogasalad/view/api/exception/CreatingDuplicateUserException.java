package oogasalad.view.api.exception;

/**
 * @author Alisha Zhang
 */
public class CreatingDuplicateUserException extends RuntimeException {

  /**
   * @param message, error message to be displayed by GUI
   */
  public CreatingDuplicateUserException(String message) {
    super(message);
  }

  /**
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the InvalidFileException
   */
  public CreatingDuplicateUserException(String message, Throwable cause) {
    super(message, cause);
  }

}
