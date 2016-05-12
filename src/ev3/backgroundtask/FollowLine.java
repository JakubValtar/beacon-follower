package ev3.backgroundtask;

import ev3.Context;
import ev3.behaviourtree.Action;

public class FollowLine implements Action.BackgroundTask {

  private final float groundLght;
  private final float obstacleLght;

  public FollowLine(float groundLght, float obstacleLght) {
    this.groundLght = groundLght;
    this.obstacleLght = obstacleLght;
  }

  @Override
  public boolean runInBackground(Context context) {
    float surfaceLght = context.surfaceLght;

    // clip to (obstacle, ground)
    float diff = clip(surfaceLght, obstacleLght, groundLght);
    // from (obstacle, ground) to (0, 1)
    diff = norm(diff, obstacleLght, groundLght);
    // from (0, 1) to (-1, 1)
    diff = 2 * diff - 1;

    // extract direction; obstacle (-) left, ground (+) right
    int dir = (int) Math.signum(diff);
    // extract magnitude (0, 1); pow creates deadzone around 0
    float mag = (float) Math.pow(Math.abs(diff), 4);

    // go faster if magnitude is close to zero,
    // slow down if we are to the side
    float linearAmt = mix(1.2f, 0.2f, mag);

    // don't steer if magnitude is close to zero,
    // steer more if we are to the side
    float angularAmt = dir * mix(0.0f, 0.9f, mag);

    context.pilot.setVelocity(linearAmt, angularAmt);

    return true;
  }

  // linear interpolation between (a) and (b)
  // ratio: (0, 1); 0 -> (a), 1 -> (b)
  static float mix(float a, float b, float ratio) {
    return a * (1 - ratio) + b * ratio;
  }

  // limit (val) to the range (min(a, b), max(a, b))
  static float clip(float val, float a, float b) {
    val = Math.min(val, Math.max(a, b));
    val = Math.max(val, Math.min(a, b));
    return val;
  }

  // normalize val from range (a, b) to (0, 1)
  static float norm(float val, float a, float b) {
    return (val - a) / (b - a);
  }
}
