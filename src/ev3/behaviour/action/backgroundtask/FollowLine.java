package ev3.behaviour.action.backgroundtask;

import ev3.Context;
import ev3.behaviour.action.Action;

public class FollowLine implements Action.BackgroundTask
{
    private float obstacleColor;
    private float groundColor;
    private float midColor;
    private double kp;

    public FollowLine(float obstacleColor, float groundColor)
    {
        this.obstacleColor = obstacleColor;
        this.groundColor = groundColor;
        midColor = Math.min(obstacleColor, groundColor) + (Math.max(obstacleColor, groundColor) - Math.min(obstacleColor, groundColor)) / 2;
        kp = 1;
    }

    @Override
    public boolean runInBackground(Context context)
    {
        double steerRate = kp * (midColor - context.surfaceLightness);

        context.pilot.steer(steerRate);

        return true;
    }
}
