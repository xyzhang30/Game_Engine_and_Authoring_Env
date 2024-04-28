package oogasalad.view.api.authoring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Panel class represents a panel in the authoring environment, providing methods for creating
 * elements and handling events. Implementing classes should provide concrete behavior for these
 * methods.
 *
 * @author Judy He
 */
public interface Panel {

  Logger LOGGER = LogManager.getLogger(Panel.class);
  String RESOURCE_FOLDER_PATH = "view.";
  String VIEW_PROPERTIES_FOLDER = "properties.";
  String UI_FILE_PREFIX = "UIElements";

  /**
   * Creates the UI elements in the panel necessary for the panel's functionality.
   *
   * @throws NoSuchFieldException If there is an error while creating elements due to missing
   *                              fields.
   */
  void createElements() throws NoSuchFieldException;

  /**
   * Handles events evoked on UI components in the panel, such as user input or system events, to
   * provide interactivity and functionality.
   */
  void handleEvents();

}
