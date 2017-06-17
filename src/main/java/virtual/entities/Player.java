package main.java.virtual.entities;

import java.awt.Point;

/**
 * Created by Valentin on 17.06.2017.
 * TODO documentation
 */
public class Player extends Point {
  private final static int STEP_SIZE = 1;

  public Player(Point p) {
    super(p);
  }

  public Player(int x, int y) {
    super(x, y);
  }

  public void walk(Direction direction) {
    switch (direction) {
      case UP:
        translate(0, -STEP_SIZE);
        break;
      case RIGHT:
        translate(STEP_SIZE, 0);
        break;
      case DOWN:
        translate(0, STEP_SIZE);
        break;
      case LEFT:
        translate(-STEP_SIZE, 0);
        break;
    }
  }

  public void walkUp() {
    walk(Direction.UP);
  }

  public void walkRight() {
    walk(Direction.RIGHT);
  }

  public void walkDown() {
    walk(Direction.DOWN);
  }

  public void walkLeft() {
    walk(Direction.LEFT);
  }

  public Point getSurrounding(Direction direction) {
    Point p = null;
    switch (direction) {
      case UP:
        p = getLocation();
        p.translate(0, -STEP_SIZE);
        break;
      case RIGHT:
        p = getLocation();
        p.translate(STEP_SIZE, 0);
        break;
      case DOWN:
        p = getLocation();
        p.translate(0, STEP_SIZE);
        break;
      case LEFT:
        p = getLocation();
        p.translate(-STEP_SIZE, 0);
        break;
    }
    return p;
  }

  @Override
  public String toString() {
    return "Player{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }
}
