# OOGASalad Stand Up and Retrospective Discussion

### TEAM 01

## Stand Up Meeting

### Noah

* Work done this Sprint
  This week I spent a great deal of time working on refactoring the GameEngine. I added new
  abstractions such as the Condition and StaticStateHandler, which utilize the command pattern and
  chain of responsibility pattern. Additionally, I began the process of abstracting away and
  refactoring the physics engine.

* Plan for next Sprint?

Next sprint, I plan on wearing “many hats”. I hope to have time to start working on the backend
aspects of the database, but my main priority will be making sure the game engine is flexible enough
to handle new games such as pinball and shuffle boarding. This will involve doing more robust
testing of the turn policy and scoring related commands, as well as communicating with the front end
to provide more of a concrete implementation of the concept of control labels.

* Blockers/Issues in your way

One of the key blockers for the team I think is the infrequency of integration. We should be merging
to main more often and going on a feature by feature basis, to ensure that we are all on the same
page. For me personally, I am struggling with a bug pertaining to scoring, as the command is not
being applied correctly. However, it is passing my tests, so I should probably write more.

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
    * GameScreen, ScreenManager, ControlPane, Arrow, TransitionScreen, ScoreTransitionScreen,
      UIScreen, FrontEndParser

* Plan for next Sprint?
    * First, creating the authoring environment which will be extensive,
    * but other than that I want to implement the TransitionScreens between rounds based on the type
      of game
    * Refactoring existing code
    * Implement testing more

* Blockers/Issues in your way
    * Authoring Environment, ensuring clear communication and collaboration for this component.g

### Judy

* Work done this Sprint
    * data package
        * Added all necessary classes for mapping to the format of the JSON configuration files (
          supporting use of Jackson to parse JSON files)
    * GameLoader
        * Used Jackson to parse JSON
    * GameLoaderModel
        * Parsed collidables
    * Testing
        * Added JUnit tests for all methods in GameLoaderModel (100% coverage)
        * Used AssertJ library for checking equal objects
    * Error handling
        * GameLoader: InvalidFileException (invalid JSON configuration)

* Plan for next Sprint?
    * GameBuilder: write JSON file given data from the View's game authoring environment
    * Write JSON configuration files for new games

* Blockers/Issues in your way
    * Next sprint: finalizing how to apply to Builder design pattern to writing new JSON files (
      GameBuilder)

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
    * (for next sprint) finalize how the authoring environment is passing information into the
      builder.

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

* Blockers/Issues in your way - Physics logic implemented in Moveable subclass, will have to
  refactor
  and abstract to physics engine.

## Project's current progress

I am somewhat satisfied with our progress, but think there is ample room for improvement. We have
the basics of both the engine and authoring enviornment/parser, but still need to develop more
features and remove some hard coding. We also have only worked on one game, so we are kind of
hoping (with good reason) that our commands will be able to easily apply to other games.

## Current level of communication

I think the current level of communication can be improved. We are talking a decent amount in our
chat, but not meeting nearly enough. We should be integrating our code more frequently and often, to
avoid merge marathons at the end. We did a better job this week, but had some Mac versus windows
issues at the end that made dealing with files a pain. I’m sure there is an API we can use.

## Satisfaction with team roles

I think everyone is pretty satisfied with the team roles. We are all working hard and committing
pretty frequently, and everyone is getting opportunities to work on multiple aspects of the project.
Most importantly, I think we all seem to get along quite well, which is extremely helpful as it
makes working more enjoyable.

## Teamwork that worked well

* Thing #1:
* Thing #2:

## Teamwork that could be improved

* Thing #1:

* Thing #2:

## Teamwork to improve next Sprint

Next sprint, I think we can improve by having everyone become more of a “jack of all trades” as
opposed to an “expert”. This will allow for debugging to be much easier, so that we all don’t need
to rely on the person that wrote a given part of the code base to help debug. I think everyone
should be familiar with all of the packages and not just the ones they wrote, and that could make a
big difference for the team.