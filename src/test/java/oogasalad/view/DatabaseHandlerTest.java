package oogasalad.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.database.Database;
import oogasalad.view.api.exception.IncorrectPasswordException;
import oogasalad.view.api.exception.UserNotFoundException;
import oogasalad.view.controller.DatabaseController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import oogasalad.view.database.Leaderboard;

public class DatabaseHandlerTest {

  private DatabaseController databaseController;
  private Database databaseView;
  private List<String> currentPlayersManager;
  private Leaderboard leaderboard;

  @BeforeEach
  public void setUp() {
    databaseView = mock(Database.class);
    databaseController = new DatabaseController(leaderboard, currentPlayersManager);
  }

  @Test
  public void testGetScoreFromId() {
    // Setup a list of PlayerRecord mocks
    PlayerRecord player1 = mock(PlayerRecord.class);
    PlayerRecord player2 = mock(PlayerRecord.class);

    // Configure mocks
    when(player1.playerId()).thenReturn(1);
    when(player1.score()).thenReturn(100.0);
    when(player2.playerId()).thenReturn(2);
    when(player2.score()).thenReturn(200.0);

    List<PlayerRecord> players = List.of(player1, player2);

    // Test finding an existing ID
    int score1 = databaseController.getScoreFromId(players, 1);
    assertEquals(100, score1, "Score should match for player ID 1");

    // Test finding another existing ID
    int score2 = databaseController.getScoreFromId(players, 2);
    assertEquals(200, score2, "Score should match for player ID 2");

    // Test with an ID that does not exist
    int score3 = databaseController.getScoreFromId(players, 3);
    assertEquals(0, score3, "Score should be 0 for a non-existent player ID");
  }

  @Test
  public void testAddGameResult() {
    // Setup
    String gameName = "ExampleGame";
    int gameId = 1;
    Map<Integer, String> playerMap = Map.of(
        1, "Alice",
        2, "Bob"
    );
    PlayerRecord player1 = mock(PlayerRecord.class);
    PlayerRecord player2 = mock(PlayerRecord.class);

    // Configure player mocks
    when(player1.playerId()).thenReturn(1);
    when(player1.score()).thenReturn(100.0);
    when(player2.playerId()).thenReturn(2);
    when(player2.score()).thenReturn(150.0);

    List<PlayerRecord> players = List.of(player1, player2);

    // Mock Database behaviors
    when(databaseView.addGameInstance(gameName)).thenReturn(gameId);

    // Execute
    databaseController.addGameResult(playerMap, players, gameName);

    // Verify that a new game instance was added
    verify(databaseView).addGameInstance(gameName);

    // Verify that addGameScore was called correctly for the first player (winning player)
    verify(databaseView).addGameScore(gameId, "Alice", 100, true);

    // Verify that addGameScore was called correctly for the second player
    verify(databaseView).addGameScore(gameId, "Bob", 150, false);
  }






}
