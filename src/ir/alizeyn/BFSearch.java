package ir.alizeyn;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Defines a Bredth-First search to be performed on a qualifying puzzle.
 * Currently supports 8puzzle and FWGC.
 *
 * @author Michael Langston && Gabe Ferrer
 */
public class BFSearch {

    public static void search(int[] board) {
        SearchNode root = new SearchNode(new EightPuzzleState(board));
        Queue<SearchNode> queue = new LinkedList<SearchNode>();

        queue.add(root);

        performSearch(queue);
    }

    /*
     * Helper method to check to see if a SearchNode has already been evaluated.
     * Returns true if it has, false if it hasn't.
     */
    private static boolean checkRepeats(SearchNode n) {
        boolean retValue = false;
        SearchNode checkNode = n;

        // While n's parent isn't null, check to see if it's equal to the node
        // we're looking for.
        while (n.getParent() != null && !retValue) {
            if (n.getParent().getCurState().equals(checkNode.getCurState())) {
                retValue = true;
            }
            n = n.getParent();
        }

        return retValue;
    }

    /**
     * Performs a BFSearch using q as the search space
     *
     * @param q - A SearchNode queue to be populated and searched
     */
    public static void performSearch(Queue<SearchNode> q) {
        int searchCount = 1; // counter for number of iterations

        while (!q.isEmpty()) // while the queue is not empty
        {
            SearchNode tempNode = q.poll();

            if (!tempNode.getCurState().isGoal()) // if tempNode is not the goal
            // state
            {
                ArrayList<State> tempSuccessors = tempNode.getCurState()
                        .genSuccessors(); // generate tempNode's immediate
                // successors

                /*
                 * Loop through the successors, wrap them in a SearchNode, check
                 * if they've already been evaluated, and if not, add them to
                 * the queue
                 */
                for (int i = 0; i < tempSuccessors.size(); i++) {
                    // second parameter here adds the cost of the new node to
                    // the current cost total in the SearchNode
                    SearchNode newNode = new SearchNode(tempNode,
                            tempSuccessors.get(i), tempNode.getCost()
                            + tempSuccessors.get(i).findCost(), 0);

                    if (!checkRepeats(newNode)) {
                        q.add(newNode);
                    }
                }
                searchCount++;
            } else
            // The goal state has been found. Print the path it took to get to
            // it.
            {
                // Use a stack to track the path from the starting state to the
                // goal state
                Stack<SearchNode> solutionPath = new Stack<SearchNode>();
                solutionPath.push(tempNode);
                tempNode = tempNode.getParent();

                while (tempNode.getParent() != null) {
                    solutionPath.push(tempNode);
                    tempNode = tempNode.getParent();
                }
                solutionPath.push(tempNode);

                // The size of the stack before looping through and emptying it.
                int loopSize = solutionPath.size();

                for (int i = 0; i < loopSize; i++) {
                    tempNode = solutionPath.pop();
                    tempNode.getCurState().printState();
                    System.out.println();
                    System.out.println();
                }
                System.out.println("The cost was: " + tempNode.getCost());

                System.exit(0);
            }
        }

        // This should never happen with our current puzzles.
        System.out.println("Error! No solution found!");
    }
}
