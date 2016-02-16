/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobjects;

import java.util.Comparator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Ville
 */
public class GameObject extends ImageView {
    boolean collidable;
    boolean layerCollision;
    double height;
    double width;
    Image image;
    int layer;
    Double uncollidableStartX;
    Double uncollidableStartY;
    Double uncollidableEndX;
    Double uncollidableEndY;
    boolean collisionSet = false;
    
    public GameObject(String id, double width, double height, Image image, boolean collidable, boolean layerCollision) {
        this.setId(id);
        this.collidable = collidable;
        this.layerCollision = layerCollision;
        this.height = height;
        this.width = width;
        this.image = image;
    }
    
    public boolean allowCollision(GameObject object) {
        if(object.getLayer() != this.layer) {
            if(layerCollision)
                return true;
        }
        else if(object.getLayer() == this.layer) {
            if(collidable)
                return true;
        }
        return false;
    }
    
    public double[] getStartPos() {
        double[] startPos = new double[2];
        startPos[0] = this.getLayoutX();
        startPos[1] = this.getLayoutY();
        return startPos;
    }
    
    public double[] getEndPos() {
        double[] endPos = new double[2];
        endPos[0] = this.getLayoutX() + width;
        endPos[1] = this.getLayoutY() + height;
        return endPos;
    }
    
    public void setLayer(int layer) {
        this.layer = layer;
    }
    
    public int getLayer() {
        return layer;
    }

    boolean allowLayerCollision(GameObject movingObject) {
        return layerCollision;
    }
   
    public double[] getCollisionStart() {
        double[] collisionStart = new double[2];
        collisionStart[0] = this.getLayoutX() + uncollidableStartX;
        collisionStart[1] = this.getLayoutY() + uncollidableStartY;
        return collisionStart;
    }
    
    public double[] getCollisionEnd() {
        double[] collisionEnd = new double[2];
        collisionEnd[0] = this.getLayoutX() + uncollidableEndX;
        collisionEnd[1] = this.getLayoutY() + uncollidableEndY;
        return collisionEnd;
    }
    
    public void setCollisionArea(double startX, double startY,
        double endX, double endY) {
            uncollidableStartX = startX;
            uncollidableStartY = startY;
            uncollidableEndX = endX;
            uncollidableEndY = endY;
            collisionSet = true;
    }
    
    public Double getCollisionStartX() {
        return this.getLayoutX() + uncollidableStartX;
    }
    
    public Double getCollisionStartY() {
        return this.getLayoutY() + uncollidableStartY;
    }
    public Double getCollisionEndX() {
        return this.getLayoutX() + uncollidableEndX;
    }
    
    public Double getCollisionEndY() {
        return this.getLayoutY() + uncollidableEndY;
    }
    
     static class CollisionComparator implements Comparator<GameObject> {
     public int compare(GameObject o1, GameObject o2) {
         return o1.getCollisionStartY().compareTo(o2.getCollisionStartY());
     }
 }
}
