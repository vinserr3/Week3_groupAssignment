package com.example.week3_grouphw;

import javafx.animation.AnimationTimer;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RobotApplication extends Application {
    static final double goalX=579; //Goal x-coordinate
    static final double goalY=245; //Goal y-coordinate

    @Override
    public void start(Stage primaryStage) throws Exception {

        TabPane tabPane = new TabPane();

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
        robotView.setVisible(true);//Visible by default
        //Load car
        Car car1 = new Car();
        car1.setLayoutX(14); //Initial X position
        car1.setLayoutY(260);//Initial Y position
        car1.setVisible(false);//Not visible by default
        // Create a pane for the layout
        Pane root = new Pane();
        root.getChildren().addAll(mazeView, robotView,car1); //adds nodes to pane

        //Button to start maze solving animation
        Button startButton = new Button("Solve Maze");
        startButton.setLayoutY(mazeImage.getHeight() + 10);
        startButton.setLayoutX(10);
        startButton.setFocusTraversable(false); //When the button was selectable, it would stop the arrow keys from working
        root.getChildren().add(startButton);

        //Start of maze 2 tab
        Tab maze2Tab = new Tab("Maze 2");
        maze2Tab.setClosable(false);
        Pane root2 = new Pane(); //second pane for second tab

        // Load the second maze image
        Image maze2Image = new Image("maze2.png");
        ImageView maze2View = new ImageView(maze2Image);

        //load second robot
        Image droid2 = new Image("robot.png");
        ImageView robot2View = new ImageView(droid2);
        robot2View.setX(14);
        robot2View.setY(250);

        //load second car
        Car car2 = new Car();
        car2.setLayoutX(14);
        car2.setLayoutY(250);
        car2.setVisible(false);

        // Add components to Pane for Maze 2
        root2.getChildren().addAll(maze2View, robot2View, car2);

        // Add Solve button for Maze 2
        Button solveMaze2Button = new Button("Solve Maze");
        solveMaze2Button.setLayoutY(maze2Image.getHeight() + 10);
        solveMaze2Button.setLayoutX(10);
        solveMaze2Button.setOnAction(e -> autoMoveDroidMaze2(robot2View));
        root2.getChildren().add(solveMaze2Button);

        //Buttons to switch cars and robots
        Button switchCar1 = new Button("Car");
        switchCar1.setLayoutX(90);
        switchCar1.setLayoutY(mazeImage.getHeight() + 10);
        switchCar1.setOnAction(e -> setCarsVisible(car1,robotView)); //Sets car visible and robot not visible
        Button switchRobot1 = new Button("Robot");
        switchRobot1.setLayoutX(127);
        switchRobot1.setLayoutY(mazeImage.getHeight()+10);
        switchRobot1.setOnAction(e -> setRobotsVisible(car1,robotView));//Sets robot visible and car not visible
        root.getChildren().addAll(switchCar1, switchRobot1); //adds buttons to Pane

        //For second tab
        Button switchCar2 = new Button("Car");
        switchCar2.setLayoutX(90);
        switchCar2.setLayoutY(maze2Image.getHeight() + 10);
        switchCar2.setOnAction(e -> setCarsVisible(car2,robot2View));
        Button switchRobot2 = new Button("Robot");
        switchRobot2.setLayoutX(127);
        switchRobot2.setLayoutY(maze2Image.getHeight()+10);
        switchRobot2.setOnAction(e -> setRobotsVisible(car2,robot2View));
        root2.getChildren().addAll(switchCar2, switchRobot2);

        //Sets each pane to each tab
        maze1Tab.setContent(root);
        maze2Tab.setContent(root2);

        // Add both tabs to the TabPane
        tabPane.getTabs().addAll(maze1Tab, maze2Tab);
        tabPane.setFocusTraversable(false);

        Scene scene = new Scene(tabPane);

        //Handle key presses for first maze
        RobotController robotController = new RobotController(robotView, mazeImage);
        RobotController robot2Controller = new RobotController(robot2View, maze2Image);
        AnimationTimer mazeTimer = new AnimationTimer() { //Timer checks which object to move
            @Override
            public void handle(long l) {
                if (maze1Tab.isSelected() && robotView.isVisible()){
                    scene.setOnKeyPressed(e -> robotController.handleKeyPress(e, robotView));
                    startButton.setOnAction(e -> autoMoveDroidMaze1(robotView));
                } else if (maze2Tab.isSelected()&&robot2View.isVisible()) {
                    scene.setOnKeyPressed(e->robot2Controller.handleKeyPress(e,robot2View));
                } else if (maze1Tab.isSelected() && car1.isVisible()) {
                    scene.setOnKeyPressed(e->robotController.handleKeyPress(e,car1));
                } else if (maze2Tab.isSelected() && car2.isVisible()) {
                    scene.setOnKeyPressed(e->robot2Controller.handleKeyPress(e,car2));
                }
            }
        };
        mazeTimer.start();

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
        robotView.setX(14);
        robotView.setY(260);
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
        TranslateTransition step14 = new TranslateTransition(Duration.seconds(1), robotView);
        step14.setByX(-560);
        step14.setByY(18);
        // Combine all movements into a sequence
        SequentialTransition sequence = new SequentialTransition(step1, step2,step3, step4, step5, step6, step7, step8, step9,
                step10, step11, step12, step13, step14);
        sequence.play();
    }

    private void autoMoveDroidMaze2(ImageView robotView){
        robotView.setX(23);
        robotView.setY(20);
        TranslateTransition step1 = new TranslateTransition(Duration.seconds(1), robotView);
        step1.setByY(300);
        TranslateTransition step2 = new TranslateTransition(Duration.seconds(1), robotView);
        step2.setByX(165);
        TranslateTransition step3 = new TranslateTransition(Duration.seconds(1), robotView);
        step3.setByY(-150);
        TranslateTransition step4 = new TranslateTransition(Duration.seconds(1), robotView);
        step4.setByX(130);
        TranslateTransition step5 = new TranslateTransition(Duration.seconds(1), robotView);
        step5.setByY(-145);
        TranslateTransition step6 = new TranslateTransition(Duration.seconds(1), robotView);
        step6.setByX(120);
        TranslateTransition step7 = new TranslateTransition(Duration.seconds(1), robotView);
        step7.setByY(290);
        TranslateTransition step8 = new TranslateTransition(Duration.seconds(1), robotView);
        step8.setByX(-415);
        step8.setByY(-290);
        SequentialTransition sequence = new SequentialTransition(step1,step2, step3, step4, step5, step6, step7, step8);
        sequence.play();
    }

    /**
     * @param car to set car visible
     * @param robot to set robot invisible
     */
    private void setCarsVisible(Car car,ImageView robot){
        car.setVisible(true);
        robot.setVisible(false);
    }

    /**
     * @param car to set car invisible
     * @param robot to set robot visible
     */
    private void setRobotsVisible(Car car, ImageView robot){
        car.setVisible(false);
        robot.setVisible(true);
    }
}

/**
 * Car class creates a car object
 */
class Car extends Group{
    public Car(){
        Polygon car = new Polygon();
        car.getPoints().addAll(new Double[]{
                1.0, 16.0,
                4.5, 2.0,
                15.5, 0.5,
                16.5, 5.5,
                23.5, 7.5,
                24., 10.5,
                25., 16.0});
        car.setFill(Color.PURPLE);
        Rectangle window1 = new Rectangle(6.5,4,2,4.5);
        Rectangle window2 = new Rectangle(10,4.5,4.25,4);
        Circle wheel1 = new Circle(6,17,3);
        Circle wheel2 = new Circle(19.5,17,3);
        window1.setFill(Color.LIGHTGREEN);
        window2.setFill(Color.LIGHTGREEN);

        getChildren().addAll(car, window1,window2,wheel1,wheel2);
    }
}
