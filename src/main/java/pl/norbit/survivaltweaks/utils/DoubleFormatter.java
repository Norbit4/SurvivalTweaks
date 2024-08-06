package pl.norbit.survivaltweaks.utils;

import java.text.DecimalFormat;

public class DoubleFormatter {

    private DoubleFormatter() {
        throw new IllegalStateException("Utility class");
    }

    private static final DecimalFormat df = new DecimalFormat("0.#");
    public static String format(double d){
        return df.format(d);
    }
}
