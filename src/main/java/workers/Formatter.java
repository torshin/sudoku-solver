package workers;

import model.Cell;

public class Formatter {
    public static String black(Cell cell) {
        return "\033[0;30m" + cell + "\033[0m";
    }

    public static String underLine(Cell cell) {
        return "\033[4;30m" + cell + "\033[0m";
    }
}
