package ev3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Context {

  public final ExecutorService executor;

  public Float beaconDistance;
  public Float beaconDirection;
  public Integer surfaceColor;

  public Pilot pilot;
  public SensorReader sensorReader;

  public Context() {
    executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        Thread t = new Thread("EXECUTOR");
        t.setPriority(Thread.MAX_PRIORITY);
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
          @Override
          public void uncaughtException(Thread t, Throwable e) {
            e.printStackTrace();
          }
        });
        return t;
      }
    });

    pilot = new Pilot();
    sensorReader = new SensorReader();
  }


}
