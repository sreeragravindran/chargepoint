package com.chargepoint.gameoflife;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UniverseTest {

    @Test
    public void ShouldCreateNextGenerationSuccessfully() {
        int[][] cells = new int[][]{
                {0, 1, 0},
                {0, 0, 1},
                {1, 1, 1},
                {0, 0, 0},
        };

        Universe universe = new Universe(cells);

        // first generation
        System.out.println("1st Generation");
        universe.tick();
        universe.display();

        var expectedCells = new int[][] {
                {0, 0, 0},
                {1, 0, 1},
                {0, 1, 1},
                {0, 1, 0}
        };

        var actualCells = universe.getCells();

        assertMatrix(expectedCells, actualCells);
        expectedCells = new int[][] {
                {0, 0, 0},
                {0, 0, 1},
                {1, 0, 1},
                {0, 1, 1}
        };

        System.out.println("2nd Generation");
        // 2nd generation
        universe.tick();
        universe.display();
        actualCells = universe.getCells();
        assertMatrix(expectedCells, actualCells);

        // 3rd generation
        System.out.println("3rd Generation");
        universe.tick();
        universe.display();

        System.out.println("4th Generation");
        universe.tick();
        universe.display();
    }

    public void assertMatrix(int[][] expected, int[][] actual) {
        for(int r=0; r < actual.length; r++) {
            for(int c=0; c < actual[0].length; c++) {
                Assertions.assertEquals(expected[r][c], actual[r][c]);
            }
        }
    }

    @Test
    public void DisplayGenerations() {
        int[][] cells = new int[25][25];

        cells[11][12] = 1;
        cells[12][13] = 1;
        cells[13][11] = 1;
        cells[13][12] = 1;
        cells[13][13] = 1;

        Universe universe = new Universe(cells);
        System.out.println("1st Generation");
        universe.display();

        System.out.println("2st Generation");
        universe.tick();
        universe.display();

        System.out.println("3rd Generation");
        universe.tick();
        universe.display();

        System.out.println("4th Generation");
        universe.tick();
        universe.display();
    }
}
