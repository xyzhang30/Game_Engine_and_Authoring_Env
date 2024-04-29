package oogasalad.view.game_environment.scene_management;

import oogasalad.view.scene_management.element_parsers.SceneElementParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the SceneElementParser class.
 *
 * @author Doga Ozmen
 */
public class SceneElementParserTest extends ApplicationTest {

  private Path tempXmlFile;
  private SceneElementParser parser;

  @BeforeEach
  public void setUp() throws Exception {
    tempXmlFile = Files.createTempFile("test", ".xml");
    parser = new SceneElementParser();

    String xmlContent = "<root><node><name>TestElement</name><type>Button</type></node></root>";
    Files.writeString(tempXmlFile, xmlContent);
  }

  @AfterEach
  public void tearDown() throws IOException {
    Files.deleteIfExists(tempXmlFile);
  }

  /**
   * Tests getting element parameters from a file.
   */
  @Test
  public void testGetElementParametersFromFile() throws Exception {
    List<Map<String, String>> parameters = parser.getElementParametersFromFile(tempXmlFile.toString());

    assertEquals(1, parameters.size(), "Should have one element map");
    Map<String, String> paramMap = parameters.get(0);
    assertEquals("TestElement", paramMap.get("name"), "Name should match");
    assertEquals("Button", paramMap.get("type"), "Type should match");
  }
}
