package com.example.week3_grouphw;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RobotApplication extends Application {
    static final double goalX=579; //Goal x-coordinate
    static final double goalY=245; //Goal y-coordinate

    @Override
    public void start(Stage primaryStage) throws Exception {

        TabPane tabPane = new TabPane();
        tabPane.setFocusTraversable(false); //This isn't working for some reason

        Tab maze1Tab = new Tab("Maze 1");
        maze1Tab.setClosable(false);
        // Load the maze image
        Image mazeImage = new Image("maze.png"); // Path to the maze image
        ImageView mazeView = new ImageView(mazeImage);

        // Load the robot image
        Image robotImage = new Image("robot.png"); // Path to the robot image
        ImageView robotView = new ImageView(robotImage);
        robotView.setX(14); // Initial X position
        robotView.setY(260); // Initial Y position
        // Create a pane for the layout
        Pane root = new Pane();
        root.getChildren().addAll(mazeView, robotView);

        Button startButton = new Button("Solve Maze");
        startButton.setLayoutY(mazeImage.getHeight() + 10);
        startButton.setLayoutX(10);
        startButton.setOnAction(e -> autoMoveDroidMaze1(robotView));
        startButton.setFocusTraversable(false); //When the button was selectable, it would stop the arrow keys from working
        root.getChildren().add(startButton);

        // Handle keyboard input
        RobotController robotController = new RobotController(robotView, mazeImage);
        root.setOnKeyPressed(e -> robotController.handleKeyPress(e)); //Was previously scene.setOnKeyPressed(e -> robotController.handleKeyPress(e));
        maze1Tab.setContent(root);

        Tab maze2Tab = new Tab("Maze 2");
        maze2Tab.setClosable(false);
        Pane root2 = new Pane();

        // Load the second maze image
        Image maze2Image = new Image("maze2.png");
        ImageView maze2View = new ImageView(maze2Image);

        // Add components to Pane for Maze 2
        root2.getChildren().addAll(maze2View);

        // Add Solve button for Maze 2
        Button solveMaze2Button = new Button("Solve Maze");
        solveMaze2Button.setLayoutY(maze2Image.getHeight() + 10);
        solveMaze2Button.setLayoutX(10);
        solveMaze2Button.setOnAction(e -> autoMoveDroidMaze1(robotView));
        root2.getChildren().add(solveMaze2Button);

        maze2Tab.setContent(root2);

        // Add both tabs to the TabPane
        tabPane.getTabs().addAll(maze1Tab,maze2Tab);


        Scene scene = new Scene(tabPane, 800, 600); // Adjust scene size as necessary
        // Set the scene and show the stage
        primaryStage.setTitle("Maze Robot");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Displays an alert that says that you reached the goal
     */
    public static void showGoalWindow() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Goal Reached");
        alert.setHeaderText(null);
        alert.setContentText("You reached the goal!");
        alert.showAndWait();
    }

    /**
     * Method to automatically move the robot to the finish
     * @param robotView The robot
     */
    private void autoMoveDroidMaze1(ImageView robotView) {
        // Create a series of movements as TranslateTransitions
        TranslateTransition step1 = new TranslateTransition(Duration.seconds(1), robotView);
        step1.setByX(25);
        TranslateTransition step2 = new TranslateTransition(Duration.seconds(1), robotView);
        step2.setByY(-110);
        TranslateTransition step3 = new TranslateTransition(Duration.seconds(1), robotView);
        step3.setByX(225);
        TranslateTransition step4 = new TranslateTransition(Duration.seconds(1), robotView);
        step4.setByY(-60);
        TranslateTransition step5 = new TranslateTransition(Duration.seconds(1), robotView);
        step5.setByX(55);
        TranslateTransition step6 = new TranslateTransition(Duration.seconds(1), robotView);
        step6.setByY(225);
        TranslateTransition step7 = new TranslateTransition(Duration.seconds(1), robotView);
        step7.setByX(60);
        TranslateTransition step8 = new TranslateTransition(Duration.seconds(1), robotView);
        step8.setByY(-105);
        TranslateTransition step9 = new TranslateTransition(Duration.seconds(1), robotView);
        step9.setByX(110);
        TranslateTransition step10 = new TranslateTransition(Duration.seconds(1), robotView);
        step10.setByY(-115);
        TranslateTransition step11 = new TranslateTransition(Duration.seconds(1), robotView);
        step11.setByX(60);
        TranslateTransition step12 = new TranslateTransition(Duration.seconds(1), robotView);
        step12.setByY(145);
        TranslateTransition step13 = new TranslateTransition(Duration.seconds(1), robotView);
        step13.setByX(25);
        // Combine all movements into a sequence
        SequentialTransition sequence = new SequentialTransition(step1, step2,step3, step4, step5, step6, step7, step8, step9,
                step10, step11, step12, step13);
        sequence.play();
    }
}
