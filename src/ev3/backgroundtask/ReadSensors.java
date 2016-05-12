package ev3.backgroundtask;

import ev3.Context;
import ev3.behaviourtree.Action.BackgroundTask;


public class ReadSensors implements BackgroundTask {

  @Override
  public boolean runInBackground(Context context) {
    context.sensorReader.read(context);
    return true;
  }

}
