package oogasalad.view.game_environment.scene_management;

import java.util.List;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.view.enums.SceneElementType;

/**
 * Creates elements from parameters received from sceneElementParser; outsources styling and event
 * handling to sceneElementStyler and sceneElementHandler respectively
 *
 * @author Jordan Haytaian
 */
public class SceneElementFactory {

  private final SceneElementParser sceneElementParser;
  private final SceneElementStyler sceneElementStyler;
  private final SceneElementHandler sceneElementHandler;
  private final double screenWidth;
  private final double screenHeight;
  private final String typeTag = "type";
  private final String textTag = "text";
  private final String widthFactorTag = "width_factor";
  private final String heightFactorTag = "height_factor";
  private final String xLayoutFactorTag = "x_layout_factor";
  private final String yLayoutFactorTag = "y_layout_factor";
  private final String xLayoutTag = "x_layout";
  private final String yLayoutTag = "y_layout";
  private final String stemWidthTag = "stem_width";
  private final String stemHeightTag = "stem_height";
  private final String arrowWidthOffsetTag = "arrow_width_offset";
  private final String arrowHeightOffsetTag = "arrow_height_offset";
  private final String styleTag = "styling";
  private final String eventTag = "event";

  /**
   * Constructor creates sceneElementParser to get parameters from xml files
   *
   * @param screenWidth         screen width used for ratio scaling elements
   * @param screenHeight        screen height used for ratio scaling elements
   * @param sceneElementStyler  styles elements using css stylesheet
   * @param sceneElementHandler adds event handling to elements
   */
  public SceneElementFactory(double screenWidth, double screenHeight,
      SceneElementStyler sceneElementStyler, SceneElementHandler sceneElementHandler) {
    sceneElementParser = new SceneElementParser();
    this.sceneElementStyler = sceneElementStyler;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    this.sceneElementHandler = sceneElementHandler;
  }

  /**
   * Creates scene elements based on type specified in parameter list; adds elements to a Pane
   *
   * @param parameterList maps parameter name to value
   * @return container for all scene elements
   */
  public Pane createSceneElements(List<Map<String, String>> parameterList) {
    AnchorPane sceneElements = new AnchorPane();
    sceneElements.setPrefWidth(screenWidth);
    sceneElements.setPrefHeight(screenHeight);

    for (Map<String, String> parameterMap : parameterList) {
      String type = parameterMap.get(typeTag);
      switch (SceneElementType.valueOf(type)) {
        case BUTTON -> {
          sceneElements.getChildren().add(createButton(parameterMap));
        }
        case TEXT -> {
          sceneElements.getChildren().add(createText(parameterMap));
        }
        case LISTVIEW -> {
          sceneElements.getChildren().add(createListView(parameterMap));
        }
        case RECTANGLE -> {
          sceneElements.getChildren().add(createRectangle(parameterMap));
        }
        case ARROW -> {
          sceneElements.getChildren().add(createArrow(parameterMap));
        }
      }
    }
    return sceneElements;
  }

  private Button createButton(Map<String, String> parameters) {
    String displayText = parameters.get(textTag);
    double widthFactor = Double.parseDouble(parameters.get(widthFactorTag));
    double heightFactor = Double.parseDouble(parameters.get(heightFactorTag));
    double xLayoutFactor = Double.parseDouble(parameters.get(xLayoutFactorTag));
    double yLayoutFactor = Double.parseDouble(parameters.get(yLayoutFactorTag));
    String style = parameters.get(styleTag);
    String event = parameters.get(eventTag);

    Button button = new Button(displayText);
    sceneElementStyler.style(button, style);
    sceneElementHandler.createElementHandler(button, event);

    button.setPrefSize(screenWidth * widthFactor, screenHeight * heightFactor);
    button.setLayoutX(screenWidth * xLayoutFactor - button.getWidth() / 2);
    button.setLayoutY(screenHeight * yLayoutFactor - button.getHeight() / 2);

    return button;
  }

  private Text createText(Map<String, String> parameters) {
    String displayText = parameters.get(textTag);
    double xLayoutFactor = Double.parseDouble(parameters.get(xLayoutFactorTag));
    double yLayoutFactor = Double.parseDouble(parameters.get(yLayoutFactorTag));
    String style = parameters.get(styleTag);

    Text text = new Text(displayText);
    sceneElementStyler.style(text, style);

    text.setLayoutX(screenWidth * xLayoutFactor - text.getLayoutBounds().getWidth() / 2);
    text.setLayoutY(screenHeight * yLayoutFactor - text.getLayoutBounds().getHeight() / 2);

    return text;
  }

  private ListView<String> createListView(Map<String, String> parameters) {
    double widthFactor = Double.parseDouble(parameters.get(widthFactorTag));
    double heightFactor = Double.parseDouble(parameters.get(heightFactorTag));
    double xLayoutFactor = Double.parseDouble(parameters.get(xLayoutFactorTag));
    double yLayoutFactor = Double.parseDouble(parameters.get(yLayoutFactorTag));
    String style = parameters.get(styleTag);
    String event = parameters.get(eventTag);

    ListView<String> listView = new ListView<>(sceneElementParser.getGameTitles());
    sceneElementStyler.style(listView, style);
    sceneElementHandler.createElementHandler(listView, event);

    listView.setPrefSize(screenWidth * widthFactor, screenHeight * heightFactor);
    listView.setLayoutX(screenWidth * xLayoutFactor - listView.getPrefWidth() / 2);
    listView.setLayoutY(screenHeight * yLayoutFactor);

    return listView;
  }

  private Rectangle createRectangle(Map<String, String> parameters) {
    double widthFactor = Double.parseDouble(parameters.get(widthFactorTag));
    double heightFactor = Double.parseDouble(parameters.get(heightFactorTag));
    double xLayoutFactor = Double.parseDouble(parameters.get(xLayoutFactorTag));
    double yLayoutFactor = Double.parseDouble(parameters.get(yLayoutFactorTag));
    String style = parameters.get(styleTag);
    String event = parameters.get(eventTag);

    Rectangle rectangle = new Rectangle(screenWidth * widthFactor,
        screenHeight * heightFactor);
    sceneElementStyler.style(rectangle, style);

    if (event != null) {
      sceneElementHandler.createElementHandler(rectangle, event);
    }

    rectangle.setLayoutX(screenWidth * xLayoutFactor);
    rectangle.setLayoutY(screenHeight * yLayoutFactor);

    return rectangle;
  }

  private Polygon createArrow(Map<String, String> parameters) {
    double xPos = Integer.parseInt(parameters.get(xLayoutTag));
    double yPos = Integer.parseInt(parameters.get(yLayoutTag));
    double stemWidth = Integer.parseInt(parameters.get(stemWidthTag));
    double stemHeight = Integer.parseInt(parameters.get(stemHeightTag));
    double arrowWidthOffset = Integer.parseInt(parameters.get(arrowWidthOffsetTag));
    double arrowHeightOffset = Integer.parseInt(parameters.get(arrowHeightOffsetTag));
    String style = parameters.get(styleTag);
    String event = parameters.get(eventTag);

    double x1 = xPos;
    double y1 = yPos;
    double x2 = xPos + stemWidth;
    double y2 = yPos;
    double x3 = xPos + stemWidth;
    double y3 = yPos - stemHeight;
    double x4 = xPos + stemWidth + arrowWidthOffset;
    double y4 = yPos - stemHeight;
    double x5 = xPos + stemWidth / 2;
    double y5 = yPos - stemHeight - arrowHeightOffset;
    double x6 = xPos - arrowWidthOffset;
    double y6 = yPos - stemHeight;
    double x7 = xPos;
    double y7 = yPos - stemHeight;

    Polygon arrow = new Polygon();
    arrow.getPoints().addAll(
        x1, y1,  // Tail
        x2, y2,  // Shaft
        x3, y3,  // Base of arrowhead
        x4, y4,  // Tip of arrowhead
        x5, y5,  // Arrowhead side
        x6, y6,  // Arrowhead side
        x7, y7   // Closing the shape back at the tail
    );
    sceneElementStyler.style(arrow, style);
    sceneElementHandler.createElementHandler(arrow, event);

    return arrow;
  }

}
