package oogasalad.model.api.exception;

/**
 * @author Alisha Zhang
 */
public class InvalidCommandException extends RuntimeException {

  /**
   * Initialize a new InvalidFileException given an error message
   *
   * @param message, error message to be displayed by GUI
   */
  public InvalidCommandException(String message) {
    super(message);
  }

  /**
   * Initialize a new InvalidFileException given an error message and another exception
   *
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the InvalidFileException
   */
  public InvalidCommandException(String message, Throwable cause) {
    super(message, cause);
  }

}
