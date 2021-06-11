package model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.IOException;

public class InfoLabel extends Label {

    private static final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
    private static final String BACKGROUND = "/model/resources/blue_button13.png";

    public InfoLabel(String text) {
        setPrefHeight(49);
        setPrefWidth(380);
        setText(text);
        setWrapText(true);
        setAlignment(Pos.CENTER);

        setLabelFont();
        createBackground();
    }

    private void setLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (IOException e) {
            setFont(new Font("Verdana", 23));
        }
    }

    private void createBackground() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND, 380, 49, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));

    }
}
