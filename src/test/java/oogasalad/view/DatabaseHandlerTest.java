package oogasalad.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
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




}
