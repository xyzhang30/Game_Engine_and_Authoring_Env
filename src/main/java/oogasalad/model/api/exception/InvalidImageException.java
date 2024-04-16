package oogasalad.model.api.exception;

/**
 * The InvalidFileException extends RuntimeException to handle any errors related parsing JSON files
 * for game configuration
 *
 * @author Alisha Zhang
 */
public class InvalidImageException extends RuntimeException {

  /**
   * Initialize a new InvalidFileException given an error message
   *
   * @param message, error message to be displayed by GUI
   */
  public InvalidImageException(String message) {
    super(message);
  }

  /**
   * Initialize a new InvalidFileException given an error message and another exception
   *
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the InvalidFileException
   */
  public InvalidImageException(String message, Throwable cause) {
    super(message, cause);
  }

}
