package koks.utilities;

public class TimeUtil {

    public long lastMS;

    public void reset() {
        lastMS = System.currentTimeMillis();
    }

<<<<<<< HEAD
    public boolean hasReached(float time) {
=======
    public boolean isDelayComplete(float time) {
>>>>>>> origin/master
        return System.currentTimeMillis() - (long) time >= lastMS;
    }

}