package ev3.behaviour.action.backgroundtask;

import ev3.Context;
import ev3.behaviour.action.Action;

public class FollowLine implements Action.BackgroundTask
{
    private float obstacleColor;
    private float groundColor;

    public FollowLine(float obstacleColor, float groundColor)
    {
        this.obstacleColor = obstacleColor;
        this.groundColor = groundColor;
    }

    @Override
    public boolean runInBackground(Context context)
    {
        // clip to (ground -> obstacle)
        float diff = clip(context.surfaceLightness, groundColor, obstacleColor);
        // from (ground -> obstacle) to (0 -> 1)
        diff = norm(diff, obstacleColor, groundColor);
        // from (0 -> 1) to (-1 -> 1)
        diff = 2*diff - 1;

        int sign = (int) Math.signum(diff);
        float mag = (float) Math.pow(Math.abs(diff), 4);

        float linearAmt = mix(0.2f, 1.2f, 1 - mag);
        float angularAmt = mix(0.0f, 0.9f, sign * mag);
        
        context.pilot.steer(linearAmt, angularAmt);

        return true;
    }

    static float mix(float a, float b, float ratio) {
        return a * (1 - ratio) + b * ratio;
    }

    static float clip(float val, float a, float b) {
        val = Math.min(val, Math.max(a, b));
        val = Math.max(val, Math.min(a, b));
        return val;
    }

    static float norm(float val, float a, float b) {
        return (val - a) / (b - a);
    }
}
