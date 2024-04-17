package oogasalad.view.api.exception;

/**
 * The MissingNonControllableTypeException extends RuntimeException to handle error when a type is
 * not defined for a non-controllable shape
 *
 * @author Judy He
 */
public class MissingNonControllableTypeException extends RuntimeException {

  /**
   * Initialize a new MissingNonControllableTypeException given an error message
   *
   * @param message, error message to be displayed by GUI
   */
  public MissingNonControllableTypeException(String message) {
    super(message);
  }

  /**
   * Initialize a new MissingNonControllableTypeException.java given an error message and another
   * exception
   *
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the MissingInteractionException
   */
  public MissingNonControllableTypeException(String message, Throwable cause) {
    super(message, cause);
  }

}
