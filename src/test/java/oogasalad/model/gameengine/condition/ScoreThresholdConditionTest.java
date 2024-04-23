package oogasalad.model.gameengine.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import java.util.Map;
import oogasalad.model.gameengine.GameEngine;
import org.junit.jupiter.api.Test;
import oogasalad.model.gameengine.condition.ScoreThresholdCondition;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.player.PlayerContainer;

public class ScoreThresholdConditionTest {

  @Test
  public void testEvaluate_AllScoresBelowThreshold() {
    PlayerContainer playerContainer = mock(PlayerContainer.class);
    List<PlayerRecord> playerRecords = Arrays.asList(
        new PlayerRecord(1, 10.0, 1),
        new PlayerRecord(2, 20.0, 2),
        new PlayerRecord(3, 30.0, 3)
    );
    when(playerContainer.getPlayerRecords()).thenReturn(playerRecords);
    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.getPlayerContainer()).thenReturn(playerContainer);
    ScoreThresholdCondition condition = new ScoreThresholdCondition(List.of(40), Map.of());
    assertFalse(condition.evaluate(gameEngine));
  }

  @Test
  public void testEvaluate_ScoreExceedsThreshold() {
    PlayerContainer playerContainer = mock(PlayerContainer.class);
    List<PlayerRecord> playerRecords = Arrays.asList(
        new PlayerRecord(1, 10.0, 1),
        new PlayerRecord(2, 20.0, 2),
        new PlayerRecord(3, 50.0, 3)
    );
    when(playerContainer.getPlayerRecords()).thenReturn(playerRecords);
    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.getPlayerContainer()).thenReturn(playerContainer);
    ScoreThresholdCondition condition = new ScoreThresholdCondition(List.of(40), Map.of());
    assertTrue(condition.evaluate(gameEngine));
  }
}
