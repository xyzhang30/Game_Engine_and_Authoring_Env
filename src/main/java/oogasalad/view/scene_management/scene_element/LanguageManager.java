package oogasalad.view.scene_management.scene_element;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import oogasalad.view.api.enums.SupportedLanguage;

/**
 * Retrieves Strings from given tags, by retrieving mapping in properties file
 *
 * @author Jordan Haytaian
 */
public class LanguageManager {

  private final Properties englishProperties;
  private final Properties spanishProperties;
  private final Properties frenchProperties;
  Map<SupportedLanguage, Properties> languageMap;
  private final String englishPropertiesPath =
      "src/main/resources/view/properties/EnglishText.properties";
  private final String spanishPropertiesPath =
      "src/main/resources/view/properties/SpanishText.properties";
  private final String frenchPropertiesPath =
      "src/main/resources/view/properties/FrenchText.properties";

  /**
   * Constructor creates properties from property file paths and maps enums to corresponding
   * properties
   */
  public LanguageManager() {
    englishProperties = loadProperties(englishPropertiesPath);
    spanishProperties = loadProperties(spanishPropertiesPath);
    frenchProperties = loadProperties(frenchPropertiesPath);
    createPropertyMap();
  }

  /**
   * Retrieves text corresponding to tag in specified language
   *
   * @param language language of translation
   * @param tag      tag corresponding to requested text
   * @return translated String
   */
  public String getText(SupportedLanguage language, String tag) {
    return languageMap.get(language).getProperty(tag);
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
    languageMap = new HashMap<>();
    languageMap.put(SupportedLanguage.ENGLISH, englishProperties);
    languageMap.put(SupportedLanguage.SPANISH, spanishProperties);
    languageMap.put(SupportedLanguage.FRENCH, frenchProperties);
  }
}
