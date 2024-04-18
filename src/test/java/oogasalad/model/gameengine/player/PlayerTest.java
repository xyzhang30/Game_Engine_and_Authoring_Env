package oogasalad.model.gameengine.player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.GameObjectContainer;
import oogasalad.model.gameengine.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.gameobject.Strikeable;
import oogasalad.model.gameengine.gameobject.scoreable.Scoreable;

public class PlayerTest {

  private Player player;
  private Strikeable strikeable1;
  private Strikeable strikeable2;
  private Scoreable scoreable;
  private GameObject gameObject1;
  private GameObject gameObject2;
  private List<Strikeable> strikeables;
  private List<Scoreable> scoreables;

  @BeforeEach
  public void setUp() {
    player = new Player(1);
    strikeable1 = mock(Strikeable.class);
    strikeable2 = mock(Strikeable.class);
    scoreable = mock(Scoreable.class);
    gameObject1 = mock(GameObject.class); // Mock Collidable object
    gameObject2 = mock(GameObject.class); // Mock Collidable object
    strikeables = new ArrayList<>();
    strikeables.add(strikeable1);
    strikeables.add(strikeable2);
    scoreables = new ArrayList<>();
    scoreables.add(scoreable);
    when(gameObject1.getId()).thenReturn(1);
    when(gameObject2.getId()).thenReturn(2);
    when(scoreable.asGameObject()).thenReturn(gameObject1);
    when(strikeable1.asGameObject()).thenReturn(gameObject1);
    when(strikeable1.asGameObject().getId()).thenReturn(1);
    when(strikeable2.asGameObject()).thenReturn(gameObject2);
    when(strikeable2.asGameObject().getId()).thenReturn(2);
    when(scoreable.asGameObject().getId()).thenReturn(1);
    when(scoreable.getTemporaryScore()).thenReturn(10.0);
    player.addStrikeables(strikeables);
    player.addStrikeables(strikeables);
    player.addScoreables(scoreables);
    player.updateActiveStrikeable();
  }


  @Test
  public void testUpdateActiveStrikeable() {

    player.updateActiveStrikeable();
    assertEquals(strikeable2.asGameObject().getId(), player.getPlayerRecord().activeStrikeable());
  }

  @Test
  public void testIsRoundCompleted() {
    assertFalse(player.isRoundCompleted());
    player.completeRound();
    assertTrue(player.isRoundCompleted());
  }

  @Test
  public void testGetStrikeableID() {

    when(strikeable1.asGameObject().getId()).thenReturn(101);
    assertEquals(101, player.getStrikeableID());
  }

  @Test
  public void testCompleteTurn() {
    player.completeTurn();
    assertEquals(1, player.getTurnsCompleted());
  }

  @Test
  public void testStartRound() {
    player.completeTurn();
    player.completeRound();
    player.startRound();
    assertFalse(player.isRoundCompleted());
    assertEquals(0, player.getTurnsCompleted());
  }

  @Test
  public void testApplyDelayedScore() {
    player.addPlayerHistory();
    assertEquals(10.0, player.getPlayerRecord().score());
  }

  @Test
  public void testGetPlayerRecord() {
    when(strikeable1.asGameObject().getId()).thenReturn(101);
    when(scoreable.getTemporaryScore()).thenReturn(10.0);
    PlayerRecord playerRecord = player.getPlayerRecord();
    assertEquals(1, playerRecord.playerId());
    assertEquals(10.0, playerRecord.score());
    assertEquals(101, playerRecord.activeStrikeable());
  }


}
