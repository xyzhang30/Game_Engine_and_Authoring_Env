package oogasalad.model.api;

import java.util.List;

public record ViewCollidableRecord(Integer collidableId, List<Integer> color, String shape, double xdimension, double ydimention, double startXpos, double startYpos) { }

