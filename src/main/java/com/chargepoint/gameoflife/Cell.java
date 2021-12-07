package com.chargepoint.gameoflife;

/*
Represents the living / dead cell
 */
public class Cell {

    private static final int LIVE = 1;
    private static final int DEAD = 0;

    private int currentState;
    private int newState;

    private Position position;

    public Cell(int state, Position position) {
        this.currentState = state;
        this.position = position;
    }

    public int getCurrentState() {
        return currentState;
    }

    public int getNewState() {
        return newState;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isAlive() {
        return getCurrentState() == LIVE;
    }

    public boolean isDead() {
        return getCurrentState() != LIVE;
    }

    private void setNewState(int newState) {
        this.newState = newState;
    }

    public void setToDie() {
        this.setNewState(DEAD);
    }

    public void setToLive() {
        this.setNewState(LIVE);
    }

    public void changeState() {
        this.currentState = this.newState;
        this.newState = DEAD;
    }

    @Override
    public String toString() {
       return " " + currentState + " ";
    }
}
