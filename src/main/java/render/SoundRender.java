package render;

import static virtual.maze.Element.*;

import java.util.LinkedList;
import java.util.List;
import virtual.entities.Direction;
import virtual.entities.Player;
import virtual.exceptions.UnableToMoveException;
import virtual.maze.Element;
import virtual.maze.Maze;

/**
 * Created by Valentin on 17.06.2017.
 * TODO documentation
 */
public class SoundRender {
  private Maze maze;
  private Player player;

  public SoundRender(Maze maze, Player player) {
    this.maze = maze;
    this.player = player;
  }

  public Element probeOneStep(Direction direction) {
    return probeMultipleSteps(direction, 1).get(0);
  }

  public boolean walk(Direction direction) throws UnableToMoveException {
    System.out.println("[DEBUG] " + player);

    if (probeOneStep(direction) == WALL) {
      throw new UnableToMoveException("Cannot move " + direction.name() + ", a wall blocks the way");
    }
    player.walk(direction);

    if (maze.getEndPoint().equals(player)) {
      return true;
    }
    System.out.println("[DEBUG] " + player);
    return false;
  }

  public List<Element> probeMultipleSteps(Direction direction, int steps) {
    System.out.println("[DEBUG] probing " + steps + " steps ahead...");

    List<Element> mazeElements = new LinkedList<>();
    Player probingPlayer = new Player(player.getLocation());

    for (int i = 0; i < steps; i++) {
      Element elementAhead = maze.getUnit(probingPlayer.getSurrounding(direction));
      System.out.println("[DEBUG] at " +  (i + 1) + ": " + elementAhead.name());
      mazeElements.add(elementAhead);
      if (elementAhead != WALL) {
        probingPlayer.walk(direction);
      } else {
        break;
      }
    }
    return mazeElements;
  }
}
