package oogasalad.model;

import oogasalad.model.gameparser.GameLoader;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import oogasalad.model.api.exception.InvalidFileException;
import oogasalad.model.gameparser.data.GameData;

public class GameLoaderTest {

  @Mock
  private GameData mockGameData;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testConstructorWithId() throws InvalidFileException {
    GameLoaderMock gameLoader = new GameLoaderMock(0);
    assertNotNull(gameLoader.gameData);
    assertEquals(mockGameData, gameLoader.gameData);
  }

  @Test
  public void testConstructorWithFilePath() throws InvalidFileException {
    GameLoader gameLoader = new GameLoaderMock("data/singlePlayerMiniGolf.json");
    assertNotNull(gameLoader.gameData);
    assertEquals(mockGameData, gameLoader.gameData);
  }

  @Test(expected = InvalidFileException.class)
  public void testInvalidFilePath() throws InvalidFileException {
    GameLoader gameLoader = new GameLoaderMock("invalidFilePath.json");
  }

  // Mock implementation of GameLoader for testing
  private class GameLoaderMock extends GameLoader {
    public GameLoaderMock(int id) throws InvalidFileException {
      super(id);
    }

    public GameLoaderMock(String filePath) throws InvalidFileException {
      super(filePath);
    }

    protected void parseJSON(String filePath) throws InvalidFileException {
      // Mocking the behavior of parseJSON to return mockGameData
      this.gameData = mockGameData;
    }
  }
}

