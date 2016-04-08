package ev3.tests;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;


public class SimpleTest {

    volatile static boolean running = true;

    public static void irSensorTest()
    {

        EV3IRSensor ev3IRSensor = new EV3IRSensor(SensorPort.S1);
        SensorMode seek = ev3IRSensor.getSeekMode();
        float[] sample = new float[seek.sampleSize()];

        Button.ESCAPE.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(Key k) {
                running = false;
            }

            @Override
            public void keyReleased(Key k) {
                running = false;
            }
        });

        while(running) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
            seek.fetchSample(sample, 0);
        }
    }

    public static void lcdTest()
    {
        LCD.clear();
        LCD.drawString("First EV3 Program", 0, 5);
        LCD.refresh();
        Button.waitForAnyPress();
        LCD.clear();
        LCD.refresh();
    }

}