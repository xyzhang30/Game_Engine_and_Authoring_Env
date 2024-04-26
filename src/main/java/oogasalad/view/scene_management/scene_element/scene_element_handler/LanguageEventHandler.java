package oogasalad.view.scene_management.scene_element.scene_element_handler;

import java.util.Map;
import javafx.scene.Node;
import oogasalad.view.api.enums.SceneElementEvent;
import oogasalad.view.api.enums.SupportedLanguage;
import oogasalad.view.scene_management.scene_managers.SceneManager;

public class LanguageEventHandler {
  private final SceneManager sceneManager;
  private Map<SceneElementEvent, SupportedLanguage> languageMap;

  /**
   * Constructs a new instance of the SceneElementHandler class.
   * <p>
   * This constructor initializes a SceneElementHandler object with the specified game controller
   * and scene manager. The game controller is responsible for managing the game state, while the
   * scene manager is responsible for managing different scenes within the game environment.
   * Additionally, the constructor sets the angle increment value to a default of 5 degrees.
   * @param sceneManager      An instance of the `SceneManager` class, responsible for managing
   */
  public LanguageEventHandler(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
    createLanguageMap();
  }

  /**
   * Handles events for the specified node based on the given event type.
   * <p>
   * This method checks the event type of the given node and delegates the handling of the event to
   * the appropriate method. It checks for different types of events such as scene change events,
   * gameplay change events, and striking events, and calls the corresponding handler method for
   * each event type.
   *
   * @param node  The scene element node to handle events for.
   * @param event A string representing the type of event to handle.
   */
  public void createElementHandler(Node node, String event) {
    createLanguageHandler(node, languageMap.get(event));
  }

  private void createLanguageMap() {
    languageMap.put(SceneElementEvent.SET_ENGLISH, SupportedLanguage.ENGLISH);
    languageMap.put(SceneElementEvent.SET_SPANISH, SupportedLanguage.SPANISH);
    languageMap.put(SceneElementEvent.SET_FRENCH, SupportedLanguage.FRENCH);
  }

  private void createLanguageHandler(Node node, SupportedLanguage language) {
    node.setOnMouseClicked(e -> {
      sceneManager.setLanguage(language);
      sceneManager.createTitleScene();
    });
  }

}
