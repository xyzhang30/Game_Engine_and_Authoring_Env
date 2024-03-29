/**
 * Parsing JSON files into GameInfoRecrod objects interpretable by the GameEngine
 *
 * @author Judy He, Alisha Zhang
 *
 */
interface GameParser {

  /**
   * parse a given JSON file containing data that defines the game and creates the game
   * creates all subturn/turn/game policies and condition maps for the game engine
   *
   * @param filePath, path to JSON file
   * @return GameInfoRecord representing the JSON data that can be interpreted by the GameEngine
   * @throws InvalidFileException when file is empty, file type is invalid, file format is invalid
   */
  void parseGame(String filePath) throws InvalidFileException;

}

