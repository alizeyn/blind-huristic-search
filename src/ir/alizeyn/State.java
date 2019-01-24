package ir.alizeyn;

import java.util.ArrayList;

/**
 * 
 * State interface from which problem states inherit. Defines a method to check
 * if the current state is a goal, generate successors, and find the cost to
 * come to the current state.
 * 
 * @author Michael Langston && Gabe Ferrer
 * 
 */
public interface State
{
	// determine if current state is goal
	boolean isGoal();

	// generate successors to the current state
	ArrayList<State> genSuccessors();

	// determine cost from initial state to THIS state
	double findCost();

	// print the current state
    void printState();

	// compare the actual state data
    boolean equals(State s);
}
