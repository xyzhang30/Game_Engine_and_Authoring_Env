/**
 * Stores game data input by user from View in JSON file
 *
 * @author Judy He, Alisha Zhang
 *
 */
interface GameBuilder {

  /**
   * Stores game information data in JSON file
   *
   * @param gameInfo, GameInfoRecord for generating a JSON file defining the game
   * @throws InvalidInputException when an input value for a certain game parameter is invalid
   */
  void createGame(GameInfoRecord gameInfo) throws InvalidInputException


}