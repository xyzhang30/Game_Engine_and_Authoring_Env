package oogasalad.view.gameplay;

import oogasalad.view.scene_management.element_parsers.GameTitleParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.io.File;
import javafx.collections.ObservableList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTitleParserTest {

  private GameTitleParser parser;

  @BeforeEach
  public void setUp() {
    parser = new GameTitleParser();
  }

  @Test
  public void testGetGameTitles() {
    // Set up files and directory mock
    File directory = mock(File.class);
    File[] files = {
        mock(File.class),
        mock(File.class),
        mock(File.class),
        mock(File.class),
        mock(File.class)
    };

    // Configure the behavior of files
    when(files[0].isDirectory()).thenReturn(false);
    when(files[1].isDirectory()).thenReturn(false);
    when(files[2].isDirectory()).thenReturn(false);
    when(files[3].isDirectory()).thenReturn(true);
    when(files[4].isDirectory()).thenReturn(false);

    when(files[0].getName()).thenReturn("chess.json");
    when(files[1].getName()).thenReturn("checkers.json");
    when(files[2].getName()).thenReturn("testGame.json");
    when(files[3].getName()).thenReturn("folder");
    when(files[4].getName()).thenReturn("tic_tac_toe.json");

    try (MockedStatic<File> mockedFile = Mockito.mockStatic(File.class)) {

      // Execute the method under test
      ObservableList<String> titles = parser.getNewGameTitles();

      // Assertions to check the outcomes
//      assertEquals(3, titles.size(), "Expected 3 valid game titles.");
//      assertTrue(titles.contains("chess"), "Expected 'chess' in the list.");
//      assertTrue(titles.contains("checkers"), "Expected 'checkers' in the list.");
//      assertTrue(titles.contains("tic_tac_toe"), "Expected 'tic_tac_toe' in the list.");
//      assertFalse(titles.contains("testGame"), "Should not include 'testGame'.");
//      assertFalse(titles.contains("folder"), "Should not include 'folder'.");
    }
  }
}
