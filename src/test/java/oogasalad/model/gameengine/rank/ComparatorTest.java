package oogasalad.model.gameengine.rank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.GameObjectContainer;
import oogasalad.model.gameengine.gameobject.Strikeable;
import oogasalad.model.gameengine.gameobject.scoreable.Scoreable;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.player.PlayerContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComparatorTest {
  private GameEngine gameEngine;
  private PlayerContainer container;


  @BeforeEach
  public void setUp() {
    gameEngine = mock(GameEngine.class);
    Scoreable s1 = mock(Scoreable.class);
    Scoreable s2 = mock(Scoreable.class);
    Scoreable s3 = mock(Scoreable.class);
    Strikeable ss1 = mock(Strikeable.class);
    Strikeable ss2 = mock(Strikeable.class);
    Strikeable ss3 = mock(Strikeable.class);
    GameObject go1 = mock(GameObject.class);
    GameObject go2 = mock(GameObject.class);
    GameObject go3 = mock(GameObject.class);

    when(s1.asGameObject()).thenReturn(go1);
    when(s2.asGameObject()).thenReturn(go2);
    when(s3.asGameObject()).thenReturn(go3);

    when(ss1.asGameObject()).thenReturn(go1);
    when(ss2.asGameObject()).thenReturn(go2);
    when(ss3.asGameObject()).thenReturn(go3);
    when(s1.asGameObject().getId()).thenReturn(1);
    when(s2.asGameObject().getId()).thenReturn(2);
    when(s3.asGameObject().getId()).thenReturn(3);
    when(s1.getTemporaryScore()).thenReturn(1.0);
    when(s2.getTemporaryScore()).thenReturn(3.0);
    when(s3.getTemporaryScore()).thenReturn(2.0);

    Player p1 = new Player(1);
    p1.addScoreables(List.of(s1));
    p1.addStrikeables(List.of(ss1));
    Player p2 = new Player(2);
    p2.addScoreables(List.of(s2));
    p2.addStrikeables(List.of(ss2));
    Player p3 = new Player(3);
    p3.addScoreables(List.of(s3));
    p3.addStrikeables(List.of(ss3));


    container = new PlayerContainer(List.of(p1,p2,p3));
    when(gameEngine.getPlayerContainer()).thenReturn(container);
    container = gameEngine.getPlayerContainer();
  }

  @Test
  public void testLowestScoreComp() {
    PlayerRecordComparator comp = new LowestScoreComparator();

    List<PlayerRecord> records = container.getPlayers().stream()
        .map(Player::getPlayerRecord)
        .sorted(comp)
        .toList();

    assertEquals(1, records.get(0).playerId());
    assertEquals(3, records.get(1).playerId());
    assertEquals(2, records.get(2).playerId());
  }


  @Test
  public void testHighestScoreComp() {
    PlayerRecordComparator comp = new HighestScoreComparator();
    List<PlayerRecord> records = container.getPlayers().stream()
        .map(Player::getPlayerRecord)
        .sorted(comp)
        .toList();
    assertEquals(2, records.get(0).playerId());
    assertEquals(3, records.get(1).playerId());
    assertEquals(1, records.get(2).playerId());
  }

  @Test
  public void testIdComp() {
    PlayerRecordComparator comp = new IDComparator();
    List<PlayerRecord> records = container.getPlayers().stream()
        .map(Player::getPlayerRecord)
        .sorted(comp)
        .toList();

    assertEquals(1, records.get(0).playerId());
    assertEquals(2, records.get(1).playerId());
    assertEquals(3, records.get(2).playerId());
  }

}
