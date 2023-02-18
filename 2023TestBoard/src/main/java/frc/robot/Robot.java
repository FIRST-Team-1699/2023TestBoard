// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.utils.LEDPresets;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // MOTOR SENSOR
  public Joystick driveStick = new Joystick(0);
  public CANSparkMax motorOne = new CANSparkMax(2, MotorType.kBrushless);
  public CANSparkMax motorTwo = new CANSparkMax(26, MotorType.kBrushless);
  public RelativeEncoder encoderOne = motorOne.getEncoder();
  public RelativeEncoder encoderTwo = motorTwo.getEncoder();
  double sliderOutput = 0.0;

  // COLOR SENSOR
  public final I2C.Port i2c = I2C.Port.kOnboard;
  public final ColorSensorV3 colorSensor = new ColorSensorV3(i2c);
  public final ColorMatch colorMatcher = new ColorMatch();
  private final Color kYellowTarget = new Color(0.361, 0.524, 0.113);
  private final Color kPurpleTarget = new Color(0.143, 0.427, 0.429);

  // LEDs
  public static AddressableLED leds = new AddressableLED(9);
  public static AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(30);
  int colorSensedR = 1;
  int colorSensedG = 1;
  int colorSensedB = 1;
  boolean rainbow = false;
  boolean baconColors = false;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    colorMatcher.addColorMatch(kYellowTarget);
    colorMatcher.addColorMatch(kPurpleTarget);
    leds.setLength(ledBuffer.getLength());
    leds.setData(ledBuffer);
    leds.start();
    rainbow = false;
  }

  @Override
  public void robotPeriodic() {
    if(driveStick.getRawButtonPressed(3)){
      if(rainbow){
        rainbow = false;
      } else {
        rainbow = true;
      }
    }

    if(driveStick.getRawButtonPressed(4)){
      if(baconColors){
        baconColors = false;
      } else {
        baconColors = true;
      }
    }

    Color detectedColor = colorSensor.getColor();
    ColorMatchResult matchResult = colorMatcher.matchClosestColor(detectedColor);
    if(matchResult.confidence > .92){
      if (matchResult.color == kYellowTarget) {
        colorSensedR = 250;
        colorSensedG = 200;
        colorSensedB = 0;
      } else if (matchResult.color == kPurpleTarget) {
        colorSensedR = 150;
        colorSensedG = 0;
        colorSensedB = 255;
      }
    } else if (DriverStation.getAlliance() == Alliance.Red) {
      colorSensedR = 255;
      colorSensedG = 0;
      colorSensedB = 0;
    } else {
      colorSensedR = 0;
      colorSensedG = 0;
      colorSensedB = 255; 
    }
    for(int i = 0; i < ledBuffer.getLength(); i++) {
      ledBuffer.setRGB(i, colorSensedR, colorSensedG, colorSensedB);
    }

    if(rainbow) {
      LEDPresets.rainbow();
    }

    if(baconColors) {
      LEDPresets.baconColors();
    }
    leds.setData(ledBuffer);
    System.out.println(matchResult.confidence); 
  }
  @Override
  public void autonomousInit() {}

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // MOTORS
    sliderOutput = driveStick.getThrottle();
    System.out.println("Speed: " + sliderOutput);

    if(driveStick.getRawButton(11)){
      System.out.println("Motor 1 running");
      motorOne.set(sliderOutput);
    }

    if(driveStick.getRawButtonReleased(11)){
      motorOne.set(0);
    }

    if(driveStick.getRawButton(12)){
      System.out.println("Motor 2 running");
      motorTwo.set(sliderOutput);
    }

    if(driveStick.getRawButtonReleased(12)){
      motorTwo.set(0);
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
