package koks.utilities.value.values;

import koks.utilities.value.Value;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 09:13
 */
public class NumberValue <T extends Number> extends Value <T> {

    private T defaultValue;
    private T maxValue;
    private T minValue;

    public NumberValue(String name, T defaultValue, T maxValue, T minValue) {
        this.defaultValue = defaultValue;
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(T maxValue) {
        this.maxValue = maxValue;
    }

    public T getMinValue() {
        return minValue;
    }

    public void setMinValue(T minValue) {
        this.minValue = minValue;
    }
}
