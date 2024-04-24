package oogasalad.model.gameengine.command;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import oogasalad.model.gameengine.command.IncrementPointsCommand;
import org.junit.jupiter.api.Test;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.scoreable.Scoreable;

public class IncrementPointsCommandTest {


  @Test
  public void testExecute() {
    GameObject gameObject = mock(GameObject.class);
    Scoreable scoreable = mock(Scoreable.class);
    Map<Integer, GameObject> gameObjectMap = Collections.singletonMap(1, gameObject);
    GameEngine engine = mock(GameEngine.class);

    IncrementPointsCommand command = new IncrementPointsCommand(Arrays.asList(1, 5), gameObjectMap);

    when(gameObject.getScoreable()).thenReturn(Optional.of(scoreable));

    command.execute(engine);

    verify(scoreable, times(1)).incrementTemporaryScore(5);
  }
}
