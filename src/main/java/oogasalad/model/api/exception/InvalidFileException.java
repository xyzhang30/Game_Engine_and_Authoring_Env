package oogasalad.model.api.exception;

/**
 * The InvalidFileException extends RuntimeException to handle any errors related parsing JSON files
 * for game configuration
 *
 * @author Judy He
 */
public class InvalidFileException extends RuntimeException {

  /**
   * Initialize a new InvalidFileException given an error message
   *
   * @param message, error message to be displayed by GUI
   */
  public InvalidFileException(String message) {
    super(message);
  }

  /**
   * Initialize a new InvalidFileException given an error message and another exception
   *
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the InvalidFileException
   */
  public InvalidFileException(String message, Throwable cause) {
    super(message, cause);
  }

}
