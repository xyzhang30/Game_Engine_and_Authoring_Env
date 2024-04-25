package oogasalad.model.gameengine.command;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.player.PlayerContainer;
import org.junit.jupiter.api.Test;

public class EightBallCommandTest {

  @Test
  public void testEightBall() {
    GameObject gameObject = mock(GameObject.class);
    Map<Integer, GameObject> gameObjectMap = Collections.singletonMap(1, gameObject);
    GameEngine engine = mock(GameEngine.class);
    PlayerContainer playerContainer = mock(PlayerContainer.class);
    Player player = mock(Player.class);
    EightBallCommand command = new EightBallCommand(Arrays.asList(1), gameObjectMap);
    when(gameObject.getVisible()).thenReturn(true);
    when(engine.getPlayerContainer()).thenReturn(playerContainer);
    when(playerContainer.getActive()).thenReturn(player);
    when(playerContainer.getPlayers()).thenReturn(List.of(player));
    when(player.areAllScoreablesInvisible()).thenReturn(true);
    when(engine.getGameObjects()).thenReturn(Collections.singletonList(gameObject));
    command.execute(engine);
    verify(gameObject, times(1)).setVisible(false);
    verify(engine, times(1)).getGameObjects();
    verify(playerContainer, times(2)).getActive();
    verify(player, times(1)).areAllScoreablesInvisible();
    verify(player, times(1)).applyGameResult(true);
  }


}
