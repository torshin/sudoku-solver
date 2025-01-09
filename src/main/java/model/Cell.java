package model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class Cell {
    public static final int SIZE = 9;
    private final static Set<Integer> POSSIBLE = IntStream.rangeClosed(1, SIZE).boxed().collect(Collectors.toSet());
    private int value = 0;
    private HashSet<Integer> impossible = new HashSet<>(SIZE - 1);
    private int row;
    private int col;


    public Cell(int value, int row, int col) {
        if (value < 0 || value > SIZE) {
            return;
        }
        this.value = value;
        this.row = row;
        this.col = col;
    }

    public Set<Integer> getPossible() {
        HashSet<Integer> integers = new HashSet<>(POSSIBLE);
        integers.removeAll(impossible);
        return integers;
    }

    /**
     * @return true if was any changes
     */
    public boolean impossible(int value) {
        boolean add = impossible.add(value);
        Set<Integer> possibles = getPossible();
        if (possibles.size() == 1) {
            if (setValue(possibles.iterator().next())) {
                return true;
            }
        }
        return add;
    }

    public boolean setValue(Integer value) {
        if (this.value == 0) {
            this.value = value;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return value == 0 ? " " : value + "";
    }
}
