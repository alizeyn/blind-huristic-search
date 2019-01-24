package ir.alizeyn;

import java.util.*;

public class BDSearch {

    public static State initialState;

    public static void search(int[] board) {

        int[] goalBoard = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};

        initialState = new EightPuzzleState(board);
        State goalState = new EightPuzzleState(goalBoard);

        SearchNode root = new SearchNode(initialState);
        SearchNode goal = new SearchNode(goalState);

        Queue<SearchNode> forwardQueue = new LinkedList<>();
        Queue<SearchNode> backwardQueue = new LinkedList<>();

        forwardQueue.add(root);
        backwardQueue.add(goal);

        performSearch(forwardQueue, backwardQueue);
    }

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

    private static boolean isInitialState(State state) {
        return state.equals(initialState);
    }

    private static SearchNode queueContainsNode(Queue<SearchNode> q, SearchNode targetNode) {
        for (SearchNode n :
                q) {
            if (n.getCurState().equals(targetNode.getCurState())) {
                return n;
            }
        }
        return null;
    }

    private static void bfs(SearchNode n, Queue<SearchNode> q) {
        ArrayList<State> tempSuccessors = n.getCurState()
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
            SearchNode newNode = new SearchNode(n,
                    tempSuccessors.get(i), n.getCost()
                    + tempSuccessors.get(i).findCost(), 0);

            if (!checkRepeats(newNode)) {
                q.add(newNode);
            }
        }
    }

    // The goal state has been found. Print the path it took to get to
    // it.
    private static void success(SearchNode node, int searchCount) {
        // Use a stack to track the path from the starting state to the
        // goal state
        Stack<SearchNode> solutionPath = new Stack<SearchNode>();
        solutionPath.push(node);
        node = node.getParent();

        while (node.getParent() != null) {
            solutionPath.push(node);
            node = node.getParent();
        }
        solutionPath.push(node);

        // The size of the stack before looping through and emptying it.
        int loopSize = solutionPath.size();

        for (int i = 0; i < loopSize; i++) {
            node = solutionPath.pop();
            node.getCurState().printState();
            System.out.println();
            System.out.println();
        }
        System.out.println("The cost was: " + node.getCost());
    }

    private static void performSearch(Queue<SearchNode> fq,
                                      Queue<SearchNode> bq) {
        int searchCount = 1;

        while (!fq.isEmpty() && !bq.isEmpty()) {

            if (!fq.isEmpty()) {

                SearchNode tempNode = fq.poll();
                SearchNode nodeExistOnBackwardQueue = queueContainsNode(bq, tempNode);
                if (tempNode.getCurState().isGoal() || nodeExistOnBackwardQueue != null) {
                    if (nodeExistOnBackwardQueue != null) {
                        success(nodeExistOnBackwardQueue, searchCount);
                    }
                    success(tempNode, searchCount);
                    System.exit(0);
                } else {
                    bfs(tempNode, fq);
                    searchCount++;
                }

            }

            if (!bq.isEmpty()) {

                SearchNode tempNode = bq.poll();
                SearchNode nodeExistOnForwardQueue = queueContainsNode(fq, tempNode);
                if (isInitialState(tempNode.getCurState()) || nodeExistOnForwardQueue != null) {
                    success(tempNode, searchCount);
                    if (nodeExistOnForwardQueue != null) {
                        success(nodeExistOnForwardQueue, searchCount);
                    }
                    System.exit(0);
                } else {
                    bfs(tempNode, bq);
                    searchCount++;
                }
            }

        }

    }
}
