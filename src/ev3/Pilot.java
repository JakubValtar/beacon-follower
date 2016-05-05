package ev3;

import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class Pilot {

  private DifferentialPilot pilot;

  public Pilot() {
    pilot = new DifferentialPilot(2.1f, 4.4f, Motor.C, Motor.B, true);
  }

  public void forward() {
    pilot.forward();
  }

  public void steerLeft() {
    pilot.steer(50);
}

  public void steerRight() {
    pilot.steer(-50);
  }

  public void stop() {
    pilot.stop();
  }

  public void turnLeft() {
    pilot.rotateLeft();
  }

  public void turnRight() {
    pilot.rotateRight();
  }

}
