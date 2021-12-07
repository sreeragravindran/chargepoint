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
        universe.tick();

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

        // 2nd generation
        universe.tick();
        actualCells = universe.getCells();
        assertMatrix(expectedCells, actualCells);
    }

    public void assertMatrix(int[][] expected, int[][] actual) {
        for(int r=0; r < actual.length; r++) {
            for(int c=0; c < actual[0].length; c++) {
                Assertions.assertEquals(expected[r][c], actual[r][c]);
            }
        }
    }
}
