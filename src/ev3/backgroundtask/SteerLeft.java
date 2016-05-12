package ev3.backgroundtask;

import ev3.Context;
import ev3.behaviourtree.Action.BackgroundTask;


public class SteerLeft implements BackgroundTask {

  @Override
  public boolean runInBackground(Context context) {
    context.pilot.steerLeft();
    return true;
  }

}
