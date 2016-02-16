/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobjects;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.PauseTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author Ville
 */
public class Sprite extends GameObject {
    
    double width;
    double height;
    double xposition;
    double yposition;
    ImageView currentSprite;
    Duration duration;
    boolean wasMovingWest;
    boolean wasMovingNorth;
    boolean wasMovingSouth;
    boolean wasMovingEast;
    int lastframe;
    PauseTransition transition;
    boolean animationIsRunning;
    Image spriteMap;
    HashMap<String, double[]> animationGroups;
    
    public Sprite(String id, double width, double height,
        Image spriteMap, boolean collidable, boolean layerCol) {
        super(id, width, height, spriteMap, collidable, layerCol);
        this.width = width;
        this.height = height;
        this.spriteMap = spriteMap;
        this.animationGroups = new HashMap<>();
        groupAnimations();
        setCurrentView();
        this.transition = new PauseTransition();
        transition.setDuration(new Duration(250));
    }

    public void groupAnimations() {
        String[] animations = {"IdleSouth", "WalkSouth1","WalkSouth2","WalkSouth3",
            "IdleNorth", "WalkNorth1", "WalkNorth2", "WalkNorth3",
           "IdleWest", "WalkWest1","WalkWest2", "WalkWest3",
           "IdleEast", "WalkEast1", "WalkEast2", "WalkEast3"};
        int x = 1;
        int y = 1;
        for(int i = 0 ; i < animations.length; i++) {
            double[] layout = {x,y};
            x += width;
            if(i == 7) {
                x = 1;
                y += height;
            }
            System.out.println("Creating layout: " + animations[i]+ "," + layout[0] + "," + layout[1]);
            animationGroups.put(animations[i], layout);
        }
    }
    
    public void moveNorth() {
        wasMovingWest = false;
        wasMovingEast = false;
        wasMovingSouth = false;
        wasMovingNorth = true;
        double[] layout1 = animationGroups.get("WalkNorth1");
        double[] layout2 = animationGroups.get("WalkNorth2");
        double[] layout3 = animationGroups.get("WalkNorth3"); 
        double[] layout4 = animationGroups.get("WalkNorth2");
        ArrayList<Rectangle2D> layouts = new ArrayList<>();
        layouts.add(new Rectangle2D(layout1[0], layout1[1], width, height));
        layouts.add(new Rectangle2D(layout2[0], layout1[1], width, height));
        layouts.add(new Rectangle2D(layout3[0], layout1[1], width, height));
        layouts.add(new Rectangle2D(layout4[0], layout4[1], width, height));
        //Rectangle2D view = new Rectangle2D(layout2[0],layout2[1], 64.0, 64.0);
        startAnimation(layouts);

    }
    
    public void moveEast() {
        wasMovingWest = false;
        wasMovingEast = true;
        wasMovingSouth = false;
        wasMovingNorth = false;
        double[] layout1 = animationGroups.get("WalkEast1");
        double[] layout2 = animationGroups.get("WalkEast2");
        double[] layout3 = animationGroups.get("WalkEast3"); 
        double[] layout4 = animationGroups.get("WalkEast2");
        ArrayList<Rectangle2D> layouts = new ArrayList<>();
        layouts.add(new Rectangle2D(layout1[0], layout1[1], width, height));
        layouts.add(new Rectangle2D(layout2[0], layout1[1], width, height));
        layouts.add(new Rectangle2D(layout3[0], layout1[1], width, height));
        layouts.add(new Rectangle2D(layout4[0], layout4[1], width, height));
        startAnimation(layouts);
    }
    
    public void moveSouth() {
        wasMovingWest = false;
        wasMovingEast = false;
        wasMovingSouth = true;
        wasMovingNorth = false;
        double[] layout1 = animationGroups.get("WalkSouth1");
        double[] layout2 = animationGroups.get("WalkSouth2");
        double[] layout3 = animationGroups.get("WalkSouth3"); 
        double[] layout4 = animationGroups.get("WalkSouth2");
        ArrayList<Rectangle2D> layouts = new ArrayList<>();
        layouts.add(new Rectangle2D(layout1[0], layout1[1], width, height));
        layouts.add(new Rectangle2D(layout2[0], layout1[1], width, height));
        layouts.add(new Rectangle2D(layout3[0], layout1[1], width, height));
        layouts.add(new Rectangle2D(layout4[0], layout4[1], width, height));
        startAnimation(layouts);
    }
    
    public void moveWest() {
        wasMovingWest = true;
        wasMovingEast = false;
        wasMovingSouth = false;
        wasMovingNorth = false;
        double[] layout1 = animationGroups.get("WalkWest1");
        double[] layout2 = animationGroups.get("WalkWest2");
        double[] layout3 = animationGroups.get("WalkWest3"); 
        double[] layout4 = animationGroups.get("WalkWest2");
        ArrayList<Rectangle2D> layouts = new ArrayList<>();
        layouts.add(new Rectangle2D(layout1[0], layout1[1], width, height));
        layouts.add(new Rectangle2D(layout2[0], layout1[1], width, height));
        layouts.add(new Rectangle2D(layout3[0], layout1[1], width, height));
        layouts.add(new Rectangle2D(layout4[0], layout4[1], width, height));
        startAnimation(layouts);
    }

    public void setCurrentView() {
        this.setImage(spriteMap);
        double[] idle = animationGroups.get("IdleSouth");
        Rectangle2D view = new Rectangle2D(idle[0], idle[1], width, height);
        System.out.println("Current: Setting sprite to: " + view.toString());
        this.setViewport(view);
        wasMovingSouth = true;
    }
    
    public void stopMoving() {
        double[] idleSouth = animationGroups.get("IdleSouth");
        double[] idleEast = animationGroups.get("IdleEast");
        double[] idleWest = animationGroups.get("IdleWest");
        double[] idleNorth = animationGroups.get("IdleNorth");
        Rectangle2D viewN = new Rectangle2D(idleNorth[0], idleNorth[1], width, height);
        Rectangle2D viewE = new Rectangle2D(idleEast[0], idleEast[1], width, height);
        Rectangle2D viewS = new Rectangle2D(idleSouth[0], idleSouth[1], width, height);
        Rectangle2D viewW = new Rectangle2D(idleWest[0], idleWest[1], width, height);
        PauseTransition transition2 = new PauseTransition();
        transition2.setDuration(transition.getDuration());
        transition.setOnFinished(e -> {
            if(wasMovingNorth)
                this.setViewport(viewN);
            else if(wasMovingSouth)
                this.setViewport(viewS);
            else if(wasMovingEast)
                this.setViewport(viewE);
            else this.setViewport(viewW);;
            lastframe = 0;
            });
        transition.play();
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
    
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }

}
