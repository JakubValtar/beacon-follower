package ev3;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;

public class SensorReader {

  private EV3IRSensor ev3IRSensor;
  private EV3ColorSensor ev3ColorSensor;
  private SensorMode irMode;
  private SensorMode colorMode;
  private float[] sample_ir;
  private float[] sample_color;

  public SensorReader() {
    ev3IRSensor = new EV3IRSensor(SensorPort.S2);
    ev3ColorSensor = new EV3ColorSensor(SensorPort.S3);
    irMode = ev3IRSensor.getSeekMode();
    colorMode = ev3ColorSensor.getColorIDMode();
    sample_ir = new float[irMode.sampleSize()];
    sample_color = new float[colorMode.sampleSize()];
  }

  public void read(Context context) {
    irMode.fetchSample(sample_ir, 0);
    colorMode.fetchSample(sample_color, 0);
    context.beaconDirection = sample_ir[0];
    context.beaconDistance = sample_ir[1];
    context.surfaceColor = (int) sample_color[0];
  }

}