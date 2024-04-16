package oogasalad.model.api;

import java.util.List;

public record GameRecord(List<CollidableRecord> collidables, List<PlayerRecord> players, int round,
                         int turn, boolean gameOver, boolean staticState) {

}




