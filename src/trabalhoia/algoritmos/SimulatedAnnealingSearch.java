package trabalhoia.algoritmos;

import java.util.List;
import java.util.Random;
import trabalhoia.estruturas.AnnealingGoalTest;
import trabalhoia.estruturas.Node;
import trabalhoia.estruturas.Problem;
import trabalhoia.estruturas.Scheduler;
import trabalhoia.estruturas.Search;

public class SimulatedAnnealingSearch extends Search {

    private Scheduler scheduler;

    public SimulatedAnnealingSearch(Problem problem) {
        super(problem);
    }

    @Override
    public SearchOutcome search(SearchType type) {
        try {
            log("Parâmetros: " + problem.toString());
            scheduler = new Scheduler();
            outcome = null;
            lastState = null;
            double deltaE = Double.POSITIVE_INFINITY;
            double interval = problem.getMaxInterval() - problem.getMinInterval();
            Node current = new Node(problem.getInitialState(), null, interval / 100);
            Node next = null;
            int timeStep = 0;
            while (outcome == null) {
                double temperature = scheduler.getTemp(timeStep++);
                if (temperature == 0.0) {
                    if (((AnnealingGoalTest) goalTest).isAnnealingGoal(deltaE))
                        outcome = SearchOutcome.SOLUTION_FOUND;
                    else
                        outcome = SearchOutcome.FAILURE;
                    lastState = current.getState();
                    return outcome;
                }
                List<Node> children = current.expandNode();
                if (children.size() > 0) {
                    next = selectRandomlyFromList(children);
                    if (type == SearchType.LOCAL_MAX)
                        deltaE = next.getState().getValue() - current.getState().getValue();
                    else
                        deltaE = current.getState().getValue() - next.getState().getValue();
                    if (shouldAccept(temperature, deltaE) && isStateValid(next.getState())) {
                        current = next;
                        log("Achado um novo nó: " + next.toString());
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Houve um erro na execução da busca: " + ex.getMessage());
        }
        return outcome;
    }

    @Override
    public double getLocalMin() {
        try {
            System.out.println("Iniciando busca de um mínimo local utilizando o método de recozimento simulado");
            goalTest = new AnnealingGoalTest(problem.getMaxError());
            search(SearchType.LOCAL_MIN);
            if (outcome == SearchOutcome.SOLUTION_FOUND) {
                System.out.println("Mínimo local da função encontrado: " + lastState.getValue() + "\n");
                return lastState.getValue();
            }
            else {
                System.out.println("Não foi possível encontrar um mínimo local para a função\n");
            }
        } catch (Exception ex) {
        }
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getLocalMax() {
        try {
            System.out.println("Iniciando busca de um máximo local utilizando o método de recozimento simulado");
            goalTest = new AnnealingGoalTest(problem.getMaxError());
            search(SearchType.LOCAL_MAX);
            if (outcome == SearchOutcome.SOLUTION_FOUND) {
                System.out.println("Máximo local da função encontrado: " + lastState.getValue() + "\n");
                return lastState.getValue();
            }
            else {
                System.out.println("Não foi possível encontrar um máximo local para a função\n");
            }
        } catch (Exception ex) {
        }
        return Double.NEGATIVE_INFINITY;
    }

    private Node selectRandomlyFromList(List<Node> nodes) {
        int index = new Random().nextInt(nodes.size());
        return nodes.get(index);
    }

    public double probabilityOfAcceptance(double temperature, double deltaE) {
        return Math.exp(deltaE / temperature);
    }

    private boolean shouldAccept(double temperature, double deltaE) {
        return (deltaE > 0.0) || (new Random().nextDouble() <= probabilityOfAcceptance(temperature, deltaE));
    }
}
