package oogasalad.view.playing_scene;

import java.net.URL;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class ElementStyler {

  private final Pane root;
  private final String cssPath = "/view/styles.css";

  public ElementStyler(Pane root) {
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
