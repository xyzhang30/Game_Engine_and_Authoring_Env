package oogasalad.model.api.exception;

/**
 * @author Alisha Zhang
 */
public class IncompletePlayerStrikeableAuthoringException extends RuntimeException {

  /**
   * @param message, error message to be displayed by GUI
   */
  public IncompletePlayerStrikeableAuthoringException(String message) {
    super(message);
  }

  /**
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the InvalidFileException
   */
  public IncompletePlayerStrikeableAuthoringException(String message, Throwable cause) {
    super(message, cause);
  }

}
