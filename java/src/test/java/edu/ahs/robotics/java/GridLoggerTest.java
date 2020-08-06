package edu.ahs.robotics.java;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GridLoggerTest {

    @Test
    public void writeLn() {
        TestWriter writer = new TestWriter();
        TestClock clock = new TestClock();
        GridLogger gridLogger = new GridLogger(writer, clock);
        clock.setTime(System.currentTimeMillis());

        gridLogger.writeRow(); // Should write nothing

        gridLogger.add("RobotX", 2.4);
        gridLogger.add("RobotY", 3.2);
        String time1 = String.valueOf(clock.getTimePassed());
        gridLogger.writeRow();


        gridLogger.add("RobotX", 3.3);
        gridLogger.add("RobotY", 3.5);
        String time2 = String.valueOf(clock.getTimePassed());
        gridLogger.writeRow();

        List<String> lines = writer.getLines();
        assertEquals("RobotX,RobotY,Time", lines.get(0));
        assertEquals("2.4,3.2," + time1, lines.get(1));
        assertEquals("3.3,3.5," + time2, lines.get(2));

    }

    @Test
    public void ClockTest(){
        TestWriter writer = new TestWriter();
        TestClock clock = new TestClock();
        GridLogger gridLogger = new GridLogger(writer, clock);
        clock.setTime(System.currentTimeMillis());

        gridLogger.add("RobotX", 2.4);
        gridLogger.add("RobotY", 3.2);
        String time1 = String.valueOf(clock.getTimePassed());
        gridLogger.writeRow();

        gridLogger.add("RobotX", 3.3);
        gridLogger.add("RobotY", 3.5);
        gridLogger.writeRow();
        String time2 = String.valueOf(clock.getTimePassed());

        gridLogger.stop();

        List<String> lines = writer.getLines();
        assertEquals("RobotX,RobotY,Time", lines.get(0));
        assertEquals("2.4,3.2," + time1, lines.get(1));
        assertEquals("3.3,3.5," + time2, lines.get(2));
    }

    @Test
    public void FileLogWriterTest(){
        LogWriter writer = new FileLogWriter("/Documents/GridLogTest.csv");
        TestClock clock = new TestClock();
        GridLogger gridLogger = new GridLogger(writer, clock);

        gridLogger.writeRow(); // Should write nothing

        gridLogger.add("RobotX", 2.4);
        gridLogger.add("RobotY", 3.2);
        gridLogger.writeRow();

        gridLogger.add("RobotX", 3.4);
        gridLogger.add("RobotY", 3.5);
        gridLogger.writeRow();
    }

    @Test
    public void add() {
    }

    @Test
    public void writeRow() {
    }

    @Test
    public void stop() {
        TestWriter writer = new TestWriter();
        TestClock clock = new TestClock();
        GridLogger gridLogger = new GridLogger(writer, clock);

        gridLogger.add("RobotX", 2.4);
        gridLogger.add("RobotY", 3.2);
        gridLogger.writeRow();

        gridLogger.stop();
        assertEquals(true, writer.isStopCalled());
    }
}