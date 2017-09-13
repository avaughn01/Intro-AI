/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package introai;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author killabeez219
 */
public class FiveSolver {

    public static void main(String[] args) {
        // TODO code application logic here
        new FiveSolver();
    }

    public FiveSolver() {
        // Scanner var
        Scanner input = new Scanner(System.in);
        // Stores user input / is initial start state
        String startState = "";

        // prompt for user input
        System.out.println("Goal State: " + goal());
        System.out.print("Enter 6 digits, 0-5, non-repeating in format XXXXXX: ");
        startState = input.next();
        ids(startState);

    }

    // Iterative depth first search to find goal state
    public void ids(String startState) {
        // The fringe
        Stack<FiveState> fringe = new Stack<>();
        // States already visited
        List<FiveState> visited = new ArrayList<>();
        // List of solution
        List<FiveState> solution = new ArrayList<>();
        // current state
        FiveState curr = null;
        // successors of curr
        List<FiveState> successors = null;
        // True if curr state is goal state
        boolean result = false;

        // the for loop and the && currDepth <= maxDepth part of while loop
        // turn it from depth first search to iterative depth first searh
        for (int maxDepth = 0; maxDepth < Integer.MAX_VALUE; maxDepth++) {
            // Start back at beginning
            visited.clear();
            fringe.clear();
            fringe.push(new FiveState(startState, "S", 0));
            while (!fringe.isEmpty() && fringe.peek().getDepth() <= maxDepth) {
                curr = fringe.pop();
                visited.add(curr);
                // if we did not find goal
                if (!curr.getName().equals(goal())) {
                    // generate curr's children
                    successors = genSuccessors(curr.getName(), curr.getDepth() + 1);
                    // do not want states already visited in successor
                    // cannot use enhanced for loop and .contains()...I believe I have to override States.equal()
                    // I tried, and cannot successfully do it
                    for (int i = 0; i < successors.size(); i++) {
                        for (int j = 0; j < visited.size(); j++) {
                            if (!successors.isEmpty()) {
                                if (successors.get(i).getName().equals(visited.get(j).getName())) {
                                    successors.remove(i);
                                    i = 0;
                                }
                            }
                        }
                    }
                    // Print statements for debugging
                    /*
                    System.out.println("Current State: " + curr.getName() + "-" + curr.getDepth());
                    System.out.print("Successors: ");
                    for (int i = 0; i < successors.size(); i++) {
                        System.out.print(successors.get(i).getName() + "-" + successors.get(i).getDepth() + " ");
                    }
                    System.out.println("\n");
                     */
                    
                    
                    // push remaining successors onto the fringe
                    if (!successors.isEmpty()) {
                        for (FiveState st : successors) {
                            if (st.getDepth() <= maxDepth) {
                                fringe.push(st);
                            }

                        }
                    }
                    
                    // Otherwise the current state is the goal state
                } else {
                    result = true;
                    break;
                }
            }
            if (result) {
                break;
            }
        }
        // Add the states that worked to solution.  This can be done inside the while loop, but I am not sure
        // which way is better
        List<Integer> depths = new ArrayList<Integer>();
        for (int i = visited.size() - 1; i >= 0; i--) {
            if (!depths.contains(visited.get(i).getDepth())) {
                solution.add(visited.get(i));
                depths.add(visited.get(i).getDepth());
            }
        }

        // Print out the solution
        if (!solution.isEmpty()) {
            System.out.print("Solution found: ");
            for (int i = solution.size() - 1; i >= 0; i--) {
                if (i == solution.size() - 1) {
                    System.out.print(solution.get(i).getValue());
                } else {
                    System.out.print("-" + solution.get(i).getValue());
                }
            }
        } else {
            System.out.println("Solution not found");
        }
        System.out.println();
    }

    // generates a states successors, state is string represntation of the puzzle state
    // and depth is the depth of the node
    public List<FiveState> genSuccessors(String state, int depth) {
        // list of successors that will be returned
        List<FiveState> successors = new ArrayList<FiveState>();

        // if move is possible add them to list of successors
        if (!up(state).equals(state)) {
            successors.add(new FiveState(up(state), "U", depth));
        }
        if (!down(state).equals(state)) {
            successors.add(new FiveState(down(state), "D", depth));
        }
        if (!left(state).equals(state)) {
            successors.add(new FiveState(left(state), "L", depth));
        }
        if (!right(state).equals(state)) {
            successors.add(new FiveState(right(state), "R", depth));
        }

        return successors;
    }

    // Representation of what goal is
    public String goal() {
        return "123450";
    }

    // Returns index of empty space
    public int getIndex(String state) {
        int index = 0;
        for (int i = 0; i < state.length(); i++) {
            if (state.charAt(i) == '0') {
                index = i;
            }
        }

        return index;
    }

    // moves the empty space up
    // if not possible returns original string
    public String up(String state) {
        int index = getIndex(state);

        // Swap the characters if possible
        if (index > 2) {
            state = swap(state, index - 3, index);
        }

        return state;
    }

    // moves the empty space down
    // if not possible returns original string
    public String down(String state) {
        int index = getIndex(state);

        if (index < 3) {
            state = swap(state, index, index + 3);
        }

        return state;
    }

    // move the empty space left
    // if not possible returns original string
    public String left(String state) {
        int index = getIndex(state);
        if ((index != 0) && (index != 3)) {
            state = swap(state, index - 1, index);
        }
        return state;
    }

    // move the empty space right
    // if not possible returns original string
    public String right(String state) {
        int index = getIndex(state);
        if ((index != 2) && (index != 5)) {
            state = swap(state, index, index + 1);
        }
        return state;
    }

    // swaps the two characters in string
    // i is index of first character to swap and s is index of second character to swap
    // index i is intended to be < index s
    public String swap(String state, int i, int s) {
        // ensure i < s
        if (i > s) {
            int temp = s;
            s = i;
            i = temp;
        }
        // go until we hit first character
        String temp = state.substring(0, i);
        String temp2 = state.substring(i + 1, s);
        String temp3 = state.substring(s + 1, state.length());

        // create new string and return it
        state = temp + state.charAt(s) + temp2 + state.charAt(i) + temp3;

        return state;

    }
}
