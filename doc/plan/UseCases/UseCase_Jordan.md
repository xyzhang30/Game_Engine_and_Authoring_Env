# API Example/ Use Case

1. When a user places a goal object in the authoring environment
    * Update the graphic representation of the game environment to show the goal object
    * Update the pending JSON file to contain the details of the goal object (size, shape, location)
    * Prompt for what happens post score (does point scoring object disappear, does a new level
      start)

```java
//goal object is represented by Element goalObj
goalObj.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            sceneManager.updateScene(); //pass necessary info about goalObj positioning
            GameParser.updatePendingFile(); //pass necessary info about goalObj
            sceneManager.createAndDisplayPrompt();
        });