package com.example.week3_grouphw;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.image.PixelReader;

public class RobotController {
    private ImageView robotView; //The robot
    private Image mazeImage;//The Maze
    private double stepSize = 5.0; // How much the robot will move by

    public RobotController(ImageView robotView, Image mazeImage) {
        this.robotView = robotView;
        this.mazeImage = mazeImage;
    }

    /**
     * Method that will move the robot on arrow key presses
     * @param event the arrow key presses
     */
    public void handleKeyPress(KeyEvent event, ImageView robot) {
        double x = robot.getX();
        double y = robot.getY();

        switch (event.getCode()) {
            case W:
                if (canMove(x, y - stepSize)) {
                    robot.setY(y - stepSize);
                }
                break;
            case S:
                if (canMove(x, y + stepSize)) {
                    robot.setY(y + stepSize);
                }
                break;
            case A:
                if (canMove(x - stepSize, y)) {
                    robot.setX(x - stepSize);
                }
                break;
            case D:
                if (canMove(x + stepSize, y)) {
                    robot.setX(x + stepSize);
                }
                break;
            default:
                break;
        }
        checkGoalReached();
    }
    public void handleKeyPress(KeyEvent event, Car car) {
        double x = car.getLayoutX();//car.getBoundsInParent().getCenterX();
        double y = car.getLayoutY();//car.getBoundsInParent().getCenterY();

        switch (event.getCode()) {
            case W:
                if (canMove(x, y - stepSize,car)) {
                    car.setRotate(270);
                    car.setLayoutY(y - stepSize);
                }
                break;
            case S:
                if (canMove(x, y + stepSize,car)) {
                    car.setRotate(90);
                    car.setLayoutY(y + stepSize);
                }
                break;
            case A:
                if (canMove(x - stepSize, y,car)) {
                    car.setRotate(0);
                    car.setLayoutX(x - stepSize);
                }
                break;
            case D:
                if (canMove(x + stepSize, y,car)) {
                    car.setRotate(0);
                    car.setLayoutX(x + stepSize);
                }
                break;
            default:
                break;
        }
        checkGoalReached();
    }

    /**
     * Helper method that will check if the robot will hit one of the maze walls
     * @param newX where the new x-coordinate will be
     * @param newY where the new y-coordinate will be
     * @return true if it doesn't hit a wall false if it does
     */
    private boolean canMove(double newX, double newY) {
        PixelReader pixelReader = mazeImage.getPixelReader();

        // Check 4 corners of the robot image for collision detection (Had to edit the dimensions because it wouldn't fit in the maze)
        int topLeftX = (int) newX;
        int topLeftY = (int) newY;
        int bottomRightX = (int) (newX + robotView.getImage().getWidth()/2);
        int bottomRightY = (int) (newY + robotView.getImage().getHeight()/2);

        // Only allow movement if all corners are on non-blocking paths
        return isPathClear(pixelReader, topLeftX, topLeftY) &&
                isPathClear(pixelReader, bottomRightX, topLeftY) &&
                isPathClear(pixelReader, topLeftX, bottomRightY) &&
                isPathClear(pixelReader, bottomRightX, bottomRightY);
    }

    private boolean canMove(double newX, double newY, Car car) {
        PixelReader pixelReader = mazeImage.getPixelReader();

        // Check 4 corners of the robot image for collision detection (Had to edit the dimensions because it wouldn't fit in the maze)
        int topLeftX = (int) newX;
        int topLeftY = (int) newY;
        int bottomRightX = (int) (newX + car.getBoundsInParent().getWidth()/2);
        int bottomRightY = (int) (newY + car.getBoundsInParent().getWidth()/2);

        // Only allow movement if all corners are on non-blocking paths
        return isPathClear(pixelReader, topLeftX, topLeftY) &&
                isPathClear(pixelReader, bottomRightX, topLeftY) &&
                isPathClear(pixelReader, topLeftX, bottomRightY) &&
                isPathClear(pixelReader, bottomRightX, bottomRightY);
    }

    /**
     * Method that checks if the specified pixel coordinates (x, y) are part of a clear path in the maze
     * @param pixelReader the pixel reader
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if the pixel is white, false if otherwise
     */
    private boolean isPathClear(PixelReader pixelReader, int x, int y) {
        try {
            Color color = pixelReader.getColor(x, y);
            return color.equals(Color.WHITE);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Method that checks if the robot was at the Goal position
     */
    private void checkGoalReached() {
        double robotX = robotView.getX();
        double robotY = robotView.getY();

        // Check if the robot is close to the goal coordinates
        if (Math.abs(robotX - RobotApplication.goalX) < 10 && Math.abs(robotY - RobotApplication.goalY) < 10) {
            RobotApplication.showGoalWindow();
        }
    }
}