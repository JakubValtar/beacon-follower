package ev3.behaviour.action.backgroundtask;

import ev3.Context;
import ev3.behaviour.action.Action.BackgroundTask;


public class SteerRight implements BackgroundTask {

  @Override
  public boolean runInBackground(Context context) {
    context.pilot.steerRight();
    return true;
  }

}
