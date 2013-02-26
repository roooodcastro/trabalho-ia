package trabalhoia.algoritmos;

import java.util.List;
import trabalhoia.estruturas.GoalTest;
import trabalhoia.estruturas.MaxValueGoalTest;
import trabalhoia.estruturas.MinValueGoalTest;
import trabalhoia.estruturas.Node;
import trabalhoia.estruturas.Problem;
import trabalhoia.estruturas.State;

public class HillClimbingSearch {

    public enum SearchOutcome {

        FAILURE, SOLUTION_FOUND
    };

    public enum SearchType {

        LOCAL_MIN, LOCAL_MAX
    };
    private Problem problem = null;
    private SearchOutcome outcome = null;
    private State lastState = null;
    private GoalTest goalTest;

    public HillClimbingSearch(Problem problem) {
        this.problem = problem;
    }

    public SearchOutcome search(SearchType type) throws Exception {
        outcome = null;
        lastState = null;
        double interval = problem.getMaxInterval() - problem.getMinInterval();
        Node current = new Node(problem.getInitialState(), null, interval  / 100);
        Node neighbor = null;
        while (outcome == null) {
            List<Node> children = current.expandNode();
            neighbor = getBestValuedNodeFrom(children, type);
            if ((neighbor == null) || !isNodeBetter(current, neighbor, type)) {
                if (goalTest.isGoal(current)) {
                    outcome = SearchOutcome.SOLUTION_FOUND;
                }
                lastState = current.getState();
//                return SearchUtils.actionsFromNodes(current.getPathFromRoot());
            }
            current = neighbor;
        }
        return outcome;
    }

    public SearchOutcome getOutcome() {
        return outcome;
    }

    public Object getLastSearchState() {
        return lastState;
    }

    private boolean isStateValid(State state) {
        return state.getX() >= problem.getMinInterval() && state.getX() <= problem.getMaxInterval();
    }

    private Node getBestValuedNodeFrom(List<Node> children, SearchType type) {
        double betterValue = Double.NEGATIVE_INFINITY;
        Node nodeWithBetterValue = null;
        for (Node child : children) {
            double value = child.getState().getValue();
            if (type == SearchType.LOCAL_MAX) {
                if (value > betterValue && isStateValid(child.getState())) {
                    betterValue = value;
                    nodeWithBetterValue = child;
                }
            }
            else {
                if (value < betterValue) {
                    betterValue = value;
                    nodeWithBetterValue = child;
                }
            }
        }
        return nodeWithBetterValue;
    }

    private boolean isNodeBetter(Node current, Node neighbour, SearchType type) {
        double currentValue = current.getState().getValue();
        double neighbourValue = neighbour.getState().getValue();
        if (type == SearchType.LOCAL_MAX) {
            return (currentValue < neighbourValue);
        }
        else {
            return (currentValue > neighbourValue);
        }
    }

    public double getLocalMin() {
        try {
            goalTest = new MinValueGoalTest(problem.getMaxError());
            search(SearchType.LOCAL_MIN);
            if (outcome == SearchOutcome.SOLUTION_FOUND) {
                return lastState.getValue();
            }
        } catch (Exception ex) {
        }
        return Double.NEGATIVE_INFINITY;
    }

    public double getLocalMax() {
        try {
            goalTest = new MaxValueGoalTest(problem.getMaxError());
            search(SearchType.LOCAL_MAX);
            if (outcome == SearchOutcome.SOLUTION_FOUND) {
                return lastState.getValue();
            }
        } catch (Exception ex) {
        }
        return Double.NEGATIVE_INFINITY;
    }
}