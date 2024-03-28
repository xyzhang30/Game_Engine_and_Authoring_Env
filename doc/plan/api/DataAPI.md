### Data API

## Konur Nordberg

## Overview:

The Factory and Lambda API is designed to empower developers to innovate within a physics-based game environment by abstracting object creation and behavior definitions. The API adheres to the SOLID principles, particularly:

* Single Responsibility Principle: Each factory is responsible only for the instantiation of 
one type of object, ensuring clear and manageable code.
* Open/Closed Principle: By using interfaces and lambdas, the API remains open for extension 
but closed for modification. New types of objects and behaviors can be introduced without 
altering existing code.
* Dependency Inversion Principle: High-level modules do not depend on low-level modules. 
Both depend on abstractions (interfaces), not on concretions (classes).

The API's design encourages programmers to think in terms of "what" they want to accomplish 
(create a player, define collision behavior) rather than "how" to implement it (reflection 
details, lambda syntax). This approach simplifies the learning curve and helps to avoid misuse
by encapsulating complex creation logic within factory implementations.



## Classes:

The API's main interfaces are:

```java
package com.gameengine.factories;

/**
 * Factory interface for creating instances of a given type.
 */
public interface Factory<T> {
  T create(Object... args);
}

/**
 * ReflectiveFactory uses Java reflection to dynamically create instances of classes.
 */
public class ReflectiveFactory<T> implements Factory<T> {
  // ...
}

/**
 * CollisionHandler functional interface to handle collisions between Collidable entities.
 */
@FunctionalInterface
public interface CollisionHandler<T extends Collidable> {
  void handleCollision(T collidable1, T collidable2);
}

/**
 * PlayerModifier functional interface to modify the state of a Player entity.
 */
@FunctionalInterface
public interface PlayerModifier<T extends Player> {
  void modifyPlayer(T player);
}

```


## Details

The API provides the following use cases:

* Creating Entities: Use ReflectiveFactory to instantiate game entities, such as players or 
collidables.
* Defining Behaviors: Use CollisionHandler and PlayerModifier to define custom behaviors 
via lambdas, which can then be applied to entities during the game.

Collaboration with other APIs:

* The Factory<T> interface may utilize parsers from the GameParser API to retrieve 
configuration details for instantiation.
* CollisionHandler and PlayerModifier behaviors can be registered with the GameEngineInternal's 
GameManager to be triggered during game updates.

Plugin Points:

* The ReflectiveFactory provides a plugin point to inject custom creation logic for new entity types.
* CollisionHandler and PlayerModifier offer points to inject custom game logic, such as special 
scoring rules or collision effects.

## Considerations:

* Performance Impact: The use of reflection and dynamic lambda creation could impact performance,
requiring benchmarking and potential optimization.
* Error Handling: Robust error handling and clear exception messaging are essential to inform 
the developer of misuse.
* Documentation: Comprehensive Javadoc documentation will be necessary to ensure developers 
understand the intended use of each interface and method.

## Potential Issues:

* The balance between reflection-based flexibility and static-type safety.
* Ensuring that lambda expressions are serialized correctly if the game state needs to be saved 
and restored.
* Integration testing with the full suite of APIs to ensure compatibility.