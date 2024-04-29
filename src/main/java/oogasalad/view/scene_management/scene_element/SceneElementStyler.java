package oogasalad.view.scene_management.scene_element;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import oogasalad.view.api.enums.ThemeType;

/**
 * Styles scene elements using css style sheets
 *
 * @author Jordan Haytaian
 */
public class SceneElementStyler {

  private final Pane root;
  private final String defaultPath = "/view/styles/default_style.css";
  private final String darkPath = "/view/styles/dark_style.css";
  private final String funPath = "/view/styles/fun_style.css";
  private Map<ThemeType, String> themePathMap;

  /**
   * Constructor adds style sheet to root
   *
   * @param root root node of scene
   */
  public SceneElementStyler(Pane root) {
    this.root = root;
    createThemePathMap();
    addStyleSheetToRoot(defaultPath);
  }

  /**
   * Changes theme by changing root's style sheet; gets style sheet by retrieving mapping to
   * specified theme enum
   *
   * @param selectedTheme represents user selected theme
   */
  public void changeTheme(ThemeType selectedTheme) {
    addStyleSheetToRoot(themePathMap.get(selectedTheme));
  }

  /**
   * Styles an element using the specified style tag
   *
   * @param node  the element to be styled
   * @param style the css style tag to add
   */
  public void style(Node node, String style) {
    node.getStyleClass().add(style);
  }

  private void addStyleSheetToRoot(String cssPath) {
    URL url = getClass().getResource(cssPath);
    if (url != null) {
      root.getStylesheets().clear();
      root.getStylesheets().add(url.toExternalForm());
    }
  }

  private void createThemePathMap() {
    themePathMap = new HashMap<>();
    themePathMap.put(ThemeType.DEFAULT, defaultPath);
    themePathMap.put(ThemeType.DARK, darkPath);
    themePathMap.put(ThemeType.FUN, funPath);
  }

}
