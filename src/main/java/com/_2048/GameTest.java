package com._2048;

import java.util.HashMap;
import java.util.Scanner;

public class GameTest {

    public static void main(String[] args) {

        Game g = new Game(4);
        Scanner scanner = new Scanner(System.in);
        HashMap<String, Integer> m = new HashMap<>();
        m.put("s", 0); // d
        m.put("d", 1); // r
        m.put("w", 2); // u
        m.put("a", 3); // l
        while (true) {
            g.dumpArr();

            System.out.print("Enter move (w,a,s,d): ");

            int move = m.get(scanner.next());

            g.parseMove(move);
            g.addRandom();
        }
    }
}
