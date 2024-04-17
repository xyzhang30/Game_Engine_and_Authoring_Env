package oogasalad.model.api;

import java.util.List;

public record GameRecord(List<GameObjectRecord> gameObjectRecords, List<PlayerRecord> players, int round,
                         int turn, boolean gameOver, boolean staticState) {

}




