package ev3;


import ev3.behaviour.Node;
import ev3.behaviour.action.Action;
import ev3.behaviour.action.backgroundtask.Forward;
import ev3.behaviour.action.backgroundtask.ReadSensors;
import ev3.behaviour.action.backgroundtask.SteerLeft;
import ev3.behaviour.action.backgroundtask.SteerRight;
import ev3.behaviour.action.backgroundtask.Stop;
import ev3.behaviour.action.backgroundtask.TurnLeft;
import ev3.behaviour.action.backgroundtask.TurnRight;
import ev3.behaviour.composite.Selector;
import ev3.behaviour.composite.Sequence;
import ev3.behaviour.condition.FloatCondition;
import ev3.behaviour.condition.IntCondition;
import ev3.behaviour.condition.predicate.Equals;
import ev3.behaviour.condition.predicate.GreaterThan;
import ev3.behaviour.condition.predicate.InBetween;
import ev3.behaviour.condition.predicate.LessThan;
import ev3.behaviour.condition.value.BeaconDirection;
import ev3.behaviour.condition.value.BeaconDistance;
import ev3.behaviour.condition.value.SurfaceColor;
import ev3.behaviour.decorator.Failer;
import ev3.behaviour.decorator.Invertor;
import ev3.behaviour.decorator.UntilSuccess;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.robotics.Color;

public class Main {

  private static volatile boolean running = true;

  public static void main(String[] args) {

    Button.ESCAPE.addKeyListener(new KeyListener() {
      @Override
      public void keyPressed(Key k) {
        running = false;
      }

      @Override
      public void keyReleased(Key k) {
        running = false;
      }
    });


    Context context = new Context();

    float successDistance = 5;
    int obstacleColor = Color.BLUE;
    float directionLimit = 5;

    Node A_FORWARD = new Action(new Forward());
    Node A_STOP = new Action(new Stop());
    Node A_TURN_LEFT = new Action(new TurnLeft());
    Node A_TURN_RIGHT = new Action(new TurnRight());
    Node A_STEER_LEFT = new Action(new SteerLeft());
    Node A_STEER_RIGHT = new Action(new SteerRight());
    Node A_READ_SENSORS = new Action(new ReadSensors());

    Node C_BEACON_VISIBLE = new Invertor(
        new FloatCondition(
            new BeaconDistance(),
            new InBetween(1, 10000)
        )
    );

    Node C_BEACON_LEFT = new FloatCondition(
        new BeaconDirection(),
        new LessThan(-directionLimit)
    );

    Node C_BEACON_RIGHT = new FloatCondition(
        new BeaconDirection(),
        new GreaterThan(directionLimit)
    );

    Node C_BEACON_CLOSE = new FloatCondition(
        new BeaconDistance(),
        new LessThan(successDistance)
    );

    Node C_ON_OBSTACLE = new IntCondition(
        new SurfaceColor(),
        new Equals(obstacleColor)
    );

    Node tree = new UntilSuccess(new Failer(new Sequence(
        A_READ_SENSORS,
        new Selector(
            C_BEACON_CLOSE,
            new Sequence(C_ON_OBSTACLE, A_STOP, A_TURN_LEFT,
                new UntilSuccess(new Invertor(C_ON_OBSTACLE)),
                A_FORWARD,
                new UntilSuccess(new Selector(
                    C_BEACON_LEFT,
                    new Failer(new Selector(
                        new Sequence(C_ON_OBSTACLE, A_STEER_LEFT),
                        A_STEER_RIGHT
                    ))
                ))
            ),
            new Sequence(
                C_BEACON_VISIBLE,
                A_STOP,
                A_TURN_LEFT,
                new UntilSuccess(C_BEACON_VISIBLE),
                A_FORWARD
            ),
            new Sequence(C_BEACON_RIGHT, A_STEER_RIGHT),
            new Sequence(C_BEACON_LEFT, A_STEER_LEFT),
            A_FORWARD
        )
    )));

    while (running) {
      tree.run(context);
      Thread.yield();
    }
  }

}
