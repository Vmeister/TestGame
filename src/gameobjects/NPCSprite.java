/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobjects;

import AI.AI;
import gameUI.DialogBox;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class NPCSprite extends Sprite {
    Map map;
    AI ai;
    Timeline timeline;
    DialogBox dialog;
    public NPCSprite(String spriteID, double width, double height, Image spriteMap,
    boolean collidable, boolean layerCol) {
        super(spriteID, width, height, spriteMap, collidable, layerCol);
        ai = new AI(this);
    }
    
    public void setDialog(DialogBox dialog) {
        this.dialog = dialog;
        dialog.setId(this.getId() + ".dialog");
    }
    
    public DialogBox getDialog() {
        return dialog;
    }
    
    public Map getMap() {
        return map;
    }
    
    public void setParentMap(Map map) {
        this.map = map;
    }
    
    public void startAI() {
        ai.startAI();
        loopAnimation();      
    }
    
    public void startAnimation(ArrayList<Rectangle2D> layouts) {
        transition.setOnFinished(e -> {
            animationIsRunning = true;
            this.setViewport(layouts.get(lastframe));
            lastframe++;
            if(lastframe > 3)
                lastframe = 0;
        });
        transition.play();
    }
    
    public void loopAnimation() {
        timeline = new Timeline(new KeyFrame(
            Duration.millis(300),
            ae -> {
                if(wasMovingNorth)
                    moveNorth();
                else if(wasMovingEast)
                    moveEast();
                else if(wasMovingSouth)
                    moveSouth();
                else moveWest();
            }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    void stopAI() {
        ai.stopAI();
        transition.stop();
        timeline.stop();
    }
}
