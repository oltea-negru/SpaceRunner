package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class ShipChooser extends VBox {

    private ImageView shipImage;
    private ImageView circleImage;

    private String circleChosen = "model/resources/tick/grey_boxTick.png";
    private String emptyCircle = "model/resources/tick/grey_circle.png";

    private SHIP ship;

    private boolean isChosen;

    public ShipChooser(SHIP ship) {
        circleImage = new ImageView(emptyCircle);
        shipImage = new ImageView(ship.getUrl());
        this.ship = ship;
        isChosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().addAll(shipImage, circleImage);
    }


    public SHIP getShip() {
        return ship;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setIsChosen(boolean value) {
        isChosen = value;
        String newImage = isChosen ? circleChosen : emptyCircle;
        circleImage.setImage(new Image(newImage));
    }
}
