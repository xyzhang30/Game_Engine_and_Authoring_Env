# Noah Use Cases

### Ball Collides with Hole


```java
//GameEngineExternal
int ballId;
int holeId;
Rules rules;
GameManager.onCollision(ballId, holeId); //got info from view
//this is now in Game Manager
TriConsumer<GameManager,Integer,Integer> handler = rules.collisionHandler().get(ballId).get(holeId);
handler.accept(this, ballId, holeId);
//call logichandler getters to give the info back to view

TriConsumer<GameManager, Integer, Integer> collisionHandler = (gameManager, ballId, holeId) -> {
  Player activePlayer = gameManager.getActivePlayer();
  activePlayer.setScore(activePlayer.getScore() + 1); 
  activePlayer.getPrimary().setVelocityy(0); //this could be ltit
  activePlayer.getPrimary().setVelocityx(0);
  activePlayer.getPrimary().setVisible(false);
  gameManager.getLogicManager().endStage();
};


```