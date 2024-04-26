package eu.openanalytics.phaedra.util;

import java.awt.*;

public class ColorUtils {

    public static String toHex(Color color) {
        return String.format("#%02X%02X%02X", color.getRGB(), color.getGreen(), color.getBlue());
    }

    public static String toHex(int red, int green, int blue) {
        return String.format("#%02X%02X%02X", red, green, blue);
    }

    public static String toHex(int rgb) {
        Color color = new Color(rgb);
        return toHex(color);
    }

    public static Color fromHex(String hex) {
        int r = Integer.parseInt(hex.substring(1, 3), 16);
        int g = Integer.parseInt(hex.substring(3, 5), 16);
        int b = Integer.parseInt(hex.substring(5, 7), 16);
        return new Color(r, g, b);
    }
}
