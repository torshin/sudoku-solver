package model;

import lombok.Data;
import workers.Formatter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class Field {
    public static final int BLOCK_SIZE = 3;
    private List<ArrayList<Cell>> cells = IntStream.range(0, Cell.SIZE)
            .mapToObj(i -> IntStream.range(0, Cell.SIZE)
                    .mapToObj(j -> new Cell(0, i, j))
                    .collect(Collectors.toCollection(ArrayList::new)))
            .toList();

    private List<Block> rows;
    private List<Block> columns;
    private List<Block> blocks;

    public Field(List<Integer> integers) {
        for (int i = 0; i < integers.size(); i++) {
            int row = i / Cell.SIZE;
            int column = i % Cell.SIZE;
            Integer value = integers.get(i);
            cells.get(row).get(column).setValue(value);
        }
        rows = cells.stream().map(Block::new).toList();
        columns = IntStream.range(0, Cell.SIZE)
                .mapToObj(index -> new Block(cells.stream().map(row -> row.get(index)).toList()))
                .toList();
        blocks = IntStream.range(0, Cell.SIZE)
                .mapToObj(row -> new Block(IntStream.range(0, Cell.SIZE)
                        .mapToObj(col -> {
                            int rowIndex = (row / BLOCK_SIZE) * BLOCK_SIZE + col / BLOCK_SIZE;
                            int colIndex = (row % BLOCK_SIZE) * BLOCK_SIZE + col % BLOCK_SIZE;
                            return cells.get(rowIndex).get(colIndex);
                        })
                        .toList()))
                .toList();
    }

    public void solve() {
        int counter = 0;
        while (solveBlocks() || solvePossibilities()) {
            counter++;
            System.out.println("__________count = " + counter + "________");
            System.out.println(this);
        }
        //        System.out.println("__________count = " + counter++ + "________");
        //        System.out.println(this);

    }

    @Override
    public String toString() {
        return IntStream.range(0, cells.size()).mapToObj(i -> IntStream.range(0, cells.get(i).size())
                .mapToObj(j -> {
                    Cell cell = cells.get(i).get(j);
                    String s = Formatter.black(cell);
                    if (i % BLOCK_SIZE == BLOCK_SIZE - 1 && i != Cell.SIZE - 1) {
                        s = Formatter.underLine(cell);
                    }
                    if (j % BLOCK_SIZE == BLOCK_SIZE - 1 && j != Cell.SIZE - 1) {
                        s = s + "|";
                    } else {
                        s = s + " ";
                    }
                    return s;

                }).collect(Collectors.joining(""))).collect(Collectors.joining("\n"));
    }

    private boolean solveBlock(List<Block> blocks) {
        return blocks.stream().anyMatch(Block::solve);
    }

    private boolean solveBlocks() {
        return (solveBlock(rows) || solveBlock(columns) || solveBlock(blocks));
    }

    private boolean solvePossibilities() {
        return false;
    }
}
