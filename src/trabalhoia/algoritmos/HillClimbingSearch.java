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
        System.out.println("Parâmetros: " + problem.toString() + "\n");
        outcome = null;
        lastState = null;
        double interval = problem.getMaxInterval() - problem.getMinInterval();
        Node current = new Node(problem.getInitialState(), null, interval / 100);
        Node neighbour = null;
        while (outcome == null) {
            List<Node> children = current.expandNode();
            neighbour = getBestValuedNodeFrom(children, type);
            System.out.println("Achado nó melhor: " + neighbour.toString());
            if ((neighbour == null) || !isNodeBetter(neighbour, current, type)) {
                if (goalTest.isGoal(current)) {
                    outcome = SearchOutcome.SOLUTION_FOUND;
                }
                lastState = current.getState();
//                return SearchUtils.actionsFromNodes(current.getPathFromRoot());
            }
            current = neighbour;
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
        double betterValue;
        if (type == SearchType.LOCAL_MAX) {
            betterValue = Double.NEGATIVE_INFINITY;
        }
        else {
            betterValue = Double.POSITIVE_INFINITY;
        }
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
                if (value < betterValue && isStateValid(child.getState())) {
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
            return (currentValue > neighbourValue);
        }
        else {
            return (currentValue < neighbourValue);
        }
    }

    public double getLocalMin() {
        try {
            System.out.println("Iniciando busca de um mínimo local utilizando o método de subida de encosta");
            goalTest = new MinValueGoalTest(problem.getMaxError());
            search(SearchType.LOCAL_MIN);
            if (outcome == SearchOutcome.SOLUTION_FOUND) {
                System.out.println("Mínimo local da função encontrado: " + lastState.getValue() + "\n\n");
                return lastState.getValue();
            }
        } catch (Exception ex) {
        }
        return Double.POSITIVE_INFINITY;
    }

    public double getLocalMax() {
        try {
            System.out.println("Iniciando busca de um máximo local utilizando o método de subida de encosta");
            goalTest = new MaxValueGoalTest(problem.getMaxError());
            search(SearchType.LOCAL_MAX);
            if (outcome == SearchOutcome.SOLUTION_FOUND) {
                System.out.println("Máximo local da função encontrado: " + lastState.getValue() + "\n\n");
                return lastState.getValue();
            }
        } catch (Exception ex) {
        }
        return Double.NEGATIVE_INFINITY;
    }
}
