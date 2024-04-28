package oogasalad.view.scene_management.element_parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
