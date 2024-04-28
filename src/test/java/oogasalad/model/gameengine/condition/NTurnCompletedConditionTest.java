package oogasalad.model.gameengine.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.player.PlayerContainer;



import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class NTurnCompletedConditionTest {

  public static class TestPlayer extends Player {

    public TestPlayer(int id) {
      super(id, 0);
    }

    @Override
    public int getTurnsCompleted() {
      return getTurnsCompleted();
    }
  }


  @Test
  public void testEvaluate_TurnsNotCompleted() {
    TestPlayer player1 = mock(TestPlayer.class);
    TestPlayer player2 = mock(TestPlayer.class);

    when(player1.getTurnsCompleted()).thenReturn(2); // Player 1 has completed 2 turns
    when(player2.getTurnsCompleted()).thenReturn(1); // Player 2 has completed 1 turn

    Map<Integer, Player> playersMap = new HashMap<>();
    playersMap.put(1, player1);
    playersMap.put(2, player2);
    PlayerContainer playerContainer = new PlayerContainer(List.of(player1, player2));

    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.getPlayerContainer()).thenReturn(playerContainer);
    TurnsCompletedCondition condition = new TurnsCompletedCondition(List.of(3), Map.of());
    assertFalse(condition.evaluate(gameEngine));
  }


  @Test
  public void testEvaluate_TurnsCompleted() {
    // Create mock players
    TestPlayer player1 = mock(TestPlayer.class);
    TestPlayer player2 = mock(TestPlayer.class);

    when(player1.getTurnsCompleted()).thenReturn(3); // Player 1 has completed 3 turns
    when(player2.getTurnsCompleted()).thenReturn(3); // Player 2 has completed 3 turns

    Map<Integer, Player> playersMap = new HashMap<>();
    playersMap.put(1, player1);
    playersMap.put(2, player2);
    PlayerContainer playerContainer = new PlayerContainer(List.of(player1, player2));

    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.getPlayerContainer()).thenReturn(playerContainer);

    TurnsCompletedCondition condition = new TurnsCompletedCondition(List.of(3), Map.of());

    assertTrue(condition.evaluate(gameEngine));
  }
}