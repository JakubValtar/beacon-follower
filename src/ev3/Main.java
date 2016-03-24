package ev3;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class Main {

  public static void main(String[] args) {
    LCD.clear();
    LCD.drawString("First EV3 Program", 0, 5);
    LCD.refresh();
    Button.waitForAnyPress();
    LCD.clear();
    LCD.refresh();

  }

}
