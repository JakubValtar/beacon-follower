package ev3;


import ev3.behaviourtree.Node;
import ev3.behaviourtree.Action;
import ev3.backgroundtask.FollowLine;
import ev3.backgroundtask.Forward;
import ev3.backgroundtask.ReadSensors;
import ev3.backgroundtask.SteerLeft;
import ev3.backgroundtask.SteerRight;
import ev3.backgroundtask.Stop;
import ev3.backgroundtask.TurnLeft;
import ev3.backgroundtask.TurnRight;
import ev3.behaviourtree.composite.Selector;
import ev3.behaviourtree.composite.Sequence;
import ev3.behaviourtree.Condition;
import ev3.behaviourtree.predicate.Equals;
import ev3.behaviourtree.predicate.GreaterThan;
import ev3.behaviourtree.predicate.InBetween;
import ev3.behaviourtree.predicate.LessThan;
import ev3.value.BeaconDirection;
import ev3.value.BeaconDistance;
import ev3.value.SurfaceColor;
import ev3.behaviourtree.decorator.Failer;
import ev3.behaviourtree.decorator.Invertor;
import ev3.behaviourtree.decorator.UntilSuccess;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.lcd.LCD;
import lejos.robotics.Color;

public class Main {

  private static volatile boolean running = true;

  private static final int SUCCESS_DISTANCE = 25;
  private static final int BEACON_DIRECTION_DEADZONE = 2;

  private static final int LINEAR_SPEED = 20;
  private static final int ANGULAR_SPEED = 10;

  public static void main(String[] args) {

    // Setup listener to stop running on ESCAPE
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

    // Create Pilot and Context
    Pilot pilot = new Pilot(LINEAR_SPEED, ANGULAR_SPEED);
    Context context = new Context(pilot);

    // Defaults
    int obstacleColor = Color.WHITE;
    float obstacleLght = 0.1286f;
    float groundLght = 0.027f;

    // Show menu
    LCD.clear();
    LCD.drawString("ENTER: run\n" +
                       "UP: calibration\n" +
                       "ESC: stop program", 0, 0);

    int buttons = Button.waitForAnyPress();

    // Calibration
    if ((buttons & Button.ID_UP) != 0) {

      // Ground
      LCD.clear();
      LCD.drawString("Put Bobes on\n" +
                         "ground and press\n" +
                         "any button", 0, 0);
      Button.waitForAnyPress();
      context.sensorReader.read(context);
      groundLght = context.surfaceLght;
      System.out.println("ground: " + groundLght);

      // Obstacle
      LCD.clear();
      LCD.drawString("Put Bobes on\n" +
                         "obstacle and\n" +
                         "press any button", 0, 0);
      Button.waitForAnyPress();
      context.sensorReader.read(context);
      obstacleLght = context.surfaceLght;
      obstacleColor = context.surfaceColor;
      System.out.println("obstacle: " + obstacleLght);
    }

    // Actions (run on background thread)
    Node A_FORWARD = new Action(new Forward());
    Node A_STOP = new Action(new Stop());
    Node A_TURN_LEFT = new Action(new TurnLeft());
    Node A_TURN_RIGHT = new Action(new TurnRight());
    Node A_STEER_LEFT = new Action(new SteerLeft());
    Node A_STEER_RIGHT = new Action(new SteerRight());
    Node A_READ_SENSORS = new Action(new ReadSensors());
    Node A_FOLLOW_LINE = new Action(new FollowLine(groundLght, obstacleLght));

    // Conditions (complete immediately)
    Node C_BEACON_VISIBLE = new Condition(
        new BeaconDistance(), new InBetween(1, 1000)
    );
    Node C_BEACON_LEFT = new Condition(
        new BeaconDirection(), new LessThan(-BEACON_DIRECTION_DEADZONE)
    );
    Node C_BEACON_RIGHT = new Condition(
        new BeaconDirection(), new GreaterThan(BEACON_DIRECTION_DEADZONE)
    );
    Node C_BEACON_CLOSE = new Condition(
        new BeaconDistance(), new LessThan(SUCCESS_DISTANCE)
    );
    Node C_ON_OBSTACLE = new Condition(
        new SurfaceColor(), new Equals(obstacleColor)
    );


    // Create the Behavior Tree
    Node tree = new Selector(
        // Always read sensors when starting the tree,
        // return FAIL to keep selector running
        new Failer(A_READ_SENSORS),
        // If beacon is close, stop
        new Sequence(C_BEACON_VISIBLE, C_BEACON_CLOSE, A_STOP),
        // Else if on obstacle, go around obstacle
        new Sequence(C_ON_OBSTACLE,
            // Start turning left on spot
            A_TURN_LEFT,
            // Until
            new UntilSuccess(new Sequence(
                A_READ_SENSORS,
                // Not on obstacle
                new Invertor(C_ON_OBSTACLE)
            )),
            // Until
            new UntilSuccess(new Sequence(
                A_READ_SENSORS,
                new Selector(
                    // Beacon is to the left
                    C_BEACON_LEFT,
                    // Keep following line
                    new Failer(A_FOLLOW_LINE)
                )
            ))
        ),
        // Else if beacon not visible, find beacon
        new Sequence(
            new Invertor(C_BEACON_VISIBLE),
            // Start turning left on spot
            A_TURN_LEFT,
            // Until
            new UntilSuccess(new Sequence(
                A_READ_SENSORS,
                // Beacon visible
                C_BEACON_VISIBLE
            ))
        ),
        // Otherwise follow beacon
        new Selector(
          // If beacon is to the right, steer right
          new Sequence(C_BEACON_RIGHT, A_STEER_RIGHT),
          // Else if beacon is to the left, steer left
          new Sequence(C_BEACON_LEFT, A_STEER_LEFT),
          // Otherwise go forward
          A_FORWARD
        )
    );

    // Loop through behavior tree until done (ESC pressed)
    while (running) {
      tree.run(context);
      sleep(1);
    }

    context.executor.shutdownNow();
  }


  // Utility method for sleeping
  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) { }
  }

}

