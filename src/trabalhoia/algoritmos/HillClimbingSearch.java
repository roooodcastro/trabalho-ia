package trabalhoia.algoritmos;

import java.util.List;
import trabalhoia.estruturas.MaxValueGoalTest;
import trabalhoia.estruturas.MinValueGoalTest;
import trabalhoia.estruturas.Node;
import trabalhoia.estruturas.Problem;
import trabalhoia.estruturas.Search;

public class HillClimbingSearch extends Search {

    public HillClimbingSearch(Problem problem) {
        super(problem);
    }

    @Override
    public SearchOutcome search(SearchType type) {
        log("Parâmetros: " + problem.toString());
        outcome = null;
        lastState = null;
        double interval = problem.getMaxInterval() - problem.getMinInterval();
        Node current = new Node(problem.getInitialState(), null, interval / 100);
        Node neighbour = null;
        while (outcome == null) {
            List<Node> children = current.expandNode();
            neighbour = getNextBestValuedNodeFrom(children, type, current);
            if ((neighbour == null) || !isNodeBetter(neighbour, current, type)) {
                if (goalTest.isGoal(current)) {
                    outcome = SearchOutcome.SOLUTION_FOUND;
                } else {
                    outcome = SearchOutcome.FAILURE;
                    return outcome;
                }
                lastState = current.getState();
            } else {
                log("Achado nó melhor: " + neighbour.toString());
            }
            current = neighbour;
        }
        return outcome;
    }

    private Node getNextBestValuedNodeFrom(List<Node> children, SearchType type, Node current) {
        for (Node child : children) {
            double value = child.getState().getValue();
            if (type == SearchType.LOCAL_MAX) {
                if (value > current.getState().getValue() && isStateValid(child.getState())) {
                    return child;
                }
            } else {
                if (value < current.getState().getValue() && isStateValid(child.getState())) {
                    return child;
                }
            }
        }
        return null;
    }

    private boolean isNodeBetter(Node current, Node neighbour, SearchType type) {
        double currentValue = current.getState().getValue();
        double neighbourValue = neighbour.getState().getValue();
        if (type == SearchType.LOCAL_MAX) {
            return (currentValue > neighbourValue);
        } else {
            return (currentValue < neighbourValue);
        }
    }

    @Override
    public double getLocalMin() {
        try {
            System.out.println("Iniciando busca de um mínimo local utilizando o método de subida de encosta");
            goalTest = new MinValueGoalTest(problem.getMaxError());
            search(SearchType.LOCAL_MIN);
            if (outcome == SearchOutcome.SOLUTION_FOUND) {
                System.out.println("Mínimo local da função encontrado: " + lastState.getValue() + "\n");
                return lastState.getValue();
            } else {
                System.out.println("Não foi possível encontrar um mínimo local para a função\n");
            }
        } catch (Exception ex) {
        }
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getLocalMax() {
        try {
            System.out.println("Iniciando busca de um máximo local utilizando o método de subida de encosta");
            goalTest = new MaxValueGoalTest(problem.getMaxError());
            search(SearchType.LOCAL_MAX);
            if (outcome == SearchOutcome.SOLUTION_FOUND) {
                System.out.println("Máximo local da função encontrado: " + lastState.getValue() + "\n");
                return lastState.getValue();
            } else {
                System.out.println("Não foi possível encontrar um máximo local para a função\n");
            }
        } catch (Exception ex) {
        }
        return Double.NEGATIVE_INFINITY;
    }
}
