package oogasalad.model.api;

import java.util.List;

public record PlayerRecord(int playerId, double score, int activeControllable,
                           boolean active) {

}

