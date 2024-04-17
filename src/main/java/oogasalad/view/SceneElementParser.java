package oogasalad.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SceneElementParser {

  private final double screenWidth;
  private final double screenHeight;
  private final String nodeTag = "node";
  private final String typeTag = "type";
  private final String textTag = "text";
  private final String widthFactorTag = "width_factor";
  private final String heightFactorTag = "height_factor";
  private final String xLayoutFactorTag = "x_layout_factor";
  private final String yLayoutFactorTag = "y_layout_factor";
  private final String styleTag = "styling";

  public SceneElementParser(double screenWidth, double screenHeight) {
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
  }

  public Map<Node, String> createElementsFromFile(String filePath) throws Exception {
    Map<Node, String> sceneElements = new HashMap<>();
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    Document doc = documentBuilder.parse(filePath);

    Element root = doc.getDocumentElement();
    root.normalize();

    NodeList nodeList = root.getElementsByTagName(nodeTag);

    for (int i = 0; i < nodeList.getLength(); i++) {
      Element element = (Element) nodeList.item(i);
      createSceneElement(element, sceneElements);
    }

    return sceneElements;
  }

  private void createSceneElement(Element element, Map<Node, String> sceneElements) {
    String type = element.getElementsByTagName(typeTag).item(0).getTextContent();
    switch (SceneElementType.valueOf(type)) {
      case BUTTON -> {
        createButton(element, sceneElements);
      }
      case TEXT -> {
        createText(element, sceneElements);
      }
    }
  }

  private void createButton(Element element, Map<Node, String> sceneElements) {
    String displayText = element.getElementsByTagName(textTag).item(0).getTextContent();
    double widthFactor = Double.parseDouble(
        element.getElementsByTagName(widthFactorTag).item(0).getTextContent());
    double heightFactor = Double.parseDouble(
        element.getElementsByTagName(heightFactorTag).item(0).getTextContent());
    double xLayoutFactor = Double.parseDouble(
        element.getElementsByTagName(xLayoutFactorTag).item(0).getTextContent());
    double yLayoutFactor = Double.parseDouble(
        element.getElementsByTagName(yLayoutFactorTag).item(0).getTextContent());
    String style = element.getElementsByTagName(styleTag).item(0).getTextContent();

    Button button = new Button(displayText);

    button.setPrefSize(screenWidth * widthFactor, screenHeight * heightFactor);
    button.setLayoutX(screenWidth * xLayoutFactor - button.getWidth() / 2);
    button.setLayoutY(screenHeight * yLayoutFactor - button.getHeight() / 2);

    sceneElements.put(button, style);
  }

  private void createText(Element element, Map<Node, String> sceneElements) {
    String displayText = element.getElementsByTagName(textTag).item(0).getTextContent();
    double xLayoutFactor = Double.parseDouble(
        element.getElementsByTagName(xLayoutFactorTag).item(0).getTextContent());
    double yLayoutFactor = Double.parseDouble(
        element.getElementsByTagName(yLayoutFactorTag).item(0).getTextContent());
    String style = element.getElementsByTagName(styleTag).item(0).getTextContent();

    Text text = new Text(displayText);
    text.getStyleClass().add(style);

    text.setLayoutX(screenWidth * xLayoutFactor - text.getLayoutBounds().getWidth() / 2);
    text.setLayoutY(screenHeight * yLayoutFactor - text.getLayoutBounds().getHeight() / 2);

    sceneElements.put(text, style);
  }

  private ListView createListView(Element element) {
    return null;
  }

}
