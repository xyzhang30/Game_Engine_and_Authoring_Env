package oogasalad.view.game_environment.scene_management;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
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
    double widthFactor = parseDoubleParameter(parameters, widthFactorTag);
    double heightFactor = parseDoubleParameter(parameters, heightFactorTag);
    double xLayoutFactor = parseDoubleParameter(parameters, xLayoutFactorTag);
    double yLayoutFactor = parseDoubleParameter(parameters, yLayoutFactorTag);
    String style = parameters.get(styleTag);
    String event = parameters.get(eventTag);

    Button button = new Button(displayText);
    styleAndHandleElement(button, style, event);

    button.setPrefSize(screenWidth * widthFactor, screenHeight * heightFactor);
    button.setLayoutX(screenWidth * xLayoutFactor - button.getWidth() / 2);
    button.setLayoutY(screenHeight * yLayoutFactor - button.getHeight() / 2);

    return button;
  }

  private Text createText(Map<String, String> parameters) {
    String displayText = parameters.get(textTag);
    double xLayoutFactor = parseDoubleParameter(parameters, xLayoutFactorTag);
    double yLayoutFactor = parseDoubleParameter(parameters, yLayoutFactorTag);
    String style = parameters.get(styleTag);

    Text text = new Text(displayText);
    sceneElementStyler.style(text, style);

    text.setLayoutX(screenWidth * xLayoutFactor - text.getLayoutBounds().getWidth() / 2);
    text.setLayoutY(screenHeight * yLayoutFactor - text.getLayoutBounds().getHeight() / 2);

    return text;
  }

  private ListView<String> createListView(Map<String, String> parameters) {
    double widthFactor = parseDoubleParameter(parameters, widthFactorTag);
    double heightFactor = parseDoubleParameter(parameters, heightFactorTag);
    double xLayoutFactor = parseDoubleParameter(parameters, xLayoutFactorTag);
    double yLayoutFactor = parseDoubleParameter(parameters, yLayoutFactorTag);
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
    double widthFactor = parseDoubleParameter(parameters, widthFactorTag);
    double heightFactor = parseDoubleParameter(parameters, heightFactorTag);
    double xLayoutFactor = parseDoubleParameter(parameters, xLayoutFactorTag);
    double yLayoutFactor = parseDoubleParameter(parameters, yLayoutFactorTag);
    String style = parameters.get(styleTag);
    String event = parameters.get(eventTag);

    Rectangle rectangle = new Rectangle(screenWidth * widthFactor,
        screenHeight * heightFactor);
    styleAndHandleElement(rectangle, style, event);

    rectangle.setLayoutX(screenWidth * xLayoutFactor);
    rectangle.setLayoutY(screenHeight * yLayoutFactor);

    return rectangle;
  }

  private Polygon createArrow(Map<String, String> parameters) {
    double xPos = parseDoubleParameter(parameters, xLayoutTag);
    double yPos = parseDoubleParameter(parameters, yLayoutTag);
    double stemWidth = parseDoubleParameter(parameters, stemWidthTag);
    double stemHeight = parseDoubleParameter(parameters, stemHeightTag);
    double arrowWidthOffset = parseDoubleParameter(parameters, arrowWidthOffsetTag);
    double arrowHeightOffset = parseDoubleParameter(parameters, arrowHeightOffsetTag);
    String style = parameters.get(styleTag);
    String event = parameters.get(eventTag);

    List<Double> arrowPoints = getArrowPoints(xPos, yPos, stemWidth, stemHeight, arrowWidthOffset,
        arrowHeightOffset);

    Polygon arrow = new Polygon();
    arrow.getPoints().addAll(arrowPoints);
    sceneElementStyler.style(arrow, style);
    sceneElementHandler.createElementHandler(arrow, event);

    return arrow;
  }

  private List<Double> getArrowPoints(double xPos, double yPos, double stemWidth, double stemHeight,
      double arrowWidthOffset,
      double arrowHeightOffset) {
    List<Double> arrowPoints = new ArrayList<>();

    arrowPoints.add(xPos);
    arrowPoints.add(yPos);
    arrowPoints.add(xPos + stemWidth);
    arrowPoints.add(yPos);
    arrowPoints.add(xPos + stemWidth);
    arrowPoints.add(yPos - stemHeight);
    arrowPoints.add(xPos + stemWidth + arrowWidthOffset);
    arrowPoints.add(yPos - stemHeight);
    arrowPoints.add(xPos + stemWidth / 2);
    arrowPoints.add(yPos - stemHeight - arrowHeightOffset);
    arrowPoints.add(xPos - arrowWidthOffset);
    arrowPoints.add(yPos - stemHeight);
    arrowPoints.add(xPos);
    arrowPoints.add(yPos - stemHeight);

    return arrowPoints;
  }

  private void styleAndHandleElement(Node element, String style, String event) {
    sceneElementStyler.style(element, style);
    if (event != null) {
      sceneElementHandler.createElementHandler(element, event);
    }
  }

  private double parseDoubleParameter(Map<String, String> parameters, String key) {
    return Double.parseDouble(parameters.get(key));
  }

}
