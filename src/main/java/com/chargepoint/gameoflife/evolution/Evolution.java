package com.chargepoint.gameoflife.evolution;

import com.chargepoint.gameoflife.Cell;
import com.chargepoint.gameoflife.Universe;

import java.util.stream.Stream;

public interface Evolution {
    public void evolve(Universe universe);

    default boolean isUnderPopulated(long count) {
        return count < 2;
    }

    default boolean isOverCrowded(long count) {
        return count > 3;
    }

    default long getLivingNeighbours(Stream<Cell> neighbours) {
        return neighbours.filter(Cell::isAlive).count();
    }
}
