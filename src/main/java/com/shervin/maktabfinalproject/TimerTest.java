package com.shervin.maktabfinalproject;

import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
    private static boolean isTimeOut = false;

    public static void main(final String[] args) {

        int time = 20;

        final Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            int i = time;

            public void run() {
                i--;
                if (i < 0) {
                    timer.cancel();
                    reverseBoolean();
                }
            }
        }, 0, 1000);
    }

    public static void reverseBoolean() {
        isTimeOut = true;
    }

}


