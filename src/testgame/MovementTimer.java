/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testgame;

import gameobjects.Map;
import gameobjects.Sprite;
import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import gameobjects.CollisionMap;
import javafx.scene.control.ScrollPane;
/**
 *
 * @author Ville
 */
public class MovementTimer extends AnimationTimer {

    double movementLength;
    boolean movingNorth;
    boolean movingEast;
    boolean movingSouth;
    boolean movingWest;
    Sprite sprite;
    Map map;
    LongProperty lastUpdateTime = new SimpleLongProperty();
    
    public void setMovementLength(double newLength) {
        this.movementLength = newLength;
    }
    
    public void setLastUpdateTime(long lastUpdate) {
        this.lastUpdateTime.set(lastUpdate);
    }
    
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    
    public void setMap(Map map) {
        this.map = map;
    }
    
    public void setDirection(String direction) {
        if(direction.equals("North")) {
            movingNorth=true; movingEast=false; movingWest=false; movingSouth=false;
        }else if(direction.equals("East")) {
            movingEast=true; movingWest=false; movingNorth=false; movingSouth=false;
        }else if(direction.equals("South")) {
            movingSouth=true; movingEast=false; movingNorth=false; movingWest=false;
        }else {
            movingWest=true;movingEast=false;movingNorth=false;movingSouth=false;
        }
    }
    @Override
    public void handle(long timestamp) {
        CollisionMap colMap = map.getCollisionMap();
        boolean canMove = true;
        if (lastUpdateTime.get() > 0) {
            if(movingNorth || movingSouth) {
                final double elapsedSeconds = (timestamp - lastUpdateTime.get()) / 50000000.0 ;
                final double deltaY = elapsedSeconds * movementLength;
                final double oldY = sprite.getLayoutY();
                final double newY = Math.max(0, Math.min(map.getHeight()-64, oldY + deltaY));
                double[] start = sprite.getCollisionStart();
                double[] layerStart = new double[2];
                double[]end = sprite.getCollisionEnd();
                double[] layerEnd = new double[2];
                layerStart[0] = sprite.getLayoutX();
                layerStart[1] = newY;
                layerEnd[0] = sprite.getLayoutX() + sprite.getWidth();
                layerEnd[1] = sprite.getLayoutY();
                if(movingNorth) {
                    start[1] = start[1] - 5;
                    canMove =colMap.checkCollisionNorth(start, end, sprite);
                }else if(movingSouth) {
                    end[1] = end[1] + 5;
                    canMove = colMap.checkCollisionSouth(start, end, sprite);
                }if(canMove) {
                    sprite.setLayoutY(newY);
                    if(sprite.getClass().equals(Sprite.class)) {
                        ScrollPane mainScreen = (ScrollPane)map.getParent().getParent().getParent();
                        mainScreen.setVvalue(sprite.getLayoutY());
                    }
                }else map.stopMoving(sprite);
                map.checkLayering();
                colMap.updateCollisionPos(sprite);
            }else if(movingWest || movingEast) {
                final double elapsedSeconds = (timestamp - lastUpdateTime.get()) / 50000000.0 ;
                final double deltaX = elapsedSeconds * movementLength;
                final double oldX = sprite.getLayoutX();
                final double newX = Math.max(0, Math.min(map.getWidth()-64, oldX + deltaX));
                double[] start = sprite.getCollisionStart();
                double[] layerStart = new double[2];
                double[]end = sprite.getCollisionEnd();
                double[] layerEnd = new double[2];
                layerStart[0] = sprite.getLayoutX();
                layerStart[1] = sprite.getLayoutY();
                layerEnd[0] = sprite.getLayoutX() + sprite.getWidth();
                layerEnd[1] = sprite.getLayoutY() + sprite.getHeight();
                if(movingWest) {
                    start[0] = newX;
                    canMove =colMap.checkCollisionWest(start, end, sprite);
                }else if(movingEast) {
                    end[0] = end[0] + 3;
                    canMove = colMap.checkCollisionEast(start, end, sprite);
                }if(canMove) {
                    sprite.setLayoutX(newX);
                    if(sprite.getClass().equals(Sprite.class)) {
                        ScrollPane mainScreen = (ScrollPane)map.getParent().getParent().getParent();
                        mainScreen.setHvalue(sprite.getLayoutX());
                    }
                } else map.stopMoving(sprite);
                map.checkLayering();
                colMap.updateCollisionPos(sprite);
            }
        }
        lastUpdateTime.set(timestamp);
    }  
}
