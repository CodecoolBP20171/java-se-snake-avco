package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.*;
import java.util.List;

// class for holding all static stuff
public class Globals {

    public static final double WINDOW_WIDTH = 600;
    public static final double WINDOW_HEIGHT = 600;

    public static Image snakeHeadGreen = new Image("snake_head_green.png");
    public static Image snakeBodyGreen = new Image("snake_body_green.png");
    public static Image snakeHeadRed = new Image("snake_head_red.png");
    public static Image snakeBodyRed = new Image("snake_body_red.png");
    public static Image simpleEnemy = new Image("simple_enemy.png");
    public static Image simpleEnemy1 = new Image("simple_enemy_1.png");
    public static Image simpleEnemy2 = new Image("simple_enemy_2.png");
    public static Image simpleEnemy3 = new Image("simple_enemy_3.png");
    public static Image laser = new Image("laser.png");
    public static Image advancedEnemy = new Image("missile.png");
    public static Image circlingENemy = new Image("spider.png");
    public static Image powerupSetLength = new Image("powerup_length.png");
    public static Image powerupAddHealth = new Image("powerup_health.png");
    public static Image powerupSetTurnRate = new Image("powerup_turn_rate.png");


    //.. put here the other images you want to use

    public static GameLoop gameLoop;
    public static List<Image> SimpleEnemies = new ArrayList();
    public static List<GameEntity> gameObjects;
    public static List<GameEntity> newGameObjects; // Holds game objects crated in this frame.
    public static List<GameEntity> oldGameObjects; // Holds game objects that will be destroyed this frame.
    public static List<SnakeHead> players;
    static int numOfEnemies;
    public static HashMap<String, Integer> scoreList;

    static {
        gameObjects = new LinkedList<>();
        newGameObjects = new LinkedList<>();
        oldGameObjects = new LinkedList<>();
        players = new ArrayList<>();
        scoreList = new HashMap<>();
    }

    public static void addGameObject(GameEntity toAdd) {
        newGameObjects.add(toAdd);
    }

    public static void removeGameObject(GameEntity toRemove) {
        oldGameObjects.add(toRemove);
    }

    public static List<GameEntity> getGameObjects() {
        return Collections.unmodifiableList(gameObjects);
    }

    public static void setNumOfEnemies(int number) {
        numOfEnemies += number;
    }

    public static int getNumOfEnemies() {
        return numOfEnemies;
    }

    public static void clearGameObjects() {
        Globals.gameObjects.clear();
    }
}
