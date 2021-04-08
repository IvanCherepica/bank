package com.bank.example.util;

public class RandomUtil {

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static double getRandomNumber(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }
}