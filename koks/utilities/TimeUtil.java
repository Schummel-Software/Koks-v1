package koks.utilities;

public class TimeUtil {

    public long lastMS;

    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    public boolean isDelayComplete(float time) {
        return System.currentTimeMillis() - (long) time >= lastMS;
    }

}