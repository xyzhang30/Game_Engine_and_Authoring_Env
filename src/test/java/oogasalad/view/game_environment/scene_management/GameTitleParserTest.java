//package oogasalad.view.game_environment.scene_management;
//
//import java.util.List;
//import javafx.collections.ObservableList;
//import oogasalad.view.scene_management.element_parsers.GameTitleParser;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class GameTitleParserTest {
//  private final Path testDir = Path.of("data/playable_games");
//  private GameTitleParser parser;
//
//  @BeforeEach
//  public void setUp() throws IOException {
//    Files.createDirectories(testDir);
//
//    // Create some test files
//    Files.createFile(testDir.resolve("game1.json"));
//    Files.createFile(testDir.resolve("game2.json"));
//
//    parser = new GameTitleParser();
//  }
//
//  @AfterEach
//  public void tearDown() throws IOException {
//    // Clean up files
//    Files.walk(testDir)
//        .map(Path::toFile)
//        .sorted((o1, o2) -> -o1.compareTo(o2))
//        .forEach(File::delete);
//  }
//
//  @Test
//  public void testGetNewGameTitles() {
//    ObservableList<String> titles = parser.getNewGameTitles();
//    assertTrue(titles.containsAll(List.of("game1", "game2")));
//    assertFalse(titles.contains("fakegame"));
//  }
//}
