/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobjects;

import gameUI.DialogBox;
import gameobjects.GameObject.CollisionComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import testgame.MovementTimer;

/**
 *
 * @author Ville
 */
public class Map extends Pane {
    double height;
    double width;
    ImageView mapGraphic;
    double movementLength = 5;
    HashMap<Sprite, MovementTimer> sprites;
    List<GameObject> gameobjects;
    CollisionMap collisionMap;
    Pane layer1;
    Pane layer2;
    Pane layer3;
    List<GameObject> nonColliding;
    boolean dialogOpen = false;
    
    public Map(double height, double width, ImageView mapGraphic) {
        this.setHeight(height);
        this.height = height;
        this.setWidth(width);
        this.width = width;
        this.getChildren().add(mapGraphic);
        this.sprites = new HashMap<>();
        this.collisionMap = new CollisionMap(this);
        this.layer1 = new Pane();
        layer1.setId("Layer 1");
        this.layer2 = new Pane();
        layer2.setId("Layer 2");
        this.layer3 = new Pane();
        layer3.setId("Layer 3");
        layer1.setPrefHeight(height);
        layer2.setPrefHeight(height);
        layer3.setPrefHeight(height);
        layer1.setPrefWidth(width);
        layer2.setPrefWidth(width);
        layer3.setPrefWidth(width);
        layer1.setVisible(true);
        layer2.setVisible(true);
        layer3.setVisible(true);
        this.getChildren().add(layer1);
        this.getChildren().add(layer2);
        this.getChildren().add(layer3);
        this.gameobjects = new ArrayList<>();
    }
    
    public void addSprite(Sprite sprite, double xpos, double ypos, int layer) {
        sprite.setLayoutX(xpos);
        sprite.setLayoutY(ypos);
        sprite.setLayer(layer);
        MovementTimer timer = new MovementTimer();
        timer.setMap(this);
        sprites.put(sprite, timer);
        sprite.setLayer(layer);
        gameobjects.add(sprite);
        if(layer == 1)
            layer1.getChildren().add(sprite);
        else if(layer == 2)
            layer2.getChildren().add(sprite);
        else layer3.getChildren().add(sprite);
        if(sprite.getClass().equals(NPCSprite.class)) {
            ((NPCSprite)sprite).setParentMap(this);
        }
        addToCollisionMap(sprite);
    }
    
    public void addStatic(StaticObject staticObj, double xpos, double ypos, int layer) {
        staticObj.setLayoutX(xpos);
        staticObj.setLayoutY(ypos);
        staticObj.setLayer(layer);
        MovementTimer timer = new MovementTimer();
        timer.setMap(this);
        gameobjects.add(staticObj);
        staticObj.setLayer(layer);
        if(layer == 1)
            layer1.getChildren().add(staticObj);
        else if(layer == 2)
            layer2.getChildren().add(staticObj);
        else layer3.getChildren().add(staticObj);
        if(staticObj.collisionSet)
            addStaticObjectToCollisionMap(staticObj);
    }
    
    public void moveSpriteNorth(Sprite sprite) {
        if(!dialogOpen) {
            sprite.moveNorth();
            MovementTimer moveTimer = sprites.get(sprite);
            moveTimer.setMovementLength(-movementLength);
            moveTimer.setDirection("North");
            moveTimer.setSprite(sprite);
            moveTimer.start();
        }
    }
    
    public void moveSpriteSouth(Sprite sprite) {
        if(!dialogOpen) {
            sprite.moveSouth();
            MovementTimer moveTimer = sprites.get(sprite);
            moveTimer.setMovementLength(movementLength);
            moveTimer.setDirection("South");
            moveTimer.setSprite(sprite);
            moveTimer.start();
        }
    }
    
    public void moveSpriteEast(Sprite sprite) {
        if(!dialogOpen) {
            sprite.moveEast();
            MovementTimer moveTimer = sprites.get(sprite);
            moveTimer.setMovementLength(movementLength);
            moveTimer.setDirection("East");
            moveTimer.setSprite(sprite);
            moveTimer.start();
        }
    }
    
    public void moveSpriteWest(Sprite sprite) {
        if(!dialogOpen) {
            sprite.moveWest();
            MovementTimer moveTimer = sprites.get(sprite);
            moveTimer.setMovementLength(-movementLength);
            moveTimer.setDirection("West");
            moveTimer.setSprite(sprite);
            moveTimer.start();
        }
    }

    public void stopMoving(Sprite sprite) {
        MovementTimer timer = sprites.get(sprite);
        timer.setMovementLength(0);
    }
    
    public void startAIs() {
        for(Sprite sprite: sprites.keySet()) {
            if(sprite.getClass().equals(NPCSprite.class)) {
                NPCSprite sprite2 = (NPCSprite)sprite;
                sprite2.startAI();
            }
        }
    }
    
    public void stopAIs() {
        for(Sprite sprite: sprites.keySet()) {
            if(sprite.getClass().equals(NPCSprite.class)) {
                NPCSprite sprite2 = (NPCSprite)sprite;
                sprite2.stopAI();
                stopMoving(sprite2);
            }
        }
    }
    
    public CollisionMap getCollisionMap() {
        return collisionMap;
    }
    public void addToCollisionMap(Sprite sprite) {
        if(!sprite.collisionSet) {
            collisionMap.addToCollisionMap(sprite.getLayoutX(), sprite.getLayoutY(), sprite.getLayoutX()+sprite.width, 
                sprite.getLayoutY()+sprite.height, sprite);
            sprite.setCollisionArea(0, 0, sprite.width, sprite.height);
        }
        else {
            double[] collisionStart = sprite.getCollisionStart();
            double[] collisionEnd = sprite.getCollisionEnd();
            collisionMap.addToCollisionMap(collisionStart[0], collisionStart[1], collisionEnd[0], 
                collisionEnd[1], sprite);
        }
        
    }

    private void addStaticObjectToCollisionMap(StaticObject staticObj) {
        double[] collisionStart = staticObj.getCollisionStart();
        double[] collisionEnd = staticObj.getCollisionEnd();
        collisionMap.addToCollisionMap(collisionStart[0], collisionStart[1], collisionEnd[0], 
                collisionEnd[1], staticObj);
    }
    
    public void checkLayering() {
        Collections.sort(gameobjects, new CollisionComparator());
        for(GameObject object: gameobjects)
            object.toFront();
  
    }
    
    public void changeLayer(GameObject object, int layer) {
        if(layer == 1) {
            if(!layer1.getChildren().contains(object))
                layer1.getChildren().add(object);
            layer2.getChildren().remove(object);
            layer3.getChildren().remove(object);
        }
        else if(layer == 2) {
             if(!layer2.getChildren().contains(object))
                layer2.getChildren().add(object);
            layer1.getChildren().remove(object);
            layer3.getChildren().remove(object);
        }
        else if(layer == 3) {
            if(!layer3.getChildren().contains(object))
                layer3.getChildren().add(object);
            layer1.getChildren().remove(object);
            layer2.getChildren().remove(object);
        }
    }

    public void activateObject(Sprite sprite) {
        GameObject object = null;
        double[] start = collisionMap.findStartPosByObject(sprite);
        double[] end = collisionMap.findEndPosByObject(sprite);
        if(sprite.wasMovingNorth) {
            System.out.println("North testi");
            start[1] = start[1] - 5;
            object = collisionMap.findCollidingObjectNorth(start, end, sprite);
        }if(sprite.wasMovingEast){
            end[0] = end[0] + 5;
            System.out.println("Eastw testi");
            object = collisionMap.findCollidingObjectEast(start, end, sprite);
        }if(sprite.wasMovingSouth){
            end[1] = end[1] + 5;
            System.out.println("South testi");
            object = collisionMap.findCollidingObjectSouth(start, end, sprite);
        }if(sprite.wasMovingWest){
            start[0] = start[0] - 5;
            System.out.println("West testi");
            object = collisionMap.findCollidingObjectWest(start, end, sprite);
        }
        if(object != null && object.getClass().equals(Door.class)) {
            ((Door)object).startAnimation();
        }
        else if(object != null && object.getClass().equals(NPCSprite.class)) {
            if(((NPCSprite)object).getDialog() != null) {
                DialogBox dialog = ((NPCSprite)object).getDialog();
                dialog.setLayoutX(object.getLayoutX());
                dialog.setLayoutY(object.getLayoutY() - 40);
                if(!layer3.getChildren().contains(dialog)) {
                    dialogOpen = true;
                    layer3.getChildren().add(dialog);
                    ((NPCSprite)object).stopAI();
                }
                else {
                    dialogOpen = false;
                    layer3.getChildren().remove(dialog);
                    ((NPCSprite)object).startAI();
                }
            }
        }
    }
}
