package ev3;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;

public class SensorReader {

  private SensorMode irMode;
  private SensorMode rgbMode;
  private float[] sample_ir;
  private float[] sample_rgb;

  public SensorReader() {
    EV3IRSensor ev3IRSensor = new EV3IRSensor(SensorPort.S2);
    EV3ColorSensor ev3ColorSensor = new EV3ColorSensor(SensorPort.S3);

    irMode = ev3IRSensor.getSeekMode();
    rgbMode = ev3ColorSensor.getRGBMode();

    sample_ir = new float[irMode.sampleSize()];
    sample_rgb = new float[rgbMode.sampleSize()];
  }

  public void read(Context context) {
    // Read beacon info
    irMode.fetchSample(sample_ir, 0);
    context.beaconDirection = (int) sample_ir[0];
    context.beaconDistance = (int) sample_ir[1];

    // Read surface color lightness
    rgbMode.fetchSample(sample_rgb, 0);
    context.surfaceLght = calculateLightness(sample_rgb[0], sample_rgb[1], sample_rgb[2]);
  }

  private float calculateLightness(float red, float green, float blue) {
    return (red + green + blue) / 3f;
  }

}
