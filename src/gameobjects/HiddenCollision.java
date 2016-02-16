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
public class HiddenCollision extends StaticObject{

    
    public HiddenCollision(String id, double width, double height, Image image, boolean collidable, boolean layerCollision) {
        super(id, width, height, image, collidable, layerCollision);
    }
    
}
