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
public class LayerChanger extends StaticObject {
    int north;
    int east;
    int south;
    int west;
    public LayerChanger(String id, double width, double height, Image image, boolean collidable, boolean layerCol) {
        super(id, width, height, image, collidable, layerCol);
    }
    
    public void layerChange(int north, int east, int south, int west) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }
    
    public void changeLayerNorth(GameObject object) {
        if(north != 0)
            object.setLayer(north);
    }
    
    public void changeLayerEast(GameObject object) {
        if(east!= 0)
            object.setLayer(east);
    }
    public void changeLayerSouth(GameObject object) {
        if(south != 0)
            object.setLayer(south);
    }
            
     public void changeLayerWest(GameObject object) {
        if(west != 0)
            object.setLayer(west);
    }
     
     public int getNorth() {
         return north;
     }
     
     public int getEast() {
         return east;
     }
     
     public int getSouth() {
         return south;
     }
     
     public int getWest() {
         return west;
     }
    
    
    
}
