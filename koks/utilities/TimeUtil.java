package koks.utilities;

public class TimeUtil {

    public long lastMS;

    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    public boolean hasReached(float time) {
        return System.currentTimeMillis() - (long) time >= lastMS;
    }

}