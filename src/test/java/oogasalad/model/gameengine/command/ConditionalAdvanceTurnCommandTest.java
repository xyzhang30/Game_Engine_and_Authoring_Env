package oogasalad.model.gameengine.command;

import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.DefaultStrikeable;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.Strikeable;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.player.PlayerContainer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.*;

import static org.mockito.Mockito.*;

public class ConditionalAdvanceTurnCommandTest {

  @Test
  public void conditionalCommandTest() {
    GameEngine engine = Mockito.mock(GameEngine.class);
    PlayerContainer playerContainer = Mockito.mock(PlayerContainer.class);
    Player player = Mockito.mock(Player.class);
    GameObject gameObject = Mockito.mock(GameObject.class);
    Strikeable strikeable = Mockito.mock(DefaultStrikeable.class);
    when(engine.getPlayerContainer()).thenReturn(playerContainer);
    when(playerContainer.getPlayers()).thenReturn(Collections.singletonList(player));
    when(player.getLastPlayerRecord()).thenReturn(new PlayerRecord(1,1,1));
    when(player.getPlayerRecord()).thenReturn(new PlayerRecord(1,1,1));
    when(player.getId()).thenReturn(1);
    when(playerContainer.getActive()).thenReturn(player);
    when(player.getStrikeable()).thenReturn(strikeable);
    when(player.getStrikeable().asGameObject()).thenReturn(gameObject);
    when(gameObject.getVisible()).thenReturn(false);

    ConditionalAdvanceTurnCommand command = new ConditionalAdvanceTurnCommand(Collections.emptyList(), Collections.emptyMap());

    command.execute(engine);

    verify(engine, times(1)).advanceTurn();
  }
}
