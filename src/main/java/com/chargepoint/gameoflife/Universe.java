package com.chargepoint.gameoflife;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Universe {

    private Cell[][] universe;

    public Universe(int[][] cells) {
        // build the Universe
        this.universe = new Cell[cells.length][cells[0].length];
        init(cells);
    }

    private void init(int[][] cells) {
        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[r].length; c++) {
                universe[r][c] = new Cell(cells[r][c], Position.of(r, c));
            }
        }
    }

    public int[][] getCells() {
        int[][] cells = new int[universe.length][universe[0].length];
        for (int r = 0; r < universe.length; r++) {
            for (int c = 0; c < universe[r].length; c++) {
                cells[r][c] = universe[r][c].getCurrentState();
            }
        }
        return cells;
    }

    /***
     * creates the next generation of cells based on the rules of the universe :
     */
    public void tick() {
        createNextGeneration();
    }

    public void display() {
        for (int r = 0; r < universe.length; r++) {
            for (int c = 0; c < universe[r].length; c++) {
                System.out.print(universe[r][c].toString());
            }
            System.out.println();
        }
    }

    /***
     * Every cell interacts with its eight neighbors, which
     * are the cells that are horizontally, vertically, or diagonally adjacent.
     * @param cell
     * @return
     */
    private Stream<Cell> getNeighbours(Cell cell) {
        var currentPosition = cell.getPosition();
        var adjacentPositions = new ArrayList<Position>();

        adjacentPositions.add(Position.of(currentPosition.getRow() - 1, currentPosition.getColumn() - 1));
        adjacentPositions.add(Position.of(currentPosition.getRow() - 1, currentPosition.getColumn()));
        adjacentPositions.add(Position.of(currentPosition.getRow() - 1, currentPosition.getColumn() + 1));
        adjacentPositions.add(Position.of(currentPosition.getRow(), currentPosition.getColumn() - 1));
        adjacentPositions.add(Position.of(currentPosition.getRow(), currentPosition.getColumn() + 1));
        adjacentPositions.add(Position.of(currentPosition.getRow() + 1, currentPosition.getColumn() - 1));
        adjacentPositions.add(Position.of(currentPosition.getRow() + 1, currentPosition.getColumn()));
        adjacentPositions.add(Position.of(currentPosition.getRow() + 1, currentPosition.getColumn() + 1));

        var adjacentCells = new ArrayList<Cell>();
        for (Position position : adjacentPositions) {
            if (isValid(position)) {
                adjacentCells.add(universe[position.getRow()][position.getColumn()]);
            }
        }

        return adjacentCells.stream();
    }

    /***
     * Rules for life and death
     *      * 1. Any live cell with fewer than two live neighbors dies as if caused by underpopulation.
     *      * 2. Any live cell with two or three live neighbors lives on to the next generation.
     *      * 3. Any live cell with more than three live neighbors dies, as if by overcrowding.
     *      * 4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
     */
    private void createNextGeneration() {
        for (int r = 0; r < universe.length; r++) {
            for (int c = 0; c < universe[r].length; c++) {
                // update cell state
                var cell = universe[r][c];
                var livingNeighbours = getLivingNeighbours(getNeighbours(cell));
                if (cell.isAlive()) {
                    // underpopulation or overcrowding
                    if (isUnderPopulated(livingNeighbours) || isOverCrowded(livingNeighbours)) {
                        cell.setToDie();
                    } else {
                        cell.setToLive();
                    }
                } else if (livingNeighbours == 3)
                    // reproduction
                    cell.setToLive();
            }
        }

        // apply new state
        for (int r = 0; r < universe.length; r++) {
            for (int c = 0; c < universe[r].length; c++) {
                universe[r][c].changeState();
            }
        }
    }

    // helper methods
    private boolean isValid(Position pos) {
        return pos.getRow() >= 0 && pos.getRow() < getRowSize() && pos.getColumn() >= 0 && pos.getColumn() < getColumnSize();
    }

    private int getRowSize() {
        return universe.length;
    }

    private int getColumnSize() {
        return universe[0].length;
    }

    private boolean isUnderPopulated(long count) {
        return count < 2;
    }

    private boolean isOverCrowded(long count) {
        return count > 3;
    }

    private long getLivingNeighbours(Stream<Cell> neighbours) {
        return neighbours.filter(Cell::isAlive).count();
    }

}
