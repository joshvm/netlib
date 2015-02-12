package netlib.core.task;

import java.util.TimerTask;

public abstract class Task implements Runnable{

    private final int delay;
    private final long period;

    final TimerTask tt;

    public Task(final int delay, final long period){
        this.delay = delay;
        this.period = period;

        tt = new TimerTask(){
            public void run(){
                Task.this.run();
            }
        };
    }

    public Task(final long period){
        this(0, period);
    }

    public int getDelay(){
        return delay;
    }

    public long getPeriod(){
        return period;
    }

    public boolean stop(){
        return tt.cancel();
    }
}
