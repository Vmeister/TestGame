/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobjects;

import javafx.scene.image.Image;

/**
 *
 * @author Ville
 */
public class StaticObject extends GameObject {
    Image graphic;
    int layer;
    double uncollidableHeight;
    double uncollidableWidth;
    
    public StaticObject(String id, double width, double height, Image image, boolean collidable, boolean layerCol) {
        super(id, width, height, image, collidable, layerCol);
        this.graphic = image;
        createGraphic();
    }

    private void createGraphic() {
       this.setImage(image);
    }
}
