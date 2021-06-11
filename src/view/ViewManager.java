package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class ViewManager {

    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private static final int HEIGHT = 700;
    private static final int WIDTH = 1024;

    private static final int MENU_BUTTONS_START_X = 100;
    private static final int MENU_BUTTONS_START_Y = 150;

    SpaceRunnerSubscene creditsScene;
    SpaceRunnerSubscene helpScene;
    SpaceRunnerSubscene scoresScene;
    SpaceRunnerSubscene shipScene;
    SpaceRunnerSubscene sceneToHide;

    List<ShipChooser> shipList;
    List<SpaceRunnerButton> menuButtons;

    private SHIP chosenSHIP;


    public ViewManager() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubscenes();
        createButtons();
        createBackground();
        createLogo();
    }

    private void createSubscenes() {
        creditsScene = new SpaceRunnerSubscene();
        helpScene = new SpaceRunnerSubscene();
        scoresScene = new SpaceRunnerSubscene();
        mainPane.getChildren().addAll(creditsScene, helpScene, scoresScene);

        createShipChooserSubscene();
    }

    private void createShipChooserSubscene() {
        shipScene = new SpaceRunnerSubscene();
        mainPane.getChildren().add(shipScene);

        InfoLabel option = new InfoLabel("CHOOSE YOUR SHIP");
        option.setLayoutX(110);
        option.setLayoutY(25);

        shipScene.getPane().getChildren().add(option);
        shipScene.getPane().getChildren().add(createShipVariety());
        shipScene.getPane().getChildren().add(createPlayButton());
    }

    private HBox createShipVariety() {
        var hbox = new HBox(20);
        shipList = new ArrayList<>();
        for (SHIP ship : SHIP.values()) {
            var chosen = new ShipChooser(ship);
            shipList.add(chosen);
            hbox.getChildren().add(chosen);
            chosen.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (ShipChooser shipChooser : shipList)
                        shipChooser.setIsChosen(false);
                    chosen.setIsChosen(true);
                    chosenSHIP=chosen.getShip();
                }
            });
        }
        hbox.setLayoutX(300-118*2);
        hbox.setLayoutY(100);
        return hbox;
    }

    private void showSubscene(SpaceRunnerSubscene subscene) {
        if (sceneToHide != null)
            sceneToHide.moveSubScene();
        sceneToHide = subscene;
        subscene.moveSubScene();

    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButtons(SpaceRunnerButton button) {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createButtons() {
        createStartButton();
        createScoresButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }

    private SpaceRunnerButton createPlayButton(){
        SpaceRunnerButton play= new SpaceRunnerButton("PLAY");
        play.setLayoutX(350);
        play.setLayoutY(300);

        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(chosenSHIP!=null){
                    GameViewManager gameViewManager= new GameViewManager();
                    gameViewManager.createGame(mainStage,chosenSHIP);
                }
            }
        });

        return play;
    }

    private void createStartButton() {
        SpaceRunnerButton start = new SpaceRunnerButton("START");
        addMenuButtons(start);

        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubscene(shipScene);
            }
        });
    }

    private void createScoresButton() {
        SpaceRunnerButton scores = new SpaceRunnerButton("SCORES");
        addMenuButtons(scores);

        scores.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubscene(scoresScene);
            }
        });
    }

    private void createHelpButton() {
        SpaceRunnerButton help = new SpaceRunnerButton("HELP");
        addMenuButtons(help);

        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubscene(helpScene);
            }
        });
    }

    private void createCreditsButton() {
        SpaceRunnerButton credits = new SpaceRunnerButton("CREDITS");
        addMenuButtons(credits);

        credits.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubscene(creditsScene);
            }
        });
    }

    private void createExitButton() {
        SpaceRunnerButton exit = new SpaceRunnerButton("EXIT");
        addMenuButtons(exit);

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }

    private void createBackground() {
        Image backgroundImage = new Image("model/resources/blue.png", 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }

    private void createLogo() {
        ImageView logo = new ImageView("/model/resources/logo_small.png");
        logo.setLayoutX(400);
        logo.setLayoutY(50);

        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(new DropShadow());
            }
        });

        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(null);
            }
        });

        mainPane.getChildren().add(logo);
    }
}
