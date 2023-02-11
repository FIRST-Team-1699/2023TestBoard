// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public Joystick driveStick = new Joystick(0);
  public CANSparkMax motorOne = new CANSparkMax(1, MotorType.kBrushless);
  public CANSparkMax motorTwo = new CANSparkMax(2, MotorType.kBrushless);
  public RelativeEncoder encoderOne = motorOne.getEncoder();
  public RelativeEncoder encoderTwo = motorTwo.getEncoder();
  double sliderOutput = 0.0;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {}

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
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
