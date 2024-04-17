package oogasalad.view.game_environment.game_environment_scene;

import java.net.URL;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class SceneElementStyler {

  private final Pane root;
  private final String cssPath = "/view/styles.css";

  public SceneElementStyler(Pane root) {
    this.root = root;
    addStyleSheetToRoot();
  }

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
