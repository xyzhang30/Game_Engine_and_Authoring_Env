package oogasalad.model.api.exception;

/**
 * @author Alisha Zhang
 */
public class InCompleteRulesAuthoringException extends RuntimeException {

  /**
   * @param message, error message to be displayed by GUI
   */
  public InCompleteRulesAuthoringException(String message) {
    super(message);
  }

  /**
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the InvalidFileException
   */
  public InCompleteRulesAuthoringException(String message, Throwable cause) {
    super(message, cause);
  }

}
