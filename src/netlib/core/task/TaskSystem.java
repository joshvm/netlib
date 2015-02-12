package netlib.core.task;

import java.util.Timer;

public class TaskSystem {

    private final Timer timer;

    public TaskSystem(){
        timer = new Timer();
    }

    public void submit(final Task task){
        timer.scheduleAtFixedRate(task.tt, task.getDelay(), task.getPeriod());
    }

    public void shutdown(){
        timer.cancel();
        timer.purge();
    }
}
