package oogasalad.model.gameengine.command;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import java.util.Map;
import org.junit.jupiter.api.Test;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.command.PlayerCompletedRoundCommand;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.player.PlayerContainer;

public class PlayerCompletedTest {

  @Test
  public void testPlayerCompletedRound() {
    PlayerContainer playerContainer = mock(PlayerContainer.class);
    Player activePlayer = mock(Player.class);
    when(playerContainer.getActive()).thenReturn(activePlayer);
    when(playerContainer.getPlayer(1)).thenReturn(activePlayer);
    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.getPlayerContainer()).thenReturn(playerContainer);
    PlayerCompletedRoundCommand command = new PlayerCompletedRoundCommand(Collections.emptyList()
        , Map.of());
    when(activePlayer.isRoundCompleted()).thenReturn(true);
    command.execute(gameEngine);
    verify(activePlayer).completeRound();
  }

  @Test
  public void testPlayerCompletedTurn() {
    PlayerContainer playerContainer = mock(PlayerContainer.class);
    Player activePlayer = mock(Player.class);
    when(playerContainer.getActive()).thenReturn(activePlayer);
    when(playerContainer.getPlayer(1)).thenReturn(activePlayer);
    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.getPlayerContainer()).thenReturn(playerContainer);
    PlayerCompletedTurnCommand command = new PlayerCompletedTurnCommand(Collections.emptyList(),
        Map.of());
    command.execute(gameEngine);
    verify(activePlayer).completeTurn();
  }
}
