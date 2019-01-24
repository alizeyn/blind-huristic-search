package ir.alizeyn;

import java.util.Scanner;

public class ProblemSolver {

    /*
     * We expect arguments in the form:
     *
     * ./ProblemSolver <-d> dfs/bfs/aso/asm <initial state> <optional parameter>
     *
     * Example: ./ProblemSolver dfs 0 1 2 3 4 5 6 7 8
     *
     * See Readme for more information.
     */
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        System.out.println("What is Initial State (example : 0 1 2 3 4 5 6 7 8) : \n");

        int[] startingStateBoard = dispatchEightPuzzle(reader.nextLine().split(" "));

        System.out.println("Which Algorithm ?\n" +
                "1 - dfs\n" +
                "2 - bfs\n" +
                "3 - bds\n" +
                "4 - ucs\n" +
                "5 - aso\n" +
                "6 - rbfs\n");

        int choice = reader.nextInt();

        switch (choice) {
            case 1:
                DFSearch.search(startingStateBoard);
                break;
            case 2:
                BFSearch.search(startingStateBoard);
                break;
            case 3:
                BDSearch.search(startingStateBoard);
                break;
            case 4:
                UCSearch.search(startingStateBoard);
                break;
            case 5:
                AStarSearch.search(startingStateBoard, 'o');
                break;
            case 6:
                RBFSearch.search(startingStateBoard);
                break;

        }
    }


    private static int[] dispatchEightPuzzle(String[] a) {
        int[] initState = new int[9];
        for (int i = 0; i < a.length; i++) {
            initState[i] = Integer.parseInt(a[i]);
        }
        return initState;
    }
}
