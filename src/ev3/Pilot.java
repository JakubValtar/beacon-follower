package ev3;

import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;

public class Pilot {

  private WheeledChassis chassis;

  private float linearSpeed;
  private float angularSpeed;

  public Pilot() {
    Wheel left = WheeledChassis.modelWheel(Motor.C, 10).offset(-70).invert(true);
    Wheel right = WheeledChassis.modelWheel(Motor.B, 10).offset(70).invert(true);
    chassis = new WheeledChassis(new Wheel[] { left, right }, WheeledChassis.TYPE_DIFFERENTIAL);

    linearSpeed = 10;
    angularSpeed = 5;
  }

  public void forward() {
    chassis.setVelocity(linearSpeed, 0);
  }

  public void steerLeft() {
    chassis.setVelocity(linearSpeed, -angularSpeed);
  }

  public void steerRight() {
    chassis.setVelocity(linearSpeed, angularSpeed);
  }

  public void steer(double rate)
  {
    chassis.setVelocity(linearSpeed, angularSpeed * rate);
  }

  public void stop() {
    chassis.stop();
  }

  public void turnLeft() {
    chassis.setVelocity(0, -angularSpeed);
  }

  public void turnRight() {
    chassis.setVelocity(0, angularSpeed);
  }

}