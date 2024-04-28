package oogasalad.model.api.exception;

/**
 * @author Alisha Zhang
 */
public class InvalidColorParsingException extends RuntimeException {

  /**
   * @param message, error message to be displayed by GUI
   */
  public InvalidColorParsingException(String message) {
    super(message);
  }

  /**
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the InvalidFileException
   */
  public InvalidColorParsingException(String message, Throwable cause) {
    super(message, cause);
  }

}
