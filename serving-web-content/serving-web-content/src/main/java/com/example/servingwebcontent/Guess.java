package com.example.servingwebcontent;

import java.util.Random;

public class Guess {

    private int number;
    private int attempt;
    private int testNumber;
    private int prize;
    private boolean win;

    public Guess() {

        Random rand = new Random();
        prize = 100000;
        number = rand.nextInt(10)+1;
    }

    public void newNumber()
    {
        Random rand = new Random();
        number = rand.nextInt(10)+1;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public int getNumber() {
        return number;
    }

    public int getAttempt() {
        return attempt;
    }

    public int getPrize() {
        return prize;
    }

    public boolean isWin() {
        return win;
    }

    public void reset()
    {
        prize = 100000;
        attempt = 0;
        win = false;
    }

    public boolean compare()
    {
        return testNumber == number;
    }

    public void fail()
    {
        if(!this.compare())
        {
            prize -= 10000;
            attempt +=1;

        }
    }


    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }
}
