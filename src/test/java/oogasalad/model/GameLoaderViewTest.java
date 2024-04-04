package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import oogasalad.model.api.ViewCollidableRecord;
import oogasalad.model.gameparser.GameLoaderView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameLoaderViewTest {

  @BeforeEach
  public void setup(){
  }

  @Test
  public void createCollidableConfigTest(){
    GameLoaderView loaderView = new GameLoaderView("singlePlayerMiniGolf");
    List<ViewCollidableRecord> collidableRecords = loaderView.getViewCollidableInfo();
    for (ViewCollidableRecord record : collidableRecords){
      if (record.collidableId() == 2 || record.collidableId() == 3){
        assertEquals("circle", record.shape());
      } else {
        assertEquals("rectangle", record.shape());
      }
    }
  }
}
