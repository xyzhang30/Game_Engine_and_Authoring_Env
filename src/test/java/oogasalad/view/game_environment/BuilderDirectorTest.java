package oogasalad.view.game_environment;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import oogasalad.model.api.data.GameData;
import oogasalad.model.api.exception.InvalidJSONDataException;
import oogasalad.view.controller.BuilderDirector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class BuilderDirectorTest {
  private BuilderDirector director;
  private GameData gameData;
  private ObjectMapper mockMapper;

  @BeforeEach
  public void setUp() {
    gameData = new GameData();
    director = new BuilderDirector();
    mockMapper = mock(ObjectMapper.class);
  }

  @Test
  public void testConstructCollidableObjects() {
    // This would ideally require mocking the GameObjectsBuilder and ensuring it's called correctly.
    List<Object> fieldData = Collections.singletonList(new Object());
    director.constructCollidableObjects(fieldData);
    // Assertions to verify the interactions or state changes
  }

//  @Test
//  public void testWriteGame() throws InvalidJSONDataException, IOException {
//    String fileName = "testGame";
//    String gameDescription = "Test Game Description";
//    String folderPath = "test_folder/";
//
//    when(mockMapper.writerWithDefaultPrettyPrinter()).thenReturn(mock(ObjectWriter.class));
//    try (MockedConstruction<ObjectMapper> mocked = Mockito.mockConstruction(ObjectMapper.class,
//        (mock, context) -> {
//          when(mock.writerWithDefaultPrettyPrinter()).thenReturn(mock(ObjectWriter.class));
//        })) {
//
//      director.writeGame(fileName, gameDescription, gameData, folderPath);
//      // Verify that JSON writing process was attempted
//      verify(mockMapper).writerWithDefaultPrettyPrinter();
//    }
//  }

//  @Test
//  public void testWriteGameWithInvalidData() {
//    String fileName = "invalidGame";
//    String gameDescription = "Invalid Game";
//    String folderPath = "test_folder/";
//    // Simulate missing game object data
//    gameData.setGameObjectProperties(null);
//
//    Exception exception = assertThrows(InvalidJSONDataException.class, () -> {
//      director.writeGame(fileName, gameDescription, gameData, folderPath);
//    });
//
//    assertTrue(exception.getMessage().contains("NullJSONFieldError"));
//  }
}
