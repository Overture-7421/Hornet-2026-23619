package org.firstinspires.ftc.teamcode.utils;

import java.util.Map;
import java.util.TreeMap;

/**
 * A map that allows for interpolation and finding closest keys/values.
 * This map supports double keys and values, allowing for numeric operations.
 */
public class InterpolatableMap extends TreeMap<Double, Double> {
    public InterpolatableMap() {
        super(Double::compareTo);
    }

    /**
     * Interpolates the value for a given key based on the surrounding keys and values.
     * If the key is outside the range of existing keys, it returns the closest value.
     * @param key the key to interpolate
     * @return the interpolated value, or NaN if no keys are available
     */
    public double interpolate(double key) {
        Map.Entry<Double, Double> lowerEntry = floorEntry(key);
        Map.Entry<Double, Double> higherEntry = ceilingEntry(key);

        if (lowerEntry == null && higherEntry == null) {
            return Double.NaN; // No keys available
        }

        if (lowerEntry == null) {
            return higherEntry.getValue(); // Only higher key exists
        }

        if (higherEntry == null) {
            return lowerEntry.getValue(); // Only lower key exists
        }

        double lowerKey = lowerEntry.getKey();
        double higherKey = higherEntry.getKey();
        double lowerValue = lowerEntry.getValue();
        double higherValue = higherEntry.getValue();

        // Linear interpolation
        return lowerValue + (higherValue - lowerValue) * ((key - lowerKey) / (higherKey - lowerKey));
    }
}