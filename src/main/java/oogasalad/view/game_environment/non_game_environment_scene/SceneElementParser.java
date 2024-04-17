package oogasalad.view.game_environment.non_game_environment_scene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class SceneElementParser {

  private final String nodeTag = "node";
  private final String playableGameDir = "data/playable_games";
  private final String testFileIdentifier = "test";

  public SceneElementParser() {
  }

  public List<Map<String, String>> getElementParametersFromFile(String filePath)
      throws ParserConfigurationException, SAXException, IOException {
    List<Map<String, String>> elementParameters = new ArrayList<>();
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    Document doc = documentBuilder.parse(filePath);

    Element root = doc.getDocumentElement();
    root.normalize();

    NodeList nodeList = root.getElementsByTagName(nodeTag);

    for (int i = 0; i < nodeList.getLength(); i++) {
      Element element = (Element) nodeList.item(i);
      elementParameters.add(createParameterList(element));
    }

    return elementParameters;
  }

  public ObservableList<String> getGameTitles() {
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

  private Map<String, String> createParameterList(Element element) {
    Map<String, String> parameters = new HashMap<>();
    NodeList children = element.getChildNodes();

    for (int i = 0; i < children.getLength(); i++) {
      Node child = children.item(i);
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        String parameterName = ((Element) child).getTagName();
        String parameterValue = child.getTextContent();
        parameters.put(parameterName, parameterValue);
      }
    }

    return parameters;
  }

}
