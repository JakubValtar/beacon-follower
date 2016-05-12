package ev3.value;

import ev3.Context;
import ev3.LocalMath;
import ev3.behaviourtree.Condition;

public class ObstacleProbability implements Condition.Value {

  @Override
  public int get(Context context) {
    float lght = LocalMath.clip(context.surfaceLght, context.groundLght, context.obstacleLght);
    lght = LocalMath.norm(lght, context.groundLght, context.obstacleLght);
    return (int) (lght * 100);
  }

}
