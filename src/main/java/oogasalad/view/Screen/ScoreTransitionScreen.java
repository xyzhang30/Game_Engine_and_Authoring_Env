package oogasalad.view.Screen;

public class ScoreTransitionScreen extends TransitionScreen {
  private int score;

  public ScoreTransitionScreen(int par, int score) {
    super(); //get from parser
    this.score = score;
    setupTransitionDetails();
  }


  @Override
  protected void setupTransitionDetails() {
    String info = String.format("Score: %d", score);
    setAdditionalInfo(info);
  }
}
