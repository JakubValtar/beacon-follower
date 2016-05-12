package ev3.backgroundtask;

import ev3.Context;
import ev3.Main;
import ev3.behaviourtree.Action.BackgroundTask;
import ev3.behaviourtree.Result;


public class ScanSurface implements BackgroundTask {

  private final boolean ground;

  public ScanSurface(boolean ground) {
    this.ground = ground;
  }

  @Override
  public boolean runInBackground(Context context) {

    float val = ground ? 0 : 1;

    context.pilot.forward();

    for (int i = 0; i < 100; i++) {
      context.sensorReader.read(context);
      val = ground
          ? Math.max(val, context.surfaceLght)
          : Math.min(val, context.surfaceLght);
      Main.sleep(10);
    }

    context.pilot.stop();

    if (ground) {
      context.groundLght = val;
    } else {
      context.obstacleLght = val;
    }

    return true;
  }

}
