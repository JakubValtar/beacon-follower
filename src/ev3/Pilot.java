package ev3;

import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;

public class Pilot {

  private WheeledChassis chassis;

  private final float linearSpeed;
  private final float angularSpeed;

  public Pilot(float linearSpeed, float angularSpeed) {
    Wheel left = WheeledChassis.modelWheel(Motor.C, 10).offset(-70).invert(true);
    Wheel right = WheeledChassis.modelWheel(Motor.B, 10).offset(70).invert(true);
    chassis = new WheeledChassis(new Wheel[] { left, right }, WheeledChassis.TYPE_DIFFERENTIAL);
    this.linearSpeed = linearSpeed;
    this.angularSpeed = angularSpeed;
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

  public void setVelocity(double linearAmt, double angularAmt) {
    chassis.setVelocity(linearSpeed * linearAmt, angularSpeed * angularAmt);
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