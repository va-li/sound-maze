package main.java.virtual.maze;


import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Valentin on 17.06.2017.
 * TODO documentation
 */
public class Maze {

  private final BufferedImage mazeImage;
  private final Point startPoint;
  private final Point endPoint;

  //private final Element[][] element

  public Maze(BufferedImage mazeImage) {
    if (mazeImage == null) {
      throw new IllegalArgumentException("mazeImage cannot be NULL.");
    }
    this.mazeImage = mazeImage;
    this.startPoint = getPointByRGB(Element.START.getRGB());
    this.endPoint = getPointByRGB(Element.END.getRGB());
  }

  public Element getUnit(int x, int y) {
    return Element.fromRGB(mazeImage.getRGB(x, y));
  }

  public Element getUnit(Point p) {
    return getUnit(p.x, p.y);
  }

  public int getWidth() {
    return mazeImage.getWidth();
  }

  public int getHeight() {
    return mazeImage.getHeight();
  }

  public Point getStartPoint() {
    return startPoint;
  }

  public Point getEndPoint() {
    return endPoint;
  }

  private Point getPointByRGB(int rgb) {
    for (int x = 0; x < mazeImage.getWidth(); x++) {
      for (int y = 0; y < mazeImage.getHeight(); y++) {
        System.out.println("[DEBUG] (" + x + ", " + y + ") = " + mazeImage.getRGB(x, y));
        if (rgb == mazeImage.getRGB(x, y)) {
          return new Point(x, y);
        }
      }
    }
    return null;
  }
}
