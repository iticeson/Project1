import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Project extends Application {

    private GamePane[][] gamePanes = new GamePane[4][4];
    private Label label;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        root.setPrefSize(600, 600);

        // add label to top
        label = new Label("The Text Field");
        root.setTop(label);

        // add GamePanes to center
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                GamePane gamePane = new GamePane();
                gamePanes[i][j] = gamePane;
                gridPane.add(gamePane, j, i);
                
            // check if moves are possible
            //checkMoves();

            // hide non-functional buttons
            if (!gamePane.isTopButtonVisible()) {
                gamePane.getTopButton().setVisible(false);
            }
            if (!gamePane.isLeftButtonVisible()) {
                gamePane.getLeftButton().setVisible(false);
            }
            if (!gamePane.isRightButtonVisible()) {
                gamePane.getRightButton().setVisible(false);
            }
            if (!gamePane.isBottomButtonVisible()) {
                gamePane.getBottomButton().setVisible(false);
            }
            }
        }
    // Set the 0,2 ball to be inactive on start
    gamePanes[2][0].setBallVisible(false);

        root.setCenter(gridPane);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
private void click(String direction, int x, int y) {
    GamePane currentPane = gamePanes[y][x];
    switch (direction) {
        case "up":
            if (y < 2 && gamePanes[y+1][x].isBallVisible() && !gamePanes[y+2][x].isBallVisible()) {
                currentPane.setBallVisible(false);
                gamePanes[y+2][x].setBallVisible(true);
                gamePanes[y+1][x].setBallVisible(false);
            }
            break;
        case "down":
            if (y > 1 && gamePanes[y-1][x].isBallVisible() && !gamePanes[y-2][x].isBallVisible()) {
                currentPane.setBallVisible(false);
                gamePanes[y-2][x].setBallVisible(true);
                gamePanes[y-1][x].setBallVisible(false);
            }
            break;
        case "left":
            if (x < 2 && gamePanes[y][x+1].isBallVisible() && !gamePanes[y][x+2].isBallVisible()) {
                currentPane.setBallVisible(false);
                gamePanes[y][x+2].setBallVisible(true);
                gamePanes[y][x+1].setBallVisible(false);
            }
            break;
        case "right":
            if (x > 1 && gamePanes[y][x-1].isBallVisible() && !gamePanes[y][x-2].isBallVisible()) {
                currentPane.setBallVisible(false);
                gamePanes[y][x-2].setBallVisible(true);
                gamePanes[y][x-1].setBallVisible(false);
            }
            break;
    }
}
private void checkMoves() {
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            GamePane gamePane = gamePanes[i][j];

            // check if top move is possible
            if (i < 2 && !gamePanes[i+2][j].isBallVisible() && gamePanes[i+1][j].isBallVisible()) {
                gamePane.setTopButtonVisible(true);
            } else {
                gamePane.setTopButtonVisible(false);
            }

            // check if left move is possible
            if (j < 2 && !gamePanes[i][j+2].isBallVisible() && gamePanes[i][j+1].isBallVisible()) {
                gamePane.setLeftButtonVisible(true);
            } else {
                gamePane.setLeftButtonVisible(false);
            }

            // check if right move is possible
            if (j > 1 && !gamePanes[i][j-2].isBallVisible() && gamePanes[i][j-1].isBallVisible()) {
                gamePane.setRightButtonVisible(true);
            } else {
                gamePane.setRightButtonVisible(false);
            }


            // check if bottom move is possible
            if (i > 1 && !gamePanes[i-2][j].isBallVisible() && gamePanes[i-1][j].isBallVisible()) {
                gamePane.setBottomButtonVisible(true);
            } else {
                gamePane.setBottomButtonVisible(false);
            }
        }
    }
}


  /* private void checkMoves() {
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            GamePane gamePane = gamePanes[i][j];
            // check if top move is possible
            if (j < 2 && !gamePanes[i][j+2].isVisible() && gamePanes[i][j+1].isVisible()) {
                gamePane.setTopButtonVisible(true);
            } else {
                gamePane.setTopButtonVisible(false);
            }
            
            // check if left move is possible
            if (j < 2 && !gamePanes[i+2][j].isVisible() && gamePanes[i+1][j].isVisible()) {
                gamePane.setLeftButtonVisible(true);
            } else {
                gamePane.setLeftButtonVisible(false);
            }
            
            // check if right move is possible
            if (j > 1 && gamePanes[i-2][j].isVisible() && gamePanes[i+1][j].isVisible()) {
                gamePane.setRightButtonVisible(true);
            } else {
                gamePane.setRightButtonVisible(false);
            }
            
            // check if bottom move is possible
            if (i > 1 && !gamePanes[i][j-2].isVisible() && gamePanes[i][j-1].isVisible()) {
                gamePane.setBottomButtonVisible(true);
            } else {
                gamePane.setBottomButtonVisible(false);
            }
        }
        
    }
}*/


    class GamePane extends GridPane {

        private Button topButton;
        private Button leftButton;
        private Button rightButton;
        private Button bottomButton;
        private Canvas canvas;

        private Color color = Color.BLACK;
        private boolean ballVisible = true;
        private boolean topButtonVisible = true;
        private boolean leftButtonVisible = true;
        private boolean rightButtonVisible = true;
        private boolean bottomButtonVisible = true;

        public GamePane() {
            topButton = new Button();
            topButton.setPrefSize(80, 20);
            topButton.setOnAction(new ButtonHandler());
            add(topButton, 1, 0);

            leftButton = new Button();
            leftButton.setPrefSize(20, 80);
            leftButton.setOnAction(new ButtonHandler());
            add(leftButton, 0, 1);

            rightButton = new Button();
            rightButton.setPrefSize(20, 80);
            rightButton.setOnAction(new ButtonHandler());
            add(rightButton, 2, 1);

            bottomButton = new Button();
            bottomButton.setPrefSize(80, 20);
            bottomButton.setOnAction(new ButtonHandler());
            add(bottomButton, 1, 2);

            canvas = new Canvas(80, 80);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            draw();
            gc.setFill(color);
            gc.fillOval(0, 0, 80, 80);

            add(canvas, 1, 1);
        }
    
    public Button getTopButton() {
        return topButton;
    }

    public Button getBottomButton() {
        return bottomButton;
    }

    public Button getLeftButton() {
        return leftButton;
    }

    public Button getRightButton() {
        return rightButton;
    }
        
    public void setBallVisible(boolean visible) {
        ballVisible = visible;
        draw();
    }
    
    public boolean isBallVisible() {
        return ballVisible;
    }

    public boolean isTopButtonVisible() {
        return topButtonVisible;
    }

    public void setTopButtonVisible(boolean visible) {
        topButtonVisible = visible;
        draw();
    }

    public boolean isLeftButtonVisible() {
        return leftButtonVisible;
    }

    public void setLeftButtonVisible(boolean visible) {
        leftButtonVisible = visible;
        draw();
    }

    public boolean isRightButtonVisible() {
        return rightButtonVisible;
    }

    public void setRightButtonVisible(boolean visible) {
        rightButtonVisible=visible;
        draw();
    }
    
    public boolean isBottomButtonVisible() {
        return bottomButtonVisible;
    }
    
    public void setBottomButtonVisible(boolean visible) {
        bottomButtonVisible=visible;
        draw();
    }
    
    public void draw() {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    
    // clear the canvas
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    
    if (ballVisible) {
        // draw the circle
        gc.setFill(color);
        gc.fillOval(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    
    // show or hide each button based on its boolean variable
    topButton.setVisible(topButtonVisible);
    leftButton.setVisible(leftButtonVisible);
    rightButton.setVisible(rightButtonVisible);
    bottomButton.setVisible(bottomButtonVisible);
}

class ButtonHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();
        int x = GridPane.getColumnIndex(button.getParent());
        int y = GridPane.getRowIndex(button.getParent());
        if (button == gamePanes[y][x].getTopButton()) {
            click("up", x, y);
            checkMoves();
        } else if (button == gamePanes[y][x].getLeftButton()) {
            click("left", x, y);
            checkMoves();
        } else if (button == gamePanes[y][x].getRightButton()) {
            click("right", x, y);
            checkMoves();
        } else if (button == gamePanes[y][x].getBottomButton()) {
            click("down", x, y);
            checkMoves();
        }
       }
      }
    }
}

