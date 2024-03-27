/**
 * Parsing JSON files into GameInfoRecrod objects interpretable by the GameEngine
 *
 * @author Judy He, Alisha Zhang
 *
 */
interface GameParser {

  /**
   * parse a given JSON file containing data that defines the game
   *
   * @param filePath, path to JSON file
   * @return GameInfoRecord representing the JSON data that can be interpreted by the GameEngine
   * @throws InvalidFileException when file is empty, file type is invalid, file format is invalid
   */
  GameObjectRecord parse(String filePath) throws InvalidFileException

}

