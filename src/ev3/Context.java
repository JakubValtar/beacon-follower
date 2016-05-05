package ev3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Context {

  public final ExecutorService executor;

  public float beaconDistance;
  public float beaconDirection;
  public int surfaceColor;

  public Pilot pilot;
  public SensorReader sensorReader;

  public Context() {
    executor = Executors.newSingleThreadExecutor();

    pilot = new Pilot();
    sensorReader = new SensorReader();
  }


}
