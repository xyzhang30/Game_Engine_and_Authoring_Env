package oogasalad.engine.api;

import java.util.List;

public record GameRecord(List<CollidableRecord> collidables, List<PlayerRecord> players, int round,
                  int turn, int subturn, int stage) {}


