package oogasalad.model.gameengine.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import java.util.Map;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.player.Player;
import org.junit.jupiter.api.Test;
import oogasalad.model.gameengine.condition.ScoreThresholdCondition;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.player.PlayerContainer;

public class ScoreThresholdConditionTest {

  @Test
  public void testEvaluate_AllScoresBelowThreshold() {
    PlayerContainer playerContainer = mock(PlayerContainer.class);
    PlayerRecord playerRecords =
        new PlayerRecord(1, 39, 3);
    Player p = mock(Player.class);
    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.getPlayerContainer()).thenReturn(playerContainer);
    when(gameEngine.getPlayerContainer().getPlayers()).thenReturn(List.of(p));
    when(p.getPlayerRecord()).thenReturn(playerRecords);
    ScoreThresholdCondition condition = new ScoreThresholdCondition(List.of(40), Map.of());
    assertFalse(condition.evaluate(gameEngine));

  }

  @Test
  public void testEvaluate_ScoreExceedsThreshold() {
    PlayerContainer playerContainer = mock(PlayerContainer.class);
    PlayerRecord playerRecords =
        new PlayerRecord(1, 50.0, 3);
    Player p = mock(Player.class);
    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.getPlayerContainer()).thenReturn(playerContainer);
    when(gameEngine.getPlayerContainer().getPlayers()).thenReturn(List.of(p));
    when(p.getPlayerRecord()).thenReturn(playerRecords);
    ScoreThresholdCondition condition = new ScoreThresholdCondition(List.of(40), Map.of());
    assertTrue(condition.evaluate(gameEngine));
  }
}
