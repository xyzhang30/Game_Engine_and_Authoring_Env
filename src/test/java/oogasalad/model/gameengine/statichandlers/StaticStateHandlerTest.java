package oogasalad.model.gameengine.statichandlers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.List;
import oogasalad.model.gameengine.condition.Condition;
import org.junit.jupiter.api.Test;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.RulesRecord;

public class StaticStateHandlerTest {


  @Test
  public void testBuildLinkedList() {
    List<String> classNames = List.of("GameOverStaticStateHandler", "RoundOverStaticStateHandler", "TurnOverStaticStateHandler");
    StaticStateHandler result = StaticStateHandlerLinkedListFactory.buildLinkedList(classNames);
    assertNotNull(result);
    assertTrue(result instanceof GameOverStaticStateHandler);
    assertTrue(result.getNext() instanceof RoundOverStaticStateHandler);
    assertTrue(result.getNext().getNext() instanceof TurnOverStaticStateHandler);
    assertNull(result.getPrev());
    assertNull(result.getNext().getNext().getNext());
  }

  @Test
  public void testRoundOverStaticStateHandler() {
    GameEngine gameEngine = mock(GameEngine.class);
    RulesRecord rulesRecord = mock(RulesRecord.class);
    Condition dummyCondition = mock(Condition.class);
    when(rulesRecord.roundCondition()).thenReturn(dummyCondition);
    when(dummyCondition.evaluate(gameEngine)).thenReturn(true);
    RoundOverStaticStateHandler handler = new RoundOverStaticStateHandler();
    handler.setPrev(new GameOverStaticStateHandler());
    when(rulesRecord.winCondition()).thenReturn(dummyCondition);
    assertTrue(handler.canHandle(gameEngine, rulesRecord));
    handler.handle(gameEngine, rulesRecord);
    verify(rulesRecord, atLeastOnce()).advanceRound();
    assertNull(handler.getNext());
  }

  @Test
  public void testGameOverStaticStateHandler() {
    GameEngine gameEngine = mock(GameEngine.class);
    RulesRecord rulesRecord = mock(RulesRecord.class);
    Condition dummyCondition = mock(Condition.class);
    when(rulesRecord.winCondition()).thenReturn(dummyCondition);
    when(dummyCondition.evaluate(gameEngine)).thenReturn(true);
    GameOverStaticStateHandler handler = new GameOverStaticStateHandler();
    assertTrue(handler.canHandle(gameEngine, rulesRecord));
    handler.handle(gameEngine, rulesRecord);
    verify(gameEngine, times(1)).endGame();
    assertNull(handler.getNext());
  }

  @Test
  public void testTurnOverStaticStateHandler() {
    GameEngine gameEngine = mock(GameEngine.class);
    RulesRecord rulesRecord = mock(RulesRecord.class);
    TurnOverStaticStateHandler handler = new TurnOverStaticStateHandler();
    handler.setPrev(new RoundOverStaticStateHandler());
    Condition dummyCondition = mock(Condition.class);
    assertTrue(handler.canHandle(gameEngine, rulesRecord));
    when(rulesRecord.winCondition()).thenReturn(dummyCondition);
    when(rulesRecord.roundCondition()).thenReturn(dummyCondition);
    assertTrue(handler.canHandle(gameEngine, rulesRecord));
    handler.handle(gameEngine, rulesRecord);
    verify(rulesRecord, atLeastOnce()).advanceTurn();
    assertNull(handler.getNext());

  }

}


