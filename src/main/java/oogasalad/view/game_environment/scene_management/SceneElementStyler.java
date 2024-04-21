package oogasalad.view.game_environment.scene_management;

import java.net.URL;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Styles scene elements using css style sheets
 *
 * @author Jordan Haytaian
 */
public class SceneElementStyler {
  private final Pane root;
  private final String cssPath = "/view/styles.css";

  /**
   * Constructor adds style sheet to root
   * @param root root node of scene
   */
  public SceneElementStyler(Pane root) {
    this.root = root;
    addStyleSheetToRoot();
  }

  /**
   * Styles an element using the specified style tag
   * @param node the element to be styled
   * @param style the css style tag to add
   */
  public void style(Node node, String style) {
    node.getStyleClass().add(style);
  }

  private void addStyleSheetToRoot() {
    URL url = getClass().getResource(cssPath);
    if (url != null) {
      root.getStylesheets().add(url.toExternalForm());
    }
  }

}
