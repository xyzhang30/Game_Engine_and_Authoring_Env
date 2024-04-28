package oogasalad.view.scene_management.scene_element.scene_element_handler;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import oogasalad.view.api.enums.SceneElementEvent;
import oogasalad.view.api.enums.SupportedLanguage;
import oogasalad.view.scene_management.scene_managers.SceneManager;

/**
 * The LanguageEventHandler class handles events related to setting the application's language. It
 * maps scene element events to corresponding supported languages and sets up an event handler to
 * change the application's language when a node is clicked.
 *
 * @author Jordan Haytaian
 */
public class LanguageEventHandler {

  private final SceneManager sceneManager;
  private Map<SceneElementEvent, SupportedLanguage> languageMap;


  /**
   * Constructs a LanguageEventHandler with the specified SceneManager. Initializes the language map
   * by creating the mapping between SceneElementEvents and SupportedLanguage enums.
   *
   * @param sceneManager The scene manager for handling scene transitions and updates.
   */
  public LanguageEventHandler(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
    createLanguageMap();
  }


  /**
   * Creates an event handler for the specified node and event type. The handler will set the
   * application's language and create the title scene when the event occurs on the given node.
   *
   * @param node  The node to which the event handler will be attached.
   * @param event The event type as a string that specifies the event to handle.
   */
  public void createElementHandler(Node node, String event) {
    createLanguageHandler(node, languageMap.get(SceneElementEvent.valueOf(event)));
  }

  private void createLanguageMap() {
    languageMap = new HashMap<>();
    languageMap.put(SceneElementEvent.SET_ENGLISH, SupportedLanguage.ENGLISH);
    languageMap.put(SceneElementEvent.SET_SPANISH, SupportedLanguage.SPANISH);
    languageMap.put(SceneElementEvent.SET_FRENCH, SupportedLanguage.FRENCH);
  }

  private void createLanguageHandler(Node node, SupportedLanguage language) {
    node.setOnMouseClicked(e -> {
      sceneManager.setLanguage(language);
      sceneManager.createLoginScene();
    });
  }

}
