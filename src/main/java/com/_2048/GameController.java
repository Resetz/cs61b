package com._2048;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.fxml.FXML;
import java.util.*;

public class GameController {

    @FXML
    GridPane grid_game;
    ArrayList<ArrayList<StackPane>> gridHandles;

    @FXML
    Text text_score;

    @FXML
    Text text_lastmove;

    @FXML
    VBox main_node;


    Game game;
    Color leftGradient = Color.web("#FFFFFF");
    Color rightGradient = Color.web("#FFA500");

    public GameController() {
        //scene = grid_game.getScene();
    }
    public void initialize() {
        this.game = new Game(4);

        // Loop through the grid and initialize shapes w/ text.
        System.out.println("initialized!");

        gridHandles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            gridHandles.add(new ArrayList<>());
            for (int j = 0 ; j < 4 ; j++) {
                gridHandles.get(i).add(null);
            }
        }

        for (int i = 0 ; i < 4 ; i++) {
            for (int j = 0 ; j < 4 ; j++) {
                StackPane s = new StackPane();
                Rectangle r = new Rectangle(100,100);
                r.heightProperty().bind(grid_game.heightProperty().divide(4));
                r.widthProperty().bind(grid_game.widthProperty().divide(4));

                Text t = new Text();
                t.setBoundsType(TextBoundsType.VISUAL);

                s.getChildren().addAll(r, t);

                gridHandles.get(i).set(j, s);
                this.grid_game.add(s, j, i); // adding to GridPane is col, row
            }
        }
        updateGrid();
    }

    public void updateGrid() {
        int[][] grid = game.getNumArr();

        for (int i = 0 ; i < 4 ; i++) {
            for (int j = 0 ; j < 4 ; j++) {
                StackPane s = gridHandles.get(i).get(j);
                Rectangle r = (Rectangle) s.getChildren().get(0);
                Text t = (Text) s.getChildren().get(1);

                Color col = getGradient((double)grid[i][j]/16);
                r.setFill(col);

                if (grid[i][j] > 0) {
                    t.setText(Integer.toString(1 << grid[i][j]));
                } else {
                    t.setText("");
                }
            }
        }
    }

    public void updateScore() {
        text_score.setText("Score: " + game.getScore());
    }

    public void updateUI() {
        updateGrid();
        updateScore();
    }

    public void processKeyEvent(KeyEvent e) {
        System.out.println("Key Event Received: ");
        System.out.println(e.getCode());

        switch (e.getCode()) {
            case UP -> {
                game.parseMove(2);
            }
            case DOWN -> {
                game.parseMove(0);
            }
            case LEFT -> {
                game.parseMove(3);
            }
            case RIGHT -> {
                game.parseMove(1);
            }
        }

        updateUI();
    }

    public Color getGradient(double percentage) {
        int r = (int) ((leftGradient.getRed() * (1 - percentage) + rightGradient.getRed() * percentage)*255);
        int g = (int) ((leftGradient.getGreen() * (1 - percentage) + rightGradient.getGreen() * percentage)*255);
        int b = (int) ((leftGradient.getBlue() * (1 - percentage) + rightGradient.getBlue() * percentage)*255);
        return Color.rgb(r,g,b);
    }




}
