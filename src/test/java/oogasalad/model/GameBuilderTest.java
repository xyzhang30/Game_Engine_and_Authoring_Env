//package oogasalad.model;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import oogasalad.model.api.data.CollidableObject;
//import oogasalad.model.api.data.CollisionRule;
//import oogasalad.model.api.data.Dimension;
//import oogasalad.model.api.data.GameData;
//import oogasalad.model.api.data.GlobalVariables;
//import oogasalad.model.api.data.ParserPlayer;
//import oogasalad.model.api.data.PlayerVariables;
//import oogasalad.model.api.data.Position;
//import oogasalad.model.api.data.Rules;
//import oogasalad.model.api.data.Variables;
//import oogasalad.model.api.exception.InvalidJSONDataException;
//import oogasalad.view.Controlling.BuilderDirector;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class GameBuilderTest {
//
//  BuilderDirector testBuilderDirector;
//  GameData testGameData = new GameData();
//  String expectedFilePath = "data/testAuthoringSinglePlayerMiniGolf.json";
//  String testFileName = "generatedTestAuthoringMiniGolf";
//
//  @BeforeEach
//  public void setup() {
//    this.testBuilderDirector = new BuilderDirector();
//
//    CollidableObject co1 = new CollidableObject(1, List.of("visible", "surface"),
//        100.0, new Position(0, 0), "rectangle", new Dimension(500, 500),
//        List.of(100, 200, 100), 0.5, 0.5,"sample.img");
//    CollidableObject co2 = new CollidableObject(2, List.of("visible", "movable"), 1.0,
//        new Position(250, 450), "circle", new Dimension(2, 2), List.of(255, 255, 255), 0,0, "sample.img");
//    CollidableObject co3 = new CollidableObject(3, List.of("visible", "surface"), 0.0,
//        new Position(250, 50), "circle", new Dimension(5, 5), List.of(0, 0, 0), 0,0,
//        "sample.img");
//
//    this.testBuilderDirector.constructCollidableObjects(testGameData, List.of(co1, co2, co3));
//
//    ParserPlayer p1 = new ParserPlayer(1, 2);
//
//    this.testBuilderDirector.constructPlayers(testGameData, List.of(p1));
//
//    PlayerVariables pvar = new PlayerVariables(0, 0);
//    GlobalVariables gvar = new GlobalVariables(1, 1);
//    Variables var1 = new Variables(gvar, pvar);
//
//    this.testBuilderDirector.constructVaraibles(testGameData, List.of(var1));
//
//    Rules rules = getRules();
//
//    this.testBuilderDirector.constructRules(testGameData, List.of(rules));
//
//  }
//
//  private static Rules getRules() {
//    Map<String, List<Double>> commands1 = Map.of("AdjustPointsCommand", List.of(1.0, 1.0));
//    Map<String, List<Double>> commands2 = Map.of("AdvanceTurnCommand", List.of());
//
//    CollisionRule collisionRule = new CollisionRule(2, 3, List.of(commands1, commands2));
//    String turnPolicy = "StandardTurnPolicy";
//    Map<String, List<Double>> roundPolicy = Map.of("AdvanceRoundCheck", List.of());
//    Map<String, List<Double>> winConditions = Map.of("NRoundsCompletedCommand", List.of(2.0));
//    Map<String, List<Double>> advance1 = Map.of("AdvanceTurnCommand", List.of());
//    Map<String, List<Double>> advance2 = Map.of("AdjustPointsCommand", List.of(1.0, 1.0));
//    Map<String, List<Double>> advance3 = Map.of("AdvanceRoundCommand", List.of());
//    Rules rules = new Rules(List.of(collisionRule), null, turnPolicy, roundPolicy, winConditions, List.of(advance1, advance2), List.of(advance3));
//    return rules;
//  }
//
//  @Test
//  public void testInvalidJSONData() {
//
//    InvalidJSONDataException exception = assertThrows(InvalidJSONDataException.class, () -> {
//      GameData invalidGameData = new GameData();
//      this.testBuilderDirector.writeGame(invalidGameData, "testAuthoringMiniGolf");
//    });
//
//    String expectedMessage = "Error writing JSON game configuration file:";
//    String actualMessage = exception.getMessage();
//
//    assertTrue(actualMessage.contains(expectedMessage));
//
//  }
//
//  @Test
//  public void testWriteJSON() throws IOException {
//    this.testBuilderDirector.writeGame(testGameData, testFileName);
//    ObjectMapper mapper = new ObjectMapper();
//    File expected = new File(expectedFilePath);
//    File tested = new File("data/"+testFileName+".json");
//
//    assertThat(mapper.readTree(expected)).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(mapper.readTree(tested));
//  }
//}
//
//
