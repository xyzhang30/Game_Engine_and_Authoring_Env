package oogasalad.view;

import oogasalad.model.api.GameRecord;

interface GameController {
  void startGame();
  void pauseGame();
  void resumeGame();
  GameRecord getLatestGameState(double dt); // Assumed to fetch the latest game state
}