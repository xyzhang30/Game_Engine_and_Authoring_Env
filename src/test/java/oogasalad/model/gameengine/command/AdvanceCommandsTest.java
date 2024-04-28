package oogasalad.model.gameengine.command;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Collections;

import java.util.Map;
import org.junit.jupiter.api.Test;
import oogasalad.model.gameengine.GameEngine;

public class AdvanceCommandsTest {

  @Test
  public void testAdvanceTurn() {
    GameEngine gameEngine = mock(GameEngine.class);
    AdvanceTurnCommand command = new AdvanceTurnCommand(Collections.emptyList(), Map.of());
    command.execute(gameEngine);
    verify(gameEngine).advanceTurn();
  }

  @Test
  public void testAdvanceRound() {
    GameEngine gameEngine = mock(GameEngine.class);
    AdvanceRoundCommand command = new AdvanceRoundCommand(Collections.emptyList(), Map.of());
    command.execute(gameEngine);
    verify(gameEngine).advanceRound();
  }
}
