package oogasalad.view.scene_management;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

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
  private final String classTag = "class";
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
    AnchorPane sceneElementPane = new AnchorPane();
    sceneElementPane.setPrefWidth(screenWidth);
    sceneElementPane.setPrefHeight(screenHeight);

    for (Map<String, String> parameterMap : parameterList) {

      try {
        String className = parameterMap.get(classTag);
        Class<?> clazz = Class.forName(className);
        Object obj = clazz.getDeclaredConstructor().newInstance();
        Node node = (Node) obj;

        configureElement(node, parameterMap);

        sceneElementPane.getChildren().add(node);
      } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
               IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        System.out.println("exception in create elements");
        //TODO: Exception Handling
      }
    }
    return sceneElementPane;
  }

  private void configureElement(Node node, Map<String, String> parameters) {
    String displayText = parameters.get(textTag);
    double widthFactor = parseDoubleParameter(parameters, widthFactorTag);
    double heightFactor = parseDoubleParameter(parameters, heightFactorTag);
    double xLayoutFactor = parseDoubleParameter(parameters, xLayoutFactorTag);
    double yLayoutFactor = parseDoubleParameter(parameters, yLayoutFactorTag);
    String style = parameters.get(styleTag);
    String event = parameters.get(eventTag);

    handleTextDisplay(node, displayText);
    handlePrefSize(node, widthFactor, heightFactor);
    handleLayout(node, xLayoutFactor, yLayoutFactor);
    handleStyle(node, style);
    handleEvent(node, event);
  }

  private void handleTextDisplay(Node node, String displayText) {
    if (displayText != null) {
      try {
        Method setDisplayTextMethod = node.getClass().getMethod("setText", String.class);
        setDisplayTextMethod.invoke(node, displayText);
      } catch (NoSuchMethodException | SecurityException | IllegalAccessException |
               IllegalArgumentException | InvocationTargetException e) {
        //TODO: Excepetion Handling
        System.out.println("exception in create text");
      }
    }
  }

  private void handlePrefSize(Node node, double widthFactor, double heightFactor) {
    if (widthFactor != 0 && heightFactor != 0) {
      try {
        Method setPrefSizeMethod = node.getClass()
            .getMethod("setPrefSize", double.class, double.class);
        setPrefSizeMethod.invoke(node, widthFactor * screenWidth, heightFactor * screenHeight);
      } catch (NoSuchMethodException | SecurityException | IllegalAccessException |
               IllegalArgumentException | InvocationTargetException e) {
        //TODO: Excepetion Handling
        System.out.println("exception in size");
      }
    }
  }

  private void handleLayout(Node node, double xLayoutFactor, double yLayoutFactor) {
    if (xLayoutFactor != 0 && yLayoutFactor != 0) {
      try {
        Method setLayoutXMethod = node.getClass()
            .getMethod("setLayoutX", double.class);
        Method setLayoutYMethod = node.getClass()
            .getMethod("setLayoutY", double.class);
        setLayoutXMethod.invoke(node,
            xLayoutFactor * screenWidth - node.getLayoutBounds().getWidth() / 2);
        setLayoutYMethod.invoke(node,
            yLayoutFactor * screenHeight - node.getLayoutBounds().getHeight() / 2);
      } catch (NoSuchMethodException | SecurityException | IllegalAccessException |
               IllegalArgumentException | InvocationTargetException e) {
        //TODO: Excepetion Handling
        System.out.println("exception in layout");
      }
    }
  }

  private void handleStyle(Node node, String style) {
    if (style != null) {
      sceneElementStyler.style(node, style);
    }
  }

  private void handleEvent(Node node, String event) {
    if (event != null) {
      sceneElementHandler.createElementHandler(node, event);
    }
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

  private double parseDoubleParameter(Map<String, String> parameters, String key) {
    try {
      return Double.parseDouble(parameters.get(key));
    } catch (Exception e) {
      return 0;
    }

  }

}
