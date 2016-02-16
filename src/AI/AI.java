/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import javafx.animation.PauseTransition;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import gameobjects.Map;
import gameobjects.NPCSprite;

public class AI {
    PauseTransition timer;
    NPCSprite sprite;
    Random random;
    Timeline timeline;
    public AI(NPCSprite sprite) {
        this.sprite = sprite;
        this.timer = new PauseTransition();
    }
    public PauseTransition getTimer() {
        return timer;
    }
    
    public void startAI() {
        if(timeline == null) {
            timeline = new Timeline(new KeyFrame(
            Duration.millis(2500),
                ae -> move()));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }else timeline.play();
    }

    private void move() {
        Random random =new Random();
        int randomTime = 500 + random.nextInt(10000);
        timer.setDuration(new Duration(randomTime));
        timer.setOnFinished(e -> {
            int randDirection = random.nextInt(4);
            timeline = new Timeline(new KeyFrame(
            Duration.millis(2500),
            ae -> moveRandDirection(randDirection)));
            timeline.play();
        });
        if(!timer.statusProperty().equals(Status.RUNNING))
            timer.play();
        sprite.getMap().stopMoving(sprite);
 
    }
    
    private void moveRandDirection(int randDirection) {
        String[] direction = {"North", "East", "South", "West"};
        Map map = sprite.getMap();
        if(direction[randDirection].equals("North"))
            map.moveSpriteNorth(sprite);
        else if(direction[randDirection].equals("East"))
            map.moveSpriteEast(sprite);
        else if(direction[randDirection].equals("South"))
             map.moveSpriteSouth(sprite);
        else map.moveSpriteWest(sprite);
    }

    public void stopAI() {
        timer.stop();
        timeline.stop();
    }
    
    
}
