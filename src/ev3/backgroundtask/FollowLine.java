package ev3.backgroundtask;

import ev3.Context;
import ev3.LocalMath;
import ev3.behaviourtree.Action;

public class FollowLine implements Action.BackgroundTask {

  @Override
  public boolean runInBackground(Context context) {
   float groundLght = context.groundLght;
   float obstacleLght = context.obstacleLght;
    float surfaceLght = context.surfaceLght;

    // clip to (obstacle, ground)
    float diff = LocalMath.clip(surfaceLght, obstacleLght, groundLght);
    // from (obstacle, ground) to (0, 1)
    diff = LocalMath.norm(diff, obstacleLght, groundLght);
    // from (0, 1) to (-1, 1)
    diff = 2 * diff - 1;

    // extract direction; obstacle (-) left, ground (+) right
    int dir = (int) Math.signum(diff);
    // extract magnitude (0, 1); pow creates deadzone around 0
    float magL = (float) Math.pow(Math.abs(diff), 4);
    float magA = (float) Math.pow(Math.abs(diff), 4);

    // go faster if magnitude is close to zero,
    // slow down if we are to the side
    float linearAmt = LocalMath.mix(1.6f, 0.2f, LocalMath.clip(magL - 0.2f, 0, 1));

    // don't steer if magnitude is close to zero,
    // steer more if we are to the side
    float angularAmt = dir * Math.max(0, LocalMath.mix(-0.2f, 0.6f, magA));

    context.pilot.setVelocity(linearAmt, angularAmt);

    return true;
  }

}
