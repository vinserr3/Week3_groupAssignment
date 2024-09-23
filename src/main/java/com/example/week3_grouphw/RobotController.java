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
    public void handleKeyPress(KeyEvent event) {
        double x = robotView.getX();
        double y = robotView.getY();

        switch (event.getCode()) {
            case UP:
                if (canMove(x, y - stepSize)) {
                    robotView.setY(y - stepSize);
                }
                break;
            case DOWN:
                if (canMove(x, y + stepSize)) {
                    robotView.setY(y + stepSize);
                }
                break;
            case LEFT:
                if (canMove(x - stepSize, y)) {
                    robotView.setX(x - stepSize);
                }
                break;
            case RIGHT:
                if (canMove(x + stepSize, y)) {
                    robotView.setX(x + stepSize);
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