package frc.utils;

import frc.robot.Robot;
public class LEDPresets {
    public static int rainbowFirstPixelHue = 1;
    public static void rainbow() {
        // For every pixel
        for (int i = 0; i < Robot.ledBuffer.getLength(); i++) {
          
          int hue = (rainbowFirstPixelHue + (i * 180 / Robot.ledBuffer.getLength())) % 180;
          // Set the value
          Robot.ledBuffer.setHSV(i, hue, 255, 128);
        }
        // Increase by to make the rainbow "move"
        rainbowFirstPixelHue += 3;
        // Check bounds
        rainbowFirstPixelHue %= 180;
    }

    public static void baconColors() {
        for (int i = 0; i < Robot.ledBuffer.getLength(); i += 2) {
            Robot.ledBuffer.setRGB(i, 250, 200, 0);
            Robot.ledBuffer.setRGB(i + 1, 0, 0, 255);
        }
    }
}
