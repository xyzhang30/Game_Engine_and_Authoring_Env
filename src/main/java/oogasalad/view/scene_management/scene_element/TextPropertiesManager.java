package oogasalad.view.scene_management.scene_element;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import oogasalad.view.api.enums.TextPropertyType;

/**
 * Retrieves Strings from given tags, by retrieving mapping in properties file
 *
 * @author Jordan Haytaian
 */
public class TextPropertiesManager {

  private final String englishPropertiesPath = "src/main/resources/view/properties/EnglishText.properties";
  private final String xmlTagPropertiesPath = "src/main/resources/view/properties/XMLTags.properties";
  Properties englishProperties;
  Properties xmlTagProperties;
  Map<TextPropertyType, Properties> propertyMap;

  /**
   * Constructor creates properties from property file paths and maps enums to corresponding
   * properties
   */
  public TextPropertiesManager() {
    englishProperties = loadProperties(englishPropertiesPath);
    xmlTagProperties = loadProperties(xmlTagPropertiesPath);
    createPropertyMap();
  }

  /**
   * Retrieves the String text corresponding to the given tag in the given properties file
   *
   * @param textPropertyType the type of property file to search
   * @param tag              the tag to search for
   * @return the String corresponding to the given tag
   */
  public String getText(TextPropertyType textPropertyType, String tag) {
    return propertyMap.get(textPropertyType).getProperty(tag);
  }

  private Properties loadProperties(String filePath) {
    Properties properties = new Properties();
    try {
      FileInputStream inputStream = new FileInputStream(filePath);
      properties.load(inputStream);
    } catch (IOException e) {
      //TODO: Exception Handling
      System.out.println(e.getMessage());
    }
    return properties;
  }

  private void createPropertyMap() {
    propertyMap = new HashMap<>();
    propertyMap.put(TextPropertyType.ENGLISH, englishProperties);
    propertyMap.put(TextPropertyType.XMLTAG, xmlTagProperties);
  }
}
