//package oogasalad.model;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import oogasalad.model.api.data.CollisionRule;
//import oogasalad.model.api.data.Dimension;
//import oogasalad.model.api.data.GlobalVariables;
//import oogasalad.model.api.data.ParserPlayer;
//import oogasalad.model.api.data.PlayerVariables;
//import oogasalad.model.api.data.Position;
//import oogasalad.model.api.data.Rules;
//import oogasalad.model.api.data.Variables;
//import oogasalad.model.api.exception.InvalidJSONDataException;
//import oogasalad.view.controller.BuilderDirector;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import oogasalad.model.api.data.GameObjectProperties;
//
//public class GameBuilderTest {
//
//  BuilderDirector testBuilderDirector;
//  String expectedFilePath = "data/test_games/testAuthoringSinglePlayerMiniGolf.json";
//  String testFileName = "generatedTestAuthoringMiniGolf";
//
//  @BeforeEach
//  public void setup() {
//    this.testBuilderDirector = new BuilderDirector();
//
//    GameObjectProperties co1 = new GameObjectProperties(1, List.of("visible", "surface"), 100.0, new Position(0, 0), "rectangle", new Dimension(500, 500), List.of(100, 200, 100), 3.03873, 2.03873, 0,"sample.img",0);
//    GameObjectProperties co2 = new GameObjectProperties(2, List.of("visible", "collidable"), 1.0, new Position(250, 450), "circle", new Dimension(2, 2), List.of(255, 255, 255), 0.0, 0.0,0,"sample.img",0);
//    GameObjectProperties co3 = new GameObjectProperties(3, List.of("visible", "surface"), 0.0, new Position(250, 50), "circle", new Dimension(5, 5), List.of(0, 0, 0), 0.0, 0.0, 0,"sample.img",0);
//
//    this.testBuilderDirector.constructCollidableObjects(List.of(co1, co2, co3));
//
//    ParserPlayer p1 = new ParserPlayer(1, List.of(2));
//
//    this.testBuilderDirector.constructPlayers(List.of(p1));
//
//    PlayerVariables pvar = new PlayerVariables(0, 0);
//    GlobalVariables gvar = new GlobalVariables(1, 1);
//    Variables var1 = new Variables(gvar, pvar);
//
//    this.testBuilderDirector.constructVaraibles(List.of(var1));
//
//    Rules rules = getRules();
//
//    this.testBuilderDirector.constructRules(List.of(rules));
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
//    String strikePolicy = "DoNothingStrikePolicy";
//
//    return new Rules(List.of(collisionRule), turnPolicy, roundPolicy, winConditions,
//        Map.of("AdvanceTurnCommand", List.of(), "AdjustPointsCommand", List.of(1.0, 1.0)),
//        Map.of("AdvanceRoundCommand", List.of()), strikePolicy,
//        "HighestScoreComparator", "VelocityStaticChecker", List.of());
//  }
//
//  @Test
//  public void testInvalidJSONData() {
//    InvalidJSONDataException exception = assertThrows(InvalidJSONDataException.class, () -> {
//      BuilderDirector invalidGameBuilder = new BuilderDirector();
//      invalidGameBuilder.writeGame("testAuthoringMiniGolf");
//    });
//
//    String expectedMessage = "Error writing JSON game configuration file:";
//    String actualMessage = exception.getMessage();
//
//    assertTrue(actualMessage.contains(expectedMessage));
//  }
//
//  @Test
//  public void testWriteJSON() throws IOException {
//    this.testBuilderDirector.writeGame(testFileName);
//    ObjectMapper mapper = new ObjectMapper();
//    File expected = new File(expectedFilePath);
//    File tested = new File("data/playable_games/"+testFileName+".json");
//
//    assertThat(mapper.readTree(expected)).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(mapper.readTree(tested));
//  }
//}
