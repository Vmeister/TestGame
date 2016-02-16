/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobjects;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 *
 * @author Ville
 */
public class Door extends StaticObject {
    boolean opened = false;
    ArrayList<Rectangle2D> frames;
    int frame = 0;  
    public Door(String id, double width, double height, Image image, boolean collidable, boolean layerCol) {
        super(id, width, height, image, collidable, layerCol);
        frames = new ArrayList<>();
        createAnimation();
    }
    
    public void createAnimation() {
        int x = 1;
        int y = 1;
        for(int i = 0 ; i < 5; i++) {
            double[] layout = {x,y};
            Rectangle2D frame = new Rectangle2D(x, y, width, height);
            System.out.println(this.getId() + " added new frame: " + frame.toString());
            frames.add(frame);
            x += width;
        }
        this.setViewport(frames.get(0));
    }
    
    public void startAnimation() {
        System.out.println("Starting " + this.getId() + " animation");
        Timeline timeline = new Timeline(new KeyFrame(
            Duration.millis(100),
            ae -> {
                if(frame == 5) {
                    System.out.println("opened");
                    opened = true;
                    collidable = opened;
                    frame--;
                }
                else if(frame == -1) {
                    System.out.println("closed");
                    frame = 0;
                    opened = false;
                    collidable = opened;
                }
                else if(!opened) {
                    openAnimation();
                }else {
                    closeAnimation();
                }
                 
            }));
        timeline.setCycleCount(6);
        timeline.play();
    }
    
    public void openAnimation() {
        System.out.println("Opening " + this.getId());
        this.setViewport(frames.get(frame));
        frame++;
    }
    
    public void closeAnimation() {
        System.out.println("Closing " + this.getId());
        this.setViewport(frames.get(frame));
        frame--;
    }
    
}
