package oogasalad.model.gameengine.gameobject;

import java.util.List;

public class MoveXControllable {


    public double moveX(boolean positive) {
      return 20*(positive?1:-1);
    }


}
