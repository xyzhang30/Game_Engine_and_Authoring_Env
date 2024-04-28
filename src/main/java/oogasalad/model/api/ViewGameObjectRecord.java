package oogasalad.model.api;

import java.util.List;
import oogasalad.model.api.data.GameObjectShape;

public record ViewGameObjectRecord(Integer id, List<Integer> color, String shape,
                                   double width,
                                   double height, double startXpos, double startYpos,
                                   String image, double direction) {

}

