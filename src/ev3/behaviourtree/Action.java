package ev3.behaviourtree;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import ev3.Context;

// Executes a task on a background thread
// Returns RUNNING until task completes
public class Action implements Node {

  public interface BackgroundTask {
    boolean runInBackground(Context context);
  }

  private final BackgroundTask task;

  private Future<Boolean> future;

  public Action(BackgroundTask task) {
    this.task = task;
  }

  @Override
  public final Result run(final Context context) {
    if (future == null) {
      future = context.executor.submit(new Callable<Boolean>() {
        public Boolean call() {
          return task.runInBackground(context);
        }
      });
    }
    if (future.isDone()) {
      try {
        boolean result = future.get();
        return result ? Result.SUCCESS : Result.FAIL;
      } catch (InterruptedException | ExecutionException | CancellationException e) {
        e.printStackTrace();
        return Result.FAIL;
      } finally {
        future = null;
      }
    }
    return Result.RUNNING;
  }

}
