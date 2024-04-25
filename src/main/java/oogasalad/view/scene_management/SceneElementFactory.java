package oogasalad.view.scene_management;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.view.enums.SceneElementType;
import oogasalad.view.visual_elements.Arrow;

/**
 * Creates elements from parameters received from sceneElementParser; outsources styling and event
 * handling to sceneElementStyler and sceneElementHandler respectively
 *
 * @author Jordan Haytaian
 */
public class SceneElementFactory {

  private final SceneElementStyler sceneElementStyler;
  private final SceneElementHandler sceneElementHandler;
  private final double screenWidth;
  private final double screenHeight;
  private Map<SceneElementType, BiConsumer<Node, Map<String, String>>> elementConfigurationMap;
  private final String classTag = "class";
  private final String textTag = "text";
  private final String typeTag = "type";
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
    this.sceneElementStyler = sceneElementStyler;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    this.sceneElementHandler = sceneElementHandler;
    createElementConfigurationMap();
  }

  /**
   * Creates scene elements based on type specified in parameter list; adds elements to a Pane
   *
   * @param parameterList maps parameter name to value
   * @return container for all scene elements
   */
  public Pane createSceneElements(List<Map<String, String>> parameterList) {
    AnchorPane sceneElementPane = new AnchorPane();
    sceneElementPane.setPrefWidth(screenWidth);
    sceneElementPane.setPrefHeight(screenHeight);

    for (Map<String, String> parameterMap : parameterList) {

      try {
        String className = parameterMap.get(classTag);
        Class<?> classObj = Class.forName(className);
        Object obj = classObj.getDeclaredConstructor().newInstance();
        Node node = (Node) obj;
        configureNode(node, parameterMap);
        sceneElementPane.getChildren().add(node);
      } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
               IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        //TODO: Exception Handling
      }
    }
    return sceneElementPane;
  }

  private void configureNode(Node node, Map<String, String> parameters) {
    executeConfigurationMethod(node, parameters);
    handleLayout(node, parameters);
    handleEvent(node, parameters);
    handleStyle(node, parameters);
  }

  private void configureRectangle(Node node, Map<String, String> parameters) {
    double widthFactor = parseDoubleParameter(parameters, widthFactorTag);
    double heightFactor = parseDoubleParameter(parameters, heightFactorTag);
    Rectangle rectangle = (Rectangle) node;
    rectangle.setWidth(widthFactor * screenWidth);
    rectangle.setHeight(heightFactor * screenHeight);
  }

  private void configureText(Node node, Map<String, String> parameters) {
    String displayText = parameters.get(textTag);
    Text text = (Text) node;
    if (displayText != null) {
      text.setText(displayText);
    }
  }

  private void configureArrow(Node node, Map<String, String> parameters) {
    double xLayoutFactor = parseDoubleParameter(parameters, xLayoutTag);
    double yLayoutFactor = parseDoubleParameter(parameters, yLayoutTag);
    double stemWidth = parseDoubleParameter(parameters, stemWidthTag);
    double stemHeight = parseDoubleParameter(parameters, stemHeightTag);
    double arrowWidthOffset = parseDoubleParameter(parameters, arrowWidthOffsetTag);
    double arrowHeightOffset = parseDoubleParameter(parameters, arrowHeightOffsetTag);

    Arrow arrow = (Arrow) node;

    arrow.setArrowDimensions(xLayoutFactor * screenWidth, yLayoutFactor * screenHeight, stemWidth,
        stemHeight, arrowWidthOffset, arrowHeightOffset);
  }

  private void configureButton(Node node, Map<String, String> parameters) {
    String displayText = parameters.get(textTag);
    double widthFactor = parseDoubleParameter(parameters, widthFactorTag);
    double heightFactor = parseDoubleParameter(parameters, heightFactorTag);

    Button button = (Button) node;
    if (displayText != null) {
      button.setText(displayText);
    }
    button.setPrefSize(widthFactor * screenWidth, heightFactor * screenHeight);
  }

  private void configureListView(Node node, Map<String, String> parameters) {
    double widthFactor = parseDoubleParameter(parameters, widthFactorTag);
    double heightFactor = parseDoubleParameter(parameters, heightFactorTag);

    ListView<String> listView = (ListView<String>) node;
    listView.setPrefSize(widthFactor * screenWidth, heightFactor * screenHeight);
  }


  private void handleLayout(Node node, Map<String, String> parameters) {
    double xLayoutFactor = parseDoubleParameter(parameters, xLayoutFactorTag);
    double yLayoutFactor = parseDoubleParameter(parameters, yLayoutFactorTag);
    node.setLayoutX(xLayoutFactor * screenWidth);
    node.setLayoutY(yLayoutFactor * screenHeight);
  }

  private void handleStyle(Node node, Map<String, String> parameters) {
    String style = parameters.get(styleTag);
    if (style != null) {
      sceneElementStyler.style(node, style);
    }
  }

  private void handleEvent(Node node, Map<String, String> parameters) {
    String event = parameters.get(eventTag);
    if (event != null) {
      sceneElementHandler.createElementHandler(node, event);
    }
  }

  private double parseDoubleParameter(Map<String, String> parameters, String key) {
    try {
      return Double.parseDouble(parameters.get(key));
    } catch (Exception e) {
      return 0;
    }
  }

  private void createElementConfigurationMap() {
    elementConfigurationMap = new HashMap<>();
    elementConfigurationMap.put(SceneElementType.RECTANGLE, this::configureRectangle);
    elementConfigurationMap.put(SceneElementType.ARROW, this::configureArrow);
    elementConfigurationMap.put(SceneElementType.TEXT, this::configureText);
    elementConfigurationMap.put(SceneElementType.BUTTON, this::configureButton);
    elementConfigurationMap.put(SceneElementType.LISTVIEW, this::configureListView);
  }

  private void executeConfigurationMethod(Node node, Map<String, String> parameters) {
    String type = parameters.get(typeTag);
    if (type != null) {
      BiConsumer<Node, Map<String, String>> configurationMethod = elementConfigurationMap.get(
          SceneElementType.valueOf(type));
      if (configurationMethod != null) {
        configurationMethod.accept(node, parameters);
      }
    }
  }
}
