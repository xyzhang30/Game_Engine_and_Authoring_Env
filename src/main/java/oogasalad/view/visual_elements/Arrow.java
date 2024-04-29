package oogasalad.view.visual_elements;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Polygon;

public class Arrow extends Polygon {

  private static final double DEFAULT_X_POS = 0.0;
  private static final double DEFAULT_Y_POS = 0.0;
  private static final double DEFAULT_STEM_WIDTH = 10.0;
  private static final double DEFAULT_STEM_HEIGHT = 30.0;
  private static final double DEFAULT_ARROW_WIDTH_OFFSET = 5.0;
  private static final double DEFAULT_ARROW_HEIGHT_OFFSET = 15.0;

  public Arrow() {
    List<Double> arrowPoints = getArrowPoints(DEFAULT_X_POS, DEFAULT_Y_POS, DEFAULT_STEM_WIDTH,
        DEFAULT_STEM_HEIGHT, DEFAULT_ARROW_WIDTH_OFFSET,
        DEFAULT_ARROW_HEIGHT_OFFSET);
    this.getPoints().addAll(arrowPoints);
  }

  public void setArrowDimensions(double xPos, double yPos, double stemWidth, double stemHeight,
      double arrowWidthOffset, double arrowHeightOffset) {
    List<Double> arrowPoints = getArrowPoints(xPos, yPos, stemWidth, stemHeight, arrowWidthOffset,
        arrowHeightOffset);
    this.getPoints().clear();
    this.getPoints().addAll(arrowPoints);

  }

  private List<Double> getArrowPoints(double xPos, double yPos, double stemWidth, double stemHeight,
      double arrowWidthOffset, double arrowHeightOffset) {
    List<Double> arrowPoints = new ArrayList<>();

    arrowPoints.add(xPos);
    arrowPoints.add(yPos);
    arrowPoints.add(xPos + stemWidth);
    arrowPoints.add(yPos);
    arrowPoints.add(xPos + stemWidth);
    arrowPoints.add(yPos - stemHeight);
    arrowPoints.add(xPos + stemWidth + arrowWidthOffset);
    arrowPoints.add(yPos - stemHeight);
    arrowPoints.add(xPos + stemWidth / 2);
    arrowPoints.add(yPos - stemHeight - arrowHeightOffset);
    arrowPoints.add(xPos - arrowWidthOffset);
    arrowPoints.add(yPos - stemHeight);
    arrowPoints.add(xPos);
    arrowPoints.add(yPos - stemHeight);

    return arrowPoints;
  }
}