package oogasalad.view;

import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class ElementStyler {

  private final Pane root;
  private final String cssPath = "/view/styles.css";

  public ElementStyler(Pane root) {
    this.root = root;
    addStyleSheetToRoot();
  }

  public void style(Map<Node, String> styleMap) {
    for (Entry<Node, String> styleEntry : styleMap.entrySet()) {
      Node node = styleEntry.getKey();
      String style = styleEntry.getValue();
      node.getStyleClass().add(style);
    }
  }

  private void addStyleSheetToRoot() {
    URL url = getClass().getResource(cssPath);
    if (url != null) {
      root.getStylesheets().add(url.toExternalForm());
    }
  }

}
