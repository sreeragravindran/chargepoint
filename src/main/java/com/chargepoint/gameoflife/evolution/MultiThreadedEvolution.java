package com.chargepoint.gameoflife.evolution;

import com.chargepoint.gameoflife.Cell;
import com.chargepoint.gameoflife.Universe;
import com.chargepoint.gameoflife.UniverseIterator;
import com.chargepoint.gameoflife.evolution.Evolution;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadedEvolution implements Evolution {

    @Override
    public void evolve(Universe universe)  {

        var startTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        var cells = new UniverseIterator(universe);
        while(cells.hasNext()) {
            executorService.submit(() -> evolve(cells.next(), universe));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(100000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException iex) {
            // swallow exception
        }
        updateState(universe);

        var timeElapsed = System.currentTimeMillis() - startTime;
        System.out.println("Time taken to evolve : " + timeElapsed + " ms");
    }

    private void evolve(Cell cell, Universe universe) {
        var livingNeighbours = getLivingNeighbours(universe.getNeighbours(cell));
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

    private void updateState(Universe universe) {
        var cells = new UniverseIterator(universe);
        // apply new state
        while(cells.hasNext()) {
            cells.next().changeState();
        }
    }
}
