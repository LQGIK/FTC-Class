package edu.ahs.robotics.java;

public class TestClock implements Clock {

    private long time;

    public long getCurrentTime(){
        return System.currentTimeMillis();
    }

    public void setTime(long time){
        this.time = time;
    }

    public long getTime(){
        return time;
    }

    public double getTimePassed(){
        return getCurrentTime() - time;
    }

}
