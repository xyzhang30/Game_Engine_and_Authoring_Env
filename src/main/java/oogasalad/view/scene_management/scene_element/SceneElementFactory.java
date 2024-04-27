package oogasalad.view.scene_management.scene_element;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.view.api.enums.SceneElementType;
import oogasalad.view.api.enums.SupportedLanguage;
import oogasalad.view.api.enums.XMLTags;
import oogasalad.view.scene_management.scene_element.scene_element_handler.SceneElementHandler;
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
  private final LanguageManager languageManager;
  private final double screenWidth;
  private final double screenHeight;
  private SupportedLanguage language;
  private Map<SceneElementType, BiConsumer<Node, Map<String, String>>> elementConfigurationMap;

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
    languageManager = new LanguageManager();
    createElementConfigurationMap();
  }

  /**
   * Creates scene elements based on type specified in parameter list; adds elements to a Pane
   *
   * @param parameterList maps parameter name to value
   * @return container for all scene elements
   */
  public Pane createSceneElements(List<Map<String, String>> parameterList,
      SupportedLanguage language) {
    this.language = language;
    AnchorPane sceneElementPane = new AnchorPane();
    sceneElementPane.setPrefWidth(screenWidth);
    sceneElementPane.setPrefHeight(screenHeight);

    for (Map<String, String> parameterMap : parameterList) {

      try {
        String className = parameterMap.get(XMLTags.CLASS.name().toLowerCase());
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
    double widthFactor = parseDoubleParameter(parameters,
        XMLTags.WIDTH_FACTOR.name().toLowerCase());
    double heightFactor = parseDoubleParameter(parameters,
        XMLTags.HEIGHT_FACTOR.name().toLowerCase());
    Rectangle rectangle = (Rectangle) node;
    rectangle.setWidth(widthFactor * screenWidth);
    rectangle.setHeight(heightFactor * screenHeight);
  }

  private void configureText(Node node, Map<String, String> parameters) {
    String textTag = parameters.get(XMLTags.TEXT.name().toLowerCase());
    double widthFactor = parseDoubleParameter(parameters,
        XMLTags.WIDTH_FACTOR.name().toLowerCase());
    Text text = (Text) node;
    if (widthFactor != 0) {
      text.setWrappingWidth(widthFactor * screenWidth);
    }
    if (textTag != null) {
      String translatedText = languageManager.getText(language, textTag);
      text.setText(translatedText);
    }
  }

  private void configureArrow(Node node, Map<String, String> parameters) {
    double xLayoutFactor = parseDoubleParameter(parameters, "x_layout");
    double yLayoutFactor = parseDoubleParameter(parameters, "y_layout");
    double stemWidth = parseDoubleParameter(parameters, "stem_width");
    double stemHeight = parseDoubleParameter(parameters, "stem_height");
    double arrowWidthOffset = parseDoubleParameter(parameters, "arrow_width_offset");
    double arrowHeightOffset = parseDoubleParameter(parameters, "arrow_height_offset");
    Arrow arrow = (Arrow) node;
    arrow.setArrowDimensions(xLayoutFactor * screenWidth, yLayoutFactor * screenHeight, stemWidth,
        stemHeight, arrowWidthOffset, arrowHeightOffset);


  }

    private void configureButton(Node node, Map<String, String> parameters) {
    String textTag = parameters.get(XMLTags.TEXT.name().toLowerCase());
    double widthFactor = parseDoubleParameter(parameters,
        XMLTags.WIDTH_FACTOR.name().toLowerCase());
    double heightFactor = parseDoubleParameter(parameters,
        XMLTags.HEIGHT_FACTOR.name().toLowerCase());

    Button button = (Button) node;
    if (textTag != null) {
      String translatedText = languageManager.getText(language, textTag);
      button.setText(translatedText);
    }
    button.setPrefSize(widthFactor * screenWidth, heightFactor * screenHeight);
  }

  private void configureComboBox(Node node, Map<String, String> parameters) {
    String textTag = parameters.get(XMLTags.TEXT.name().toLowerCase());
    double widthFactor = parseDoubleParameter(parameters,
        XMLTags.WIDTH_FACTOR.name().toLowerCase());
    double heightFactor = parseDoubleParameter(parameters,
        XMLTags.HEIGHT_FACTOR.name().toLowerCase());

    ComboBox<String> comboBox = (ComboBox<String>) node;
    if (textTag != null) {
      String translatedText = languageManager.getText(language, textTag);
      comboBox.setPromptText(translatedText);
    }
    comboBox.setPrefSize(widthFactor * screenWidth, heightFactor * screenHeight);
  }

  private void configureListView(Node node, Map<String, String> parameters) {
    double widthFactor = parseDoubleParameter(parameters,
        XMLTags.WIDTH_FACTOR.name().toLowerCase());
    double heightFactor = parseDoubleParameter(parameters,
        XMLTags.HEIGHT_FACTOR.name().toLowerCase());

    ListView<String> listView = (ListView<String>) node;
    listView.setPrefSize(widthFactor * screenWidth, heightFactor * screenHeight);
  }


  private void handleLayout(Node node, Map<String, String> parameters) {
    double xLayoutFactor = parseDoubleParameter(parameters,
        XMLTags.X_LAYOUT_FACTOR.name().toLowerCase());
    double yLayoutFactor = parseDoubleParameter(parameters,
        XMLTags.Y_LAYOUT_FACTOR.name().toLowerCase());
    node.setLayoutX(xLayoutFactor * screenWidth);
    node.setLayoutY(yLayoutFactor * screenHeight);
  }

  private void handleStyle(Node node, Map<String, String> parameters) {
    String style = parameters.get(XMLTags.STYLE.name().toLowerCase());
    if (style != null) {
      sceneElementStyler.style(node, style);
    }
  }

  private void handleEvent(Node node, Map<String, String> parameters) {
    String event = parameters.get(XMLTags.EVENT.name().toLowerCase());
    String eventType = parameters.get(XMLTags.EVENT_TYPE.name().toLowerCase());
    if (event != null) {
      sceneElementHandler.createElementHandler(node, eventType, event);
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
    elementConfigurationMap.put(SceneElementType.COMBOBOX, this::configureComboBox);
  }

  private void executeConfigurationMethod(Node node, Map<String, String> parameters) {
    String type = parameters.get(XMLTags.TYPE.name().toLowerCase());
    if (type != null) {
      BiConsumer<Node, Map<String, String>> configurationMethod = elementConfigurationMap.get(
          SceneElementType.valueOf(type));
      if (configurationMethod != null) {
        configurationMethod.accept(node, parameters);
      }
    }
  }
}
