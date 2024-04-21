package oogasalad.view.authoring_environment.panels;

/**
 * Represents a panel in the authoring environment, providing methods for creating elements and handling events.
 * Implementing classes should provide concrete behavior for these methods.
 *
 * @author Judy He
 */
public interface Panel {

  /**
   * Creates the UI elements in the panel necessary for the panel's functionality.
   *
   * @throws NoSuchFieldException If there is an error while creating elements due to missing fields.
   */
  void createElements() throws NoSuchFieldException;

  /**
   * Handles events evoked on UI components in the panel, such as user input or system events, to provide interactivity and functionality.
   */
  void handleEvents();

}
