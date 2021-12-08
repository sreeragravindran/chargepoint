package com.chargepoint.gameoflife;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class UniverseIterator implements Iterator<Cell> {

    Cell[][] dataset;
    private int rowIndex;
    private int columnIndex;

    public UniverseIterator(Universe universe) {
        this.dataset = universe.universe;
    }

    @Override
    public boolean hasNext() {
        if (rowIndex >= dataset.length)
            return false;
        return columnIndex < dataset[rowIndex].length || rowIndex != dataset.length - 1;
    }

    @Override
    public Cell next() {
        if (!hasNext())
            throw new NoSuchElementException();
        if (columnIndex >= dataset[rowIndex].length) {
            rowIndex++;
            columnIndex = 0;
        }
        return dataset[rowIndex][columnIndex++];
    }
}
