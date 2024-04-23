package oogasalad.model.gameengine.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import oogasalad.model.gameengine.GameEngine;
import org.junit.jupiter.api.Test;
import oogasalad.model.gameengine.condition.AllPlayersCompletedRoundCondition;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.player.PlayerContainer;

public class AllPlayersCompletedRoundConditionTest {

  @Test
  public void testEvaluate_AllPlayersCompletedRound() {
    Player player1 = new Player(1);
    Player player2 = new Player(2);
    PlayerContainer playerContainer = new PlayerContainer(List.of(player1,player2));
    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.getPlayerContainer()).thenReturn(playerContainer);
    AllPlayersCompletedRoundCondition condition = new AllPlayersCompletedRoundCondition(List.of()
        , Map.of());
    player1.completeRound();
    player2.completeRound();
    assertTrue(condition.evaluate(gameEngine));
  }

  @Test
  public void testEvaluate_NotAllPlayersCompletedRound() {
    Player player1 = new Player(1);
    Player player2 = new Player(2);
    PlayerContainer playerContainer = new PlayerContainer(List.of(player1,player2));
    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.getPlayerContainer()).thenReturn(playerContainer);
    AllPlayersCompletedRoundCondition condition = new AllPlayersCompletedRoundCondition(List.of()
        , Map.of());
    assertFalse(condition.evaluate(gameEngine));

  }
}
