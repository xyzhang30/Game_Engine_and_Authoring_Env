package oogasalad.view.game_environment.scene_management;

import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import oogasalad.model.api.PlayerRecord;
import oogasalad.view.scene_management.scene_element.GameStatusManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the GameStatusManager class.
 *
 * @author Doga Ozmen
 */
public class GameStatusManagerTest extends DukeApplicationTest {

  private GameStatusManager gameStatusManager;
  private Text turnDisplay;
  private Text roundDisplay;
  private ListView<String> scoreListDisplay;

  /**
   * Initializes JavaFX objects before each test.
   */
  @Start
  private void start() {
    // Initialize JavaFX objects
    turnDisplay = new Text();
    roundDisplay = new Text();
    scoreListDisplay = new ListView<>();
  }

  @BeforeEach
  public void setUp() {
    start();
    gameStatusManager = new GameStatusManager();
    gameStatusManager.setTurnText(turnDisplay);
    gameStatusManager.setRoundText(roundDisplay);
    gameStatusManager.setScoreList(scoreListDisplay);
  }

  /**
   * Tests updating the game status.
   */
  @Test
  public void testUpdateGameStatus() {
    Map<Integer, String> playerMap = new HashMap<>();
    playerMap.put(1, "Alice");
    playerMap.put(2, "Bob");

    List<PlayerRecord> players = Arrays.asList(
        new PlayerRecord(1, 10, 1),
        new PlayerRecord(2, 15, 2)
    );

    gameStatusManager.update(players, 1, 3, playerMap);

    assertEquals("Alice", turnDisplay.getText());
    assertEquals("3", roundDisplay.getText());
    assertTrue(scoreListDisplay.getItems().contains("Alice: 10.0"));
    assertTrue(scoreListDisplay.getItems().contains("Bob: 15.0"));
  }

  /**
   * Tests restoring the last update.
   */
  @Test
  public void testRestoreLastUpdate() {
    testUpdateGameStatus();

    turnDisplay.setText("");
    roundDisplay.setText("");
    scoreListDisplay.getItems().clear();

    gameStatusManager.restoreLastUpdate();

    assertEquals("", turnDisplay.getText());
    assertEquals("", roundDisplay.getText());
    assertTrue(scoreListDisplay.getItems().contains("Alice: 10.0"));
    assertTrue(scoreListDisplay.getItems().contains("Bob: 15.0"));
  }
}
