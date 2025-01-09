package org.example;

import model.Cell;
import model.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Integer> integers;
        if (args.length == 0) {
            integers = readField();
        } else {
            integers = readArgs(args);
        }
        Field field = new Field(integers);
        Field initial = new Field(integers);
        field.solve();

    }

    private static List<Integer> readArgs(String[] args) {
        return getIntegers(new Scanner(String.join(" ", args)));
    }

    private static List<Integer> readField() {
        Scanner s = new Scanner(System.in);
        return getIntegers(s);
    }

    private static ArrayList<Integer> getIntegers(Scanner s) {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < Cell.SIZE; i++) {
            for (int j = 0; j < Cell.SIZE; j++) {
                integers.add(s.nextInt());
            }
        }
        return integers;
    }
}
