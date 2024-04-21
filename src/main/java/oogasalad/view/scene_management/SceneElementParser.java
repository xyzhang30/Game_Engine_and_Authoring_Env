package oogasalad.view.scene_management;

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

/**
 * Parses XML files for parameters necessary to create scene elements and JSON files for game titles
 * to display accurate game options
 *
 * @author Jordan Haytaian
 */
public class SceneElementParser {

  private final String nodeTag = "node";
  private final String playableGameDir = "data/playable_games";
  private final String testFileIdentifier = "test";

  /**
   * Constructor
   */
  public SceneElementParser() {
  }

  /**
   * Parses XML file for scene element parameters
   *
   * @param filePath xml file path
   * @return for each element, a map from parameter name to value
   * @throws ParserConfigurationException If a DocumentBuilder cannot be created which satisfies the
   *                                      configuration requested.
   * @throws SAXException                 If any XML parsing errors occur.
   * @throws IOException                  If an I/O error occurs while reading the file.
   */
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

  /**
   * Retrieves a list of game titles from the specified directory.
   * <p>
   * This method scans the directory specified by `playableGameDir` and collects all files that are
   * not directories. It then filters out files that contain the specified `testFileIdentifier` in
   * their names (case insensitive) and adds the file names, excluding their extensions, to a list
   * of game titles. The method returns an observable list containing these game titles.
   *
   * @return An observable list of strings, where each string is the title of a game file in the
   * directory, excluding the file extension.
   */
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
