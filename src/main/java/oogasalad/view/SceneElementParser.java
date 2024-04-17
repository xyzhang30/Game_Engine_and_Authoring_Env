package oogasalad.view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import oogasalad.view.enums.SceneElementType;
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
  private final String playableGameDir = "data/playable_games";
  private final String testFileIdentifier = "test";

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
      case LISTVIEW -> {
        createListView(element, sceneElements);
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

  private void createListView(Element element, Map<Node, String> sceneElements) {
    double widthFactor = Double.parseDouble(
        element.getElementsByTagName(widthFactorTag).item(0).getTextContent());
    double heightFactor = Double.parseDouble(
        element.getElementsByTagName(heightFactorTag).item(0).getTextContent());
    double xLayoutFactor = Double.parseDouble(
        element.getElementsByTagName(xLayoutFactorTag).item(0).getTextContent());
    double yLayoutFactor = Double.parseDouble(
        element.getElementsByTagName(yLayoutFactorTag).item(0).getTextContent());
    String style = element.getElementsByTagName(styleTag).item(0).getTextContent();

    ListView<String> listView = new ListView<>(getListOptions());
    listView.setPrefSize(screenWidth * widthFactor, screenHeight * heightFactor);
    listView.setLayoutX(screenWidth * xLayoutFactor - listView.getPrefWidth() / 2);
    listView.setLayoutY(screenHeight * yLayoutFactor);

    sceneElements.put(listView, style);
  }

  private ObservableList<String> getListOptions() {
    Set<String> games = Stream.of(new File(playableGameDir).listFiles())
        .filter(file -> !file.isDirectory())
        .map(File::getName)
        .collect(Collectors.toSet());

    List<String> gameTitles = new ArrayList<>();
    for (String filePath : games) {
      if (!filePath.toLowerCase().contains(testFileIdentifier)) {
        gameTitles.add(filePath.substring(0, filePath.indexOf(".")));
      }
    }

    return FXCollections.observableList(gameTitles);
  }

}
