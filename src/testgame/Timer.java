
package testgame;

/**
 *
 * @author Ville
 */
public class Timer {
    private long start;
    private long lastAnimation;
    private int animationRound;
    private boolean started; 
    
    public void start() {
        this.start = System.currentTimeMillis();
        started = true;
        animationRound = 0;
    }
    
    public void stop() {
        start = 0;
        lastAnimation = 0;
        animationRound = 0;
    }
    
    public long elapsedTime() {
        long now = System.currentTimeMillis();
        return now - start;
    }
    
    public long lastAnimation() {
        return lastAnimation;
    }
    
    public void setLastAnimation(long lastAnimation) {
        this.lastAnimation = lastAnimation;
    }
    
    public boolean hasStarted() {
        return started;
    }
    
    public void increaseAnimationRound() {
        animationRound++;
    }
    
    public int getAnimationRound() {
        return animationRound;
    }
}
