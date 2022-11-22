package model;

public enum SHIP {
    BLUE("model/resources/ships/playerShip3_blue.png", "model/resources/ships/playerLife3_blue.png"),
    RED("model/resources/ships/playerShip3_red.png", "model/resources/ships/playerLife3_red.png"),
    GREEN("model/resources/ships/playerShip3_green.png","model/resources/ships/playerLife3_green.png"),
    ORANGE("model/resources/ships/playerShip3_orange.png","model/resources/ships/playerLife3_orange.png");

   private String url;
   private String urlLife;

    SHIP(String urlShip, String life) {
        url=urlShip;
        urlLife=life;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlLife() {
        return urlLife;
    }
}
