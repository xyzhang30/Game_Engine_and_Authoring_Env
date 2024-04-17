package oogasalad.view.api.exception;

/**
 * The MissingInteractionException extends RuntimeException to handle error when an interaction is
 * not defined for a pair of shapes
 *
 * @author Judy He
 */
public class MissingInteractionException extends RuntimeException {

  /**
   * Initialize a new MissingInteractionException given an error message
   *
   * @param message, error message to be displayed by GUI
   */
  public MissingInteractionException(String message) {
    super(message);
  }

  /**
   * Initialize a new MissingInteractionException given an error message and another exception
   *
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the MissingInteractionException
   */
  public MissingInteractionException(String message, Throwable cause) {
    super(message, cause);
  }

}
