package ev3.behaviour.action.backgroundtask;

import ev3.Context;
import ev3.behaviour.action.Action;

public class FollowLine implements Action.BackgroundTask
{
    private float obstacleColor;
    private float groundColor;
    private float midColor;
    private double kp;
    private double lightnessAverage = -1;
    private double averageRatio = 0.6;

    public FollowLine(float obstacleColor, float groundColor)
    {
        this.obstacleColor = obstacleColor;
        this.groundColor = groundColor;
        midColor = (obstacleColor + groundColor) / 2;
        kp = 2;
    }

    @Override
    public boolean runInBackground(Context context)
    {
        if (lightnessAverage == -1) {
            lightnessAverage = context.surfaceLightness;
        } else {
            lightnessAverage = averageRatio * lightnessAverage +
                    (1 - averageRatio) * context.surfaceLightness;
        }
        double diff = midColor - lightnessAverage;
        double steerRate = kp * diff;
        double linearAmt = 1.5f - (Math.abs(diff) / (Math.abs(obstacleColor - groundColor)/2));
        linearAmt = Math.max(0.5f, linearAmt);
        context.pilot.steer(linearAmt, steerRate);

        return true;
    }
}
