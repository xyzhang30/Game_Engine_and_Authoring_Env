# OOGASalad Stand Up and Retrospective Discussion

### TEAM

### NAMES

## Stand Up Meeting

### Noah

* Work done this Sprint
In this sprint, I primarily worked on the Game Engine module. This involved writing all of the 
  Command classes, as well as designing and redesigning APIs for the backend. I worked on 9 
  different tests that focused on both the commands and the physics engine, and outlined a basic 
  physics engine. However, there is still work to be done.
* Plan for next Sprint?
Next Sprint, I hope to refactor the Physics Engine and abstract it away, so that collisions are 
  detected in the backend instead of the front end. I would also like to spend time working on 
  adding abstractions to the game engine so that more complex games would work, as well as 
  features such as multiple rounds, turns, and balls. Perhaps live games too, but only if we 
  have the time.
* Blockers/Issues in your way
The primary blockers in my way is the physics engine API. The fact that view is detecting the 
  collisions makes it extremely difficult to test the model, as you need to manually "simulate" 
  collisions, which involves solving physics equations and has the pre-requisite of all 
  collidables being able to move correctly. I am excited to continue working!

### Kevin

* Work done this Sprint

* Plan for next Sprint?

* Blockers/Issues in your way

### Konur

* Work done this Sprint

* Plan for next Sprint?

* Blockers/Issues in your way

### Doga

* Work done this Sprint
  * GameScreen, ScreenManager, ControlPane, Arrow, TransitionScreen, ScoreTransitionScreen, UIScreen, FrontEndParser

* Plan for next Sprint?
  * First, creating the authoring environment which will be extensive, 
  * but other than that I want to implement the TransitionScreens between rounds based on the type of game
  * Refactoring existing code
  * Implement testing more

* Blockers/Issues in your way
  * Authoring Environment, ensuring clear communication and collaboration for this component.g

### Judy

* Work done this Sprint

* Plan for next Sprint?

* Blockers/Issues in your way

### Alisha

* Work done this Sprint
  * Finished view and model parser
  * Made JSON files for games
  * Fixed physics for object collisions 
  * Made tests for view parser

* Plan for next Sprint?
  * Finish the game builder (the writing JSON part) for the game authoring environment
  * Make more game files for different games
  * Refactor the view parser
  * Make more tests for view parser, make tests for the game builder

* Blockers/Issues in your way
  * (for next sprint) finalize how the authoring environment is passing information into the builder.

### Jordan

* Controller, TitleScreen, MenuScreen, GameScreen, CollisionManager, SceneManager, AnimationManager,
  Visual Element classes; pair programming, front-end planing meetings
* 
* Refactoring existing code (fixing dependencies, reflection, hard coded values), meeting with
  Luke?, start on authoring environment

* Just making sure we are all on the same page for authoring


### Konur

* Work done this Sprint - None, had a terribly busy academic week

* Plan for next Sprint? - Create a robust physics engine

* Blockers/Issues in your way - Physics logic implemented in Moveable subclass, will have to refactor
and abstract to physics engine.

## Project's current progress

We currently have a functioning one-hole mini golf game. There are different types of surfaces 
with varying frictions, a hole, and a water hazard. The game can be played with one player. We 
have a parser that reads the file from JSON, and different splash screens in the front end.

## Current level of communication

Communication has been adequate, but can be improved. Our group chat is active and our meetings 
are somewhat frequent and engaging, but we can be better with keeping each other updated and 
communicating through issues on git lab. For example, we had a miscommunication where the view 
and model both created the same parser, because we were not clear whose responsibility it is.

## Satisfaction with team roles

We are all very satisfied with team roles. We all have a reasonable number of commits, are all 
working hard, and supporting each other. When issues have arisen, we have spoken about it as a 
team, and have been very supportive.

## Teamwork that worked well

* Thing #1: Designing of APIs / White boarding in person together

* Thing #2: We are all very supportive and understanding of each other : Jordan brought cookies 
  to a late night work session!

## Teamwork that could be improved

* Thing #1: Long integration session the night before the demo

* Thing #2: Better communication of API changes

## Teamwork to improve next Sprint

Next sprint, we will try to meet more frequently and do a better job of keeping each other 
updated with API changes. We will use the issues features on git more regularly, to hold 
everyone accountable, and try to have a daily or every-other-day stand-up, for the sake of all 
being engaged, accountability, and team comradery.