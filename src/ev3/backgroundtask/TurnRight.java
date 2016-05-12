package ev3.backgroundtask;

import ev3.Context;
import ev3.behaviourtree.Action.BackgroundTask;


public class TurnRight implements BackgroundTask {

  @Override
  public boolean runInBackground(Context context) {
    context.pilot.turnRight();
    return true;
  }

}
