package ev3;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class Pilot {

  private EV3LargeRegulatedMotor left;
  private EV3LargeRegulatedMotor right;
  private float basicSpeed;

  public Pilot() {
    left = new EV3LargeRegulatedMotor(MotorPort.C);
    right = new EV3LargeRegulatedMotor(MotorPort.B);

    basicSpeed = 30;
  }

  public void forward() {
    equalizeMotorsSpeed();

    left.forward();
    right.forward();
  }


  public void steerLeft() {
    setMotorsSpeed(basicSpeed/2, basicSpeed);

    left.forward();
    right.forward();
}

  public void steerRight() {
    setMotorsSpeed(basicSpeed, basicSpeed/2);

    left.forward();
    right.forward();
  }

  public void stop() {
    left.stop();
    right.stop();
  }

  public void turnLeft() {
    equalizeMotorsSpeed();

    right.forward();
    left.backward();
  }

  public void turnRight() {
    equalizeMotorsSpeed();

    left.forward();
    right.backward();
  }

  private void setMotorsSpeed(float leftSpeed, float rightSpeed)
  {
    left.setSpeed(leftSpeed);
    right.setSpeed(rightSpeed);
  }

  private void equalizeMotorsSpeed()
  {
    setMotorsSpeed(basicSpeed, basicSpeed);
  }

}