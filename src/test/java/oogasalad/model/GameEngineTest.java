package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameparser.GameLoaderModel;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import org.junit.jupiter.api.Test;

public class GameEngineTest {

  private GameEngine gameEngine;
  private GameLoaderModel loaderMock;

  @BeforeEach
  public void setUp() {
    // Create a mock GameLoaderModel
    loaderMock = mock(GameLoaderModel.class);

    // Mock PlayerContainer
    PlayerContainer playerContainerMock = mock(PlayerContainer.class);
    // Set up behavior for playerContainerMock if needed

    // Mock RulesRecord
    RulesRecord rulesRecordMock = mock(RulesRecord.class);
    // Set up behavior for rulesRecordMock if needed

    // Mock CollidableContainer
    CollidableContainer collidableContainerMock = mock(CollidableContainer.class);
    // Set up behavior for collidableContainerMock if needed

    // Set up predefined behavior for the loaderMock to return the mock objects
    when(loaderMock.getPlayerContainer()).thenReturn(playerContainerMock);
    when(loaderMock.getRulesRecord()).thenReturn(rulesRecordMock);
    when(loaderMock.getCollidableContainer()).thenReturn(collidableContainerMock);

    // Initialize GameEngine with the mock
    gameEngine = new GameEngine(123, loaderMock);
  }

  // Write your tests here
}
