package oogasalad.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

  public SceneElementParser(double screenWidth, double screenHeight) {
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
  }

  public List<Node> createElementsFromFile(String filePath) throws Exception {
    List<Node> sceneElements = new ArrayList<>();
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    Document doc = documentBuilder.parse(filePath);

    Element root = doc.getDocumentElement();
    root.normalize();

    NodeList nodeList = root.getElementsByTagName(nodeTag);

    for (int i = 0; i < nodeList.getLength(); i++) {
      Element element = (Element) nodeList.item(i);
      sceneElements.add(createSceneElement(element));
    }

    return sceneElements;
  }

  private Node createSceneElement(Element element) {
    String type = element.getElementsByTagName(typeTag).item(0).getTextContent();
    switch (SceneElementType.valueOf(type)) {
      case BUTTON -> {
        return createButton(element);
      }
      case TEXT -> {
        return createText(element);
      }
    }
  }

  private Button createButton(Element element) {
    String displayText = element.getElementsByTagName(textTag).item(0).getTextContent();
    double widthFactor = Double.parseDouble(
        element.getElementsByTagName(widthFactorTag).item(0).getTextContent());
    double heightFactor = Double.parseDouble(
        element.getElementsByTagName(heightFactorTag).item(0).getTextContent());

    Button button = new Button(displayText);
    button.setPrefSize(screenWidth * widthFactor, screenHeight * heightFactor);
    

  }

}
