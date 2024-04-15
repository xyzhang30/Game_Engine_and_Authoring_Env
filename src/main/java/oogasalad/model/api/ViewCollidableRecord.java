package oogasalad.model.api;

import java.util.List;
import oogasalad.model.api.data.CollidableShape;

public record ViewCollidableRecord(Integer id, List<Integer> color, CollidableShape shape, double width,
                                   double height, double startXpos, double startYpos,
                                   String image) {

}

