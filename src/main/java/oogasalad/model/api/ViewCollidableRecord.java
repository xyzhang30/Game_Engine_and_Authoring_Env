package oogasalad.model.api;

import java.util.List;

public record ViewCollidableRecord(Integer id, List<Integer> color, String shape, double width,
                                   double height, double startXpos, double startYpos,
                                   String image) {

}

