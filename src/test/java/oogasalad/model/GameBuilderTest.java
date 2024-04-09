package oogasalad.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.api.data.CollidableObject;
import oogasalad.model.api.data.CollisionRule;
import oogasalad.model.api.data.Dimension;
import oogasalad.model.api.data.GameData;
import oogasalad.model.api.data.GlobalVariables;
import oogasalad.model.api.data.ParserPlayer;
import oogasalad.model.api.data.PlayerVariables;
import oogasalad.model.api.data.Position;
import oogasalad.model.api.data.Rules;
import oogasalad.model.api.data.Variables;
import oogasalad.model.gamebuilder.BuilderDirector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameBuilderTest {

  BuilderDirector testBuilderDirector;
  GameData testGameData = new GameData();

  @BeforeEach
  public void setup() {
    this.testBuilderDirector = new BuilderDirector();

    CollidableObject co1 = new CollidableObject(1, List.of("visible, movable"),
        Double.POSITIVE_INFINITY, new Position(0, 0), "rectangle", new Dimension(500, 500),
        List.of(100, 200, 100), 0.5, "sample.img");
    CollidableObject co2 = new CollidableObject(2, List.of("visible", "movable"), 1,
        new Position(250, 450), "circle", new Dimension(2, 2), List.of(255, 255, 255), 0, "sample.img");
    CollidableObject co3 = new CollidableObject(3, List.of("visible, surface"), 0,
        new Position(250, 50), "circle", new Dimension(5, 5), List.of(0, 0, 0), 0,
        "sample.img");

    this.testBuilderDirector.constructCollidableObjects(testGameData, List.of(co1, co2, co3));

    ParserPlayer p1 = new ParserPlayer(1, 2);

    this.testBuilderDirector.constructPlayers(testGameData, List.of(p1));

    PlayerVariables pvar = new PlayerVariables(0, 0);
    GlobalVariables gvar = new GlobalVariables(1, 1);
    Variables var1 = new Variables(gvar, pvar);

    this.testBuilderDirector.constructVaraibles(testGameData, List.of(var1));

    Rules rules = getRules();

    this.testBuilderDirector.constructRules(testGameData, List.of(rules));

  }

  private static Rules getRules() {
    Map<String, List<Double>> commands = Map.of("AdjustPointsCommand", List.of(1.0, 1.0), "AdvanceTurnCommand", List.of());
    CollisionRule collisionRule = new CollisionRule(2, 3, List.of(commands));
    String turnPolicy = "StandardTurnPolicy";
    Map<String, List<Double>> winConditions = Map.of("NRoundsCompletedCommand", List.of(2.0));
    Map<String, List<Double>> advance = Map.of("AdvanceTurnCommand", List.of(), "AdjustPointsCommand", List.of(1.0, 1.0));

    Rules rules = new Rules(List.of(collisionRule), null, turnPolicy, winConditions, List.of(advance));
    return rules;
  }

//  @Test
//  public void testInvalidJSONFile() {
//
//    InvalidFileException exception = assertThrows(InvalidFileException.class, () -> {
//      String invalidFileName = "invalidConfigurationFormat";
//      testGameLoaderModel = new GameLoaderModel(invalidFileName);
//    });
//
//    String expectedMessage = "Error parsing JSON game configuration file:";
//    String actualMessage = exception.getMessage();
//
//    assertTrue(actualMessage.contains(expectedMessage));
//
//  }

  @Test
  public void testWriteJSON() throws IOException {
    String expectedFile = "testAuthoringSinglePlayerMiniGolf.json";
    String testFile = "test_authoring_mini_golf.json";
//    String fileType = ".json";
    String filePath = "data/";
    this.testBuilderDirector.writeGame(testGameData, filePath, testFile);
    ObjectMapper mapper = new ObjectMapper();
    File expected = new File(filePath+expectedFile);
    File tested = new File(filePath+testFile);

    assertEquals(mapper.readTree(expected), mapper.readTree(tested));
  }
}  

  
