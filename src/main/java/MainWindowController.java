package main.java;

import static main.java.virtual.entities.Direction.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import main.java.virtual.entities.Direction;
import main.java.virtual.exceptions.UnableToMoveException;
import main.java.virtual.maze.Element;
import main.java.virtual.maze.Maze;
import main.java.virtual.entities.Player;
import main.java.render.SoundRender;

public class MainWindowController {
  @FXML
  private Canvas canvasMaze;

  private Stage stage;

  private final String mazeFileName = "maze_10x10.bmp";
  private final String emptySpotSoundFileName = "stone_dropping.wav";
  private final String wallSoundFileName = "water_splash.wav";
  private final String endSoundFileName = "cowbell.wav";
  private final String walkingSoundFileName = "robot_walk.wav";
  private final String dangerSoundFileName = "danger.wav";
  private final String finishSoundFileName = "finish.wav";

  private final int maxProbeStep = 4;

  private SoundRender soundMaze;
  private Media emtpySpotSound;
  private Media wallSound;
  private Media endSound;
  private Media walkingSound;
  private Media dangerSound;
  private Media finishSound;

  private BufferedImage mazeImage;

  @FXML
  public void initialize() {
    try {
      mazeImage = ImageIO.read(getClass().getResource("../resources/mazes/" + mazeFileName));
    } catch (IOException e) {
      System.err.println("Could not load '" + mazeFileName + "' - " + e.getMessage());
    }

    Maze maze = new Maze(mazeImage);
    Player player = new Player(maze.getStartPoint());

    soundMaze = new SoundRender(maze, player);
    emtpySpotSound = new Media(new File(getClass().getResource("../resources/sounds/" + emptySpotSoundFileName).getFile()).toURI().toString());
    wallSound = new Media(new File(getClass().getResource("../resources/sounds/" + wallSoundFileName).getFile()).toURI().toString());
    endSound = new Media(new File(getClass().getResource("../resources/sounds/" + endSoundFileName).getFile()).toURI().toString());
    walkingSound = new Media(new File(getClass().getResource("../resources/sounds/" + walkingSoundFileName).getFile()).toURI().toString());
    dangerSound = new Media(new File(getClass().getResource("../resources/sounds/" + dangerSoundFileName).getFile()).toURI().toString());
    finishSound = new Media(new File(getClass().getResource("../resources/sounds/" + finishSoundFileName).getFile()).toURI().toString());
  }

  public void setStage(Stage stage) {
    this.stage = stage;
    canvasMaze.requestFocus();
    Image image = SwingFXUtils.toFXImage(mazeImage, null);
    canvasMaze.getGraphicsContext2D().drawImage(image , 0, 0, image.getWidth(), image.getHeight(), 0, 0, canvasMaze.getWidth(), canvasMaze.getHeight());
  }

  @FXML
  private void handleKeyPress(KeyEvent keyEvent) {
    System.out.println("[DEBUG] key pressed: " + keyEvent.getCode());

    boolean foundEnd = false;
    try {
      if (keyEvent.getCode() == KeyCode.W) {
        playSounds(soundMaze.probeMultipleSteps(UP, maxProbeStep), UP);
      } else if (keyEvent.getCode() == KeyCode.A) {
        playSounds(soundMaze.probeMultipleSteps(LEFT, maxProbeStep), LEFT);
      } else if (keyEvent.getCode() == KeyCode.S) {
        playSounds(soundMaze.probeMultipleSteps(DOWN, maxProbeStep), DOWN);
      } else if (keyEvent.getCode() == KeyCode.D) {
        playSounds(soundMaze.probeMultipleSteps(RIGHT, maxProbeStep), RIGHT);
      } else if (keyEvent.getCode() == KeyCode.UP) {
        foundEnd = soundMaze.walk(UP);
        new MediaPlayer(walkingSound).play();
      } else if (keyEvent.getCode() == KeyCode.RIGHT) {
        foundEnd = soundMaze.walk(RIGHT);
        new MediaPlayer(walkingSound).play();
      } else if (keyEvent.getCode() == KeyCode.DOWN) {
        foundEnd = soundMaze.walk(DOWN);
        new MediaPlayer(walkingSound).play();
      } else if (keyEvent.getCode() == KeyCode.LEFT) {
        foundEnd = soundMaze.walk(LEFT);
        new MediaPlayer(walkingSound).play();
      }
    } catch (UnableToMoveException e) {
      System.out.println("[DEBUG] - " + e.getMessage());
      new MediaPlayer(dangerSound).play();
    }

    if (foundEnd) {
      MediaPlayer mediaPlayer = new MediaPlayer(finishSound);
      mediaPlayer.setCycleCount(3);
      mediaPlayer.play();
    }
  }

  private void playSounds(List<Element> mazeElementsAhead, Direction direction) {
    double volume = 1.0;
    double decrease = 0.85;
    List<MediaPlayer> mediaPlayers = new ArrayList<>(mazeElementsAhead.size());

    for (Element element : mazeElementsAhead) {
      switch (element) {
        case WALL:
          mediaPlayers.add(createSound(wallSound, volume, direction));
          break;

        case EMPTY:
          mediaPlayers.add(createSound(emtpySpotSound, volume, direction));
          break;

        case START:
          mediaPlayers.add(createSound(emtpySpotSound, volume, direction));
          break;

        case END:
          mediaPlayers.add(createSound(endSound, volume, direction));
          break;
      }

      volume *= decrease;
      decrease *= decrease;
    }

    for (int i = 0; i < mediaPlayers.size(); i++) {
      if (i < (mediaPlayers.size() - 1)) {
        int nextIdx = i + 1;
        MediaPlayer mediaPlayer = mediaPlayers.get(i);
        mediaPlayer.setOnEndOfMedia(() -> {
          mediaPlayers.get(nextIdx).play();
        });
      }
    }

    mediaPlayers.get(0).play();
  }

  private MediaPlayer createSound(Media media, double volume, Direction direction) {
    MediaPlayer mediaPlayer = new MediaPlayer(media);

    mediaPlayer.setVolume(volume);

    switch (direction) {
      case UP:
        mediaPlayer.setBalance(0.0);
        break;
      case RIGHT:
        mediaPlayer.setBalance(0.9);
        break;
      case DOWN:
        mediaPlayer.setBalance(0.0);
        break;
      case LEFT:
        mediaPlayer.setBalance(-0.9);
        break;
    }

    return mediaPlayer;
  }
}
