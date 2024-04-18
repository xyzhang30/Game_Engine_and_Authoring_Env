package oogasalad.model.gameengine.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.condition.NRoundsCompletedCondition;
import oogasalad.model.api.GameRecord;

public class NRoundCompletedConditionTest {

  @Test
  public void testEvaluateRoundsNotCompleted() {
    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.restoreLastStaticGameRecord()).thenReturn(new GameRecord(null, null, 2, 1, false, true));
    NRoundsCompletedCondition condition = new NRoundsCompletedCondition(Arrays.asList(3.0));
    assertFalse(condition.evaluate(gameEngine));
  }

  @Test
  public void testEvaluateRoundsCompletedExact() {
    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.restoreLastStaticGameRecord()).thenReturn(new GameRecord(null, null, 3, 1, false, true));
    NRoundsCompletedCondition condition = new NRoundsCompletedCondition(Arrays.asList(3.0));
    assertFalse(condition.evaluate(gameEngine));
  }

  @Test
  public void testEvaluateTooManyRoundsCompleted() {
    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.restoreLastStaticGameRecord()).thenReturn(new GameRecord(null, null, 300, 1,
        false, true));
    NRoundsCompletedCondition condition = new NRoundsCompletedCondition(Arrays.asList(3.0));
    assertTrue(condition.evaluate(gameEngine));
  }

}
