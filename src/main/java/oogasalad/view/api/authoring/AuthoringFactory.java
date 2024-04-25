package oogasalad.view.api.authoring;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Interface representing an authoring factory allowing for different implementations
 * for configuring the game authoring environment features.
 *
 * @author Judy He
 */
public interface AuthoringFactory {

  Logger LOGGER = LogManager.getLogger(AuthoringFactory.class);

  String RESOURCE_FOLDER_PATH = "view.";
  String UI_FILE_PREFIX = "UIElements";
  String DEFAULT_VALUES_FILE = "DefaultAuthoringValues";
  ResourceBundle defaultValuesResourceBundle = ResourceBundle.getBundle(RESOURCE_FOLDER_PATH + DEFAULT_VALUES_FILE);

  /**
   * Creates a list of UI elements that set up configuring game objects.
   *
   * @return a list of UI elements that set up configuring game objects.
   */
  List<Node> createGameObjectsConfiguration();
  /**
   * Creates a list of UI elements that set up configuring Surface game objects.
   *
   * @return a list of UI elements that set up configuring Surface game objects.
   */
  List<Node> createSurfacesConfiguration();
  /**
   * Creates a list of UI elements for configuring collidable game objects.
   *
   * @return a list of UI elements for configuring collidables.
   */
  List<Node> createCollidablesConfiguration();
  /**
   * Creates a list of UI elements for configuring players in the game.
   *
   * @return a list of UI elements for configuring players.
   */
  List<Node> createPlayersConfiguration();
  /**
   * Resets certain UI elements to their initial state.
   */
  void resetAuthoringElements();


}
