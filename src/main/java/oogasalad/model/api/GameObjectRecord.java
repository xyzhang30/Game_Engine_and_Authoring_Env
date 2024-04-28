package oogasalad.model.api;

/**
 * Represents an object with properties such as mass, position, velocity, visibility, etc.
 *
 * @param id           The unique identifier of the object.
 * @param mass         The mass of the object.
 * @param x            The x-coordinate of the object's position.
 * @param y            The y-coordinate of the object's position.
 * @param velocityX    The velocity of the object along the x-axis.
 * @param velocityY    The velocity of the object along the y-axis.
 * @param visible      Indicates whether the object is visible or not.
 * @param staticMu     The coefficient of static friction.
 * @param kineticMu    The coefficient of kinetic friction.
 * @param inclineAngle The angle of inclination if the object is on an inclined plane.
 * @param width        The width of the object.
 * @param height       The height of the object.
 * @param inelastic    Indicates whether collisions with this object are inelastic.
 * @param phaser       True iff object phases through inelastic objects.
 */

public record GameObjectRecord(int id, double mass, double x, double y, double velocityX,
                               double velocityY, boolean visible, double staticMu, double kineticMu,
                               double inclineAngle, double width,
                               double height, boolean inelastic, boolean phaser) {

}
