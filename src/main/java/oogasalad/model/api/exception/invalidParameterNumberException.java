package oogasalad.model.api.exception;

public class invalidParameterNumberException extends RuntimeException {

  /**
   * Initialize a new InvalidFileException given an error message
   *
   * @param message, error message to be displayed by GUI
   */
  public invalidParameterNumberException(String message) {
    super(message);
  }

  /**
   * Initialize a new InvalidFileException given an error message and another exception
   *
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the InvalidFileException
   */
  public invalidParameterNumberException(String message, Throwable cause) {
    super(message, cause);
  }

}
