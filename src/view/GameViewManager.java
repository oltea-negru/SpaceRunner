package view;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.SHIP;
import model.SmallInfoLabel;

import java.util.Random;


public class GameViewManager {

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 800;

    private ImageView ship;
    private Stage menuStage;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private int angle;
    private AnimationTimer timer;

    private GridPane gridPane1;
    private GridPane gridPane2;

    private String BACKGROUND="model/resources/blue.png";

    private static final String BROWN_METEOR="model/resources/rocks/small_Brown.png";
    private static final String GREY_METEOR="model/resources/rocks/small_Grey.png";

    private ImageView[] brownMeteors;
    private ImageView[] greyMeteors;

    private ImageView star;
    private ImageView[] playerLives;
    private SmallInfoLabel pointsLabel;
    private int playerLife;
    private int points;
    private final static String STAR="/model/resources/star3.png";

    private final static int STAR_RADIUS=12;
    private final static int SHIP_RADIUS=27;
    private final static int METEOR_RADIUS=20;

    Random randomPosition;



    public GameViewManager() {
        initialiseGame();
        createKeyListeners();
        randomPosition= new Random();
    }

    private void createKeyListeners() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = true;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = true;
                }
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = false;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = false;
                }
            }
        });
    }

    private void initialiseGame() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, WIDTH, HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    public void createGame(Stage menuStage, SHIP chosenShip) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        createBackground();
        createElements(chosenShip);
        createShip(chosenShip);
        createGameLoop();
        gameStage.show();
    }

    private void createShip(SHIP chosenShip) {
        ship = new ImageView(chosenShip.getUrl());
        ship.setLayoutX(WIDTH / 2);
        ship.setLayoutY(HEIGHT - 90);
        gamePane.getChildren().add(ship);
    }

    private void createGameLoop() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                moveBackground();
                moveElements();
                checkElementsPosition();
                collision();
                    moveShip();
            }
        };
        timer.start();
    }

    private void moveShip() {
        if (isLeftKeyPressed && isRightKeyPressed) {
            if(angle<0)
                angle+=5;
            else if(angle>0)
                angle-=5;
            ship.setRotate(angle);
        } else if (!isLeftKeyPressed && isRightKeyPressed) {
            if(angle<30){
                angle += 5;}
            ship.setRotate(angle);
            if(ship.getLayoutX()<522){
                ship.setLayoutX(ship.getLayoutX()+3);
            }
        } else if (isLeftKeyPressed) {
            if(angle>-30){
            angle -= 5;}
            ship.setRotate(angle);
            if(ship.getLayoutX()>-20){
                ship.setLayoutX(ship.getLayoutX()-3);
            }
        } else {
            if(angle<0)
                angle+=5;
            else if(angle>0)
                angle-=5;
            ship.setRotate(angle);
        }
    }

    private void createBackground(){
        gridPane1= new GridPane();
        gridPane2= new GridPane();

        for(int i=0; i<12;i++){
            ImageView backgroundImage1= new ImageView(BACKGROUND);
            ImageView backgroundImage2= new ImageView(BACKGROUND);
            GridPane.setConstraints(backgroundImage1, i%3, i/3);
            GridPane.setConstraints(backgroundImage2, i%3, i/3);
            gridPane1.getChildren().add(backgroundImage1);
            gridPane2.getChildren().add(backgroundImage2);
        }

        gridPane2.setLayoutY(-1024);

        gamePane.getChildren().addAll(gridPane1,gridPane2);
    }

    private void moveBackground(){
        gridPane1.setLayoutY(gridPane1.getLayoutY()+0.5);
        gridPane2.setLayoutY(gridPane2.getLayoutY()+0.5);

        if(gridPane1.getLayoutY()>1023)
            gridPane1.setLayoutY(-1024);
        if(gridPane2.getLayoutY()>1023)
            gridPane2.setLayoutY(-1024);
    }

    private void createElements(SHIP chosenShip){
        playerLife =2;

        star=new ImageView(STAR);
        setNewPosition(star);
        gamePane.getChildren().add(star);

        pointsLabel= new SmallInfoLabel("POINTS: 00");
        pointsLabel.setLayoutX(460);
        pointsLabel.setLayoutY(20);
        gamePane.getChildren().add(pointsLabel);

        playerLives= new ImageView[3];
        for(int i=0; i<3; i++) {
            playerLives[i] = new ImageView(chosenShip.getUrlLife());
            playerLives[i].setLayoutX(455+(i*50));
            playerLives[i].setLayoutY(80);
            gamePane.getChildren().add(playerLives[i]);
        }

        brownMeteors= new ImageView[4];
        for(int i=0; i<4; i++){
            brownMeteors[i]=new ImageView(BROWN_METEOR);
            setNewPosition(brownMeteors[i]);
        gamePane.getChildren().add(brownMeteors[i]);
        }

        greyMeteors= new ImageView[4];
        for(int i=0; i<4; i++){
            greyMeteors[i]=new ImageView(GREY_METEOR);
            setNewPosition(greyMeteors[i]);
            gamePane.getChildren().add(greyMeteors[i]);
        }
    }

    private void setNewPosition(ImageView imageView){
        imageView.setLayoutX(randomPosition.nextInt(370));
        imageView.setLayoutY(-(randomPosition.nextInt(3200)+600));
    }

    private void moveElements(){
        star.setLayoutY(star.getLayoutY()+5);

        for(int i=0; i< brownMeteors.length;i++){
            brownMeteors[i].setLayoutY(brownMeteors[i].getLayoutY()+7);
            brownMeteors[i].setRotate(brownMeteors[i].getRotate()+4);
        }

        for(int i=0; i< greyMeteors.length;i++){
            greyMeteors[i].setLayoutY(greyMeteors[i].getLayoutY()+7);
            greyMeteors[i].setRotate(greyMeteors[i].getRotate()+4);
        }
    }

    private void checkElementsPosition(){
        if(star.getLayoutY()>1200)
            setNewPosition(star);

        for(int i=0; i<brownMeteors.length; i++)
        {
            if(brownMeteors[i].getLayoutY()>900)
                setNewPosition(brownMeteors[i]);
        }

        for(int i=0; i<greyMeteors.length; i++)
        {
            if(greyMeteors[i].getLayoutY()>900)
                setNewPosition(greyMeteors[i]);
        }
    }

    private void collision(){
        if(SHIP_RADIUS+STAR_RADIUS>getDistance(ship.getLayoutX()+49,star.getLayoutX()+15,
                ship.getLayoutY()+37, star.getLayoutY()+15))
        {
            setNewPosition(star);
            points++;
            String textToSet="POINTS: ";
            if(points<10){
                textToSet=textToSet+"0";
            }
            pointsLabel.setText(textToSet+points);
        }

        for(int i=0; i< brownMeteors.length; i++){
            if(METEOR_RADIUS+STAR_RADIUS>getDistance(ship.getLayoutX()+49,brownMeteors[i].getLayoutX()+20,
                    ship.getLayoutY()+37, brownMeteors[i].getLayoutY()+20)){
                removeLife();
                setNewPosition(brownMeteors[i]);
            }
        }

        for(int i=0; i< greyMeteors.length; i++){
            if(METEOR_RADIUS+STAR_RADIUS>getDistance(ship.getLayoutX()+49,greyMeteors[i].getLayoutX()+20,
                    ship.getLayoutY()+37, greyMeteors[i].getLayoutY()+20)){
                removeLife();
                setNewPosition(greyMeteors[i]);
            }
        }
    }

    private void removeLife(){
        gamePane.getChildren().remove(playerLives[playerLife]);
        playerLife--;
        if(playerLife<0){
            gameStage.close();
            timer.stop();
            menuStage.show();
        }
    }

    private double getDistance(double x1, double x2, double y1, double y2){
        return  Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1-y2),2));
    }
}
