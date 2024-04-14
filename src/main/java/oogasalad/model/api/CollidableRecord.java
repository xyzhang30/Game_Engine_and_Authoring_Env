package oogasalad.model.api;

public record CollidableRecord(int id, double mass, double x, double y, double velocityX,
                               double velocityY, boolean visible, double staticMu, double kineticMu, double inclineAngle, double width,
                               double height) {

}
