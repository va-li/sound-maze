package main.java.virtual.maze;

/**
 * Created by Valentin on 17.06.2017.
 * TODO documentation
 */
public enum Element {
  WALL(-16777216),
  EMPTY(-1),
  START(-65536),
  END(-16776961);

  private final int rgb;

  private Element(int rgb) {
    this.rgb = rgb;
  }

  public int getRGB() {
    return rgb;
  }

  public static Element fromRGB(int rgb) {
    for (Element e: Element.values()) {
      if (e.getRGB() == rgb) {
        return e;
      }
    }
    return null;
  }
}
