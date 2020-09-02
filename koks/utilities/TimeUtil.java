package koks.utilities;

/**
 * @author avox | lmao | kroko
 * @created on 29/07/2020.
 */
public class TimeUtil {

    public long lastMS;

    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    public boolean isDelayComplete(float time) {
        return System.currentTimeMillis() - (long) time >= lastMS;
    }

}