package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import oogasalad.model.gameparser.GameLoaderView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameLoaderViewTest {

  @BeforeEach
  public void setup(){
  }

  @Test
  public void createCollidableConfigTest(){
    GameLoaderView gameLoaderView = new GameLoaderView("singlePlayerMiniGolf");
    Path path = Paths.get("src/main/resources/");
    assertTrue(Files.exists(path), "Properties file should be created upon initialisation");
  }
}
