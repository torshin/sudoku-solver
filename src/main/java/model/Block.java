package model;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class Block {
    private List<Cell> cells;

    public Block(List<Cell> cells) {
        this.cells = cells;
    }

    /**
     * @return true if was any changes
     */
    public boolean solve() {
        return occupy() || onlyPossible();
    }

    private boolean occupy() {
        for (int i = 0; i < cells.size(); i++) {
            for (int j = 0; j < cells.size(); j++) {
                if (i == j) {
                    continue;
                }
                if (cells.get(j).impossible(cells.get(i).getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean onlyPossible() {
        Map<Integer, List<Cell>> possibles = IntStream.range(0, Cell.SIZE)
                .boxed()
                .collect(Collectors.toMap(Function.identity(),
                        n -> cells.stream()
                                .filter(cell -> cell.getValue() == 0)
                                .filter(cell -> cell.getPossible().contains(n))
                                .toList()));
        return possibles.entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() == 1)
                .filter(entry -> entry.getValue().get(0).getValue() == 0)
                .findAny()
                .map(e -> e.getValue().get(0).setValue(e.getKey()))
                .orElse(false);
    }
}
