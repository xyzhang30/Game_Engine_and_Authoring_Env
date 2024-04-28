package oogasalad.model.gameengine.condition;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import java.util.Map;
import org.junit.jupiter.api.Test;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.api.GameRecord;

public class NRoundCompletedConditionTest {

  @Test
  public void testEvaluateRoundsNotCompleted() {
    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.restoreLastStaticGameRecord()).thenReturn(new GameRecord(null, null, 2, 1, false, true));
    RoundsCompletedCondition condition = new RoundsCompletedCondition(List.of(3), Map.of());
    assertFalse(condition.evaluate(gameEngine));
  }

  @Test
  public void testEvaluateRoundsCompletedExact() {
    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.restoreLastStaticGameRecord()).thenReturn(new GameRecord(null, null, 3, 1, false, true));
    RoundsCompletedCondition condition = new RoundsCompletedCondition(List.of(3), Map.of());
    assertFalse(condition.evaluate(gameEngine));
  }

  @Test
  public void testEvaluateTooManyRoundsCompleted() {
    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.restoreLastStaticGameRecord()).thenReturn(new GameRecord(null, null, 300, 1,
        false, true));
    RoundsCompletedCondition condition = new RoundsCompletedCondition(List.of(3), Map.of());
    assertTrue(condition.evaluate(gameEngine));
  }

}
