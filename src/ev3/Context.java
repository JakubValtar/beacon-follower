package ev3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Context {

  public final ExecutorService executor;

  public final Pilot pilot;
  public final SensorReader sensorReader;

  public int beaconDistance;
  public int beaconDirection;
  public float surfaceLght;

  public float groundLght;
  public float obstacleLght;

  public Context(Pilot pilot) {
    executor = Executors.newSingleThreadExecutor();

    this.pilot = pilot;
    sensorReader = new SensorReader();
  }


}
