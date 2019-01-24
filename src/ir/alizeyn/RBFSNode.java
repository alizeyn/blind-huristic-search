package ir.alizeyn;

public class RBFSNode {

    private State curState;
    private RBFSNode parent;
    private double f; // cost to get to this state
    private double fLimit; // heuristic cost

    // Constructor for the root SearchNode
    public RBFSNode(State s) {
        curState = s;
        parent = null;
        f = 0;
        fLimit = Integer.MAX_VALUE;
    }

    // Constructor for all other SearchNodes
    public RBFSNode(RBFSNode prev, State s, double f) {
        parent = prev;
        curState = s;
        this.f = f;
    }


    public State getCurState() {
        return curState;
    }


    public RBFSNode getParent() {
        return parent;
    }


    public double getCost() {
        return f;
    }


    public double getFLimit() {
        return fLimit;
    }


    public void setFLimit(double fLimit) {
        this.fLimit = fLimit;
    }

}
