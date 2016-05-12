package ev3.backgroundtask;

import ev3.Context;
import ev3.behaviourtree.Action.BackgroundTask;


public class SteerRight implements BackgroundTask {

  @Override
  public boolean runInBackground(Context context) {
    context.pilot.steerRight();
    return true;
  }

}
