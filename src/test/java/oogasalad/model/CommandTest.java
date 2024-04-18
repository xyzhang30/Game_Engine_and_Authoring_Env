package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameparser.GameLoaderModel;

public class CommandTest {

  private GameEngine gameEngine;
  private GameLoaderModel loader;

  @BeforeEach
  public void setUp() {
    loader = mock(GameLoaderModel.class);
    gameEngine = new GameEngine("ThreePlayerMiniGolf");
  }

  @Test
  public void testAdvanceRound() {
    int initialRound = gameEngine.restoreLastStaticGameRecord().round();
    gameEngine.advanceRound();
    assertEquals(initialRound + 1, gameEngine.restoreLastStaticGameRecord().round());
  }

}
