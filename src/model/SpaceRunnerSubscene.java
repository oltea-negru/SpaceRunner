package model;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class SpaceRunnerSubscene extends SubScene {

    private final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
    private final String BACKGROUND = "/model/resources/blue_panel.png";

    private boolean isHidden;

    public SpaceRunnerSubscene() {
        super(new AnchorPane(), 600, 400);
        prefHeight(400);
        prefWidth(600);
        isHidden = true;
        BackgroundImage background = new BackgroundImage(new Image(BACKGROUND, 600, 400, false, true), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(background));

        setLayoutX(1024);
        setLayoutY(200);
    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(new Duration(300));
        transition.setNode(this);

        if (isHidden) {
            transition.setToX(-676);
            isHidden = false;
        } else {
            isHidden = true;
            transition.setToX(0);
        }

        transition.play();
    }

    public AnchorPane getPane(){
        return (AnchorPane) this.getRoot();
    }

}
