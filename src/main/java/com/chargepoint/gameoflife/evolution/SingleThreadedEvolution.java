package com.chargepoint.gameoflife.evolution;

import com.chargepoint.gameoflife.Cell;
import com.chargepoint.gameoflife.Universe;
import com.chargepoint.gameoflife.UniverseIterator;

public class SingleThreadedEvolution implements Evolution {

    /***
     * Rules for life and death
     *      * 1. Any live cell with fewer than two live neighbors dies as if caused by underpopulation.
     *      * 2. Any live cell with two or three live neighbors lives on to the next generation.
     *      * 3. Any live cell with more than three live neighbors dies, as if by overcrowding.
     *      * 4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
     */
    @Override
    public void evolve(Universe universe) {
        var startTime = System.currentTimeMillis();
        var cells = new UniverseIterator(universe);
        while(cells.hasNext()) {
            evolve(cells.next(), universe);
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
