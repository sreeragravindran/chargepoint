package com.chargepoint.gameoflife;

import com.chargepoint.gameoflife.evolution.Evolution;
import com.chargepoint.gameoflife.evolution.MultiThreadedEvolution;
import com.chargepoint.gameoflife.evolution.SingleThreadedEvolution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

public class Universe {

    Cell[][] universe;
    Evolution evolution;

    public Universe(int[][] cells) {
        // todo: ideally the dependency should be injected
        this.evolution = new SingleThreadedEvolution();
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
        evolution.evolve(this);
    }

    public void display() {
        for (Cell[] cells : universe) {
            for (Cell cell : cells) {
                System.out.print(cell.toString());
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
    public Stream<Cell> getNeighbours(Cell cell) {
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

}
