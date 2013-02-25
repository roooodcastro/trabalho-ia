package trabalhoia.algoritmos;

import java.util.ArrayList;
import java.util.List;
import trabalhoia.estruturas.HeuristicFunction;
import trabalhoia.estruturas.Node;
import trabalhoia.estruturas.Problem;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): Figure 4.2, page
 * 122.<br>
 * <br>
 *
 * <pre>
 * function HILL-CLIMBING(problem) returns a state that is a local maximum
 *
 *   current &lt;- MAKE-NODE(problem.INITIAL-STATE)
 *   loop do
 *     neighbor &lt;- a highest-valued successor of current
 *     if neighbor.VALUE &lt;= current.VALUE then return current.STATE
 *     current &lt;- neighbor
 * </pre>
 *
 * Figure 4.2 The hill-climbing search algorithm, which is the most basic local
 * search technique. At each step the current node is replaced by the best
 * neighbor; in this version, that means the neighbor with the highest VALUE,
 * but if a heuristic cost estimate h is used, we would find the neighbor with
 * the lowest h.
 *
 * @author Ravi Mohan
 * @author Mike Stampone
 * @author Rodrigo Castro
 */
public class HillClimbingSearch {

    public enum SearchOutcome {

        FAILURE, SOLUTION_FOUND
    };
    private HeuristicFunction hf = null;
    private SearchOutcome outcome = SearchOutcome.FAILURE;
    private Object lastState = null;

    /**
     * Constructs a hill-climbing search from the specified heuristic function.
     *
     * @param hf
     *            a heuristic function
     */
    public HillClimbingSearch(HeuristicFunction hf) {
        this.hf = hf;
    }

    /**
     * Returns a list of actions to the local maximum if the local maximum was
     * found, a list containing a single NoOp Action if already at the local
     * maximum, or an empty list if the search was canceled by the user.
     *
     * @param p
     *            the search problem
     *
     * @return a list of actions to the local maximum if the local maximum was
     *         found, a list containing a single NoOp Action if already at the
     *         local maximum, or an empty list if the search was canceled by the
     *         user.
     */
    // function HILL-CLIMBING(problem) returns a state that is a local maximum
    public List<Action> search(Problem p) throws Exception {
        clearInstrumentation();
        outcome = SearchOutcome.FAILURE;
        lastState = null;
        // current <- MAKE-NODE(problem.INITIAL-STATE)
        Node current = new Node(p.getInitialState());
        Node neighbor = null;
        // loop do
        while (!CancelableThread.currIsCanceled()) {
            List<Node> children = expandNode(current, p);
            // neighbor <- a highest-valued successor of current
            neighbor = getHighestValuedNodeFrom(children);

            // if neighbor.VALUE <= current.VALUE then return current.STATE
            if ((neighbor == null) || (getValue(neighbor) <= getValue(current))) {
                if (SearchUtils.isGoalState(p, current)) {
                    outcome = SearchOutcome.SOLUTION_FOUND;
                }
                lastState = current.getState();
                return SearchUtils.actionsFromNodes(current.getPathFromRoot());
            }
            // current <- neighbor
            current = neighbor;
        }
        return new ArrayList<Action>();
    }

    /**
     * Returns SOLUTION_FOUND if the local maximum is a goal state, or FAILURE
     * if the local maximum is not a goal state.
     *
     * @return SOLUTION_FOUND if the local maximum is a goal state, or FAILURE
     *         if the local maximum is not a goal state.
     */
    public SearchOutcome getOutcome() {
        return outcome;
    }

    /**
     * Returns the last state from which the hill climbing search found the
     * local maximum.
     *
     * @return the last state from which the hill climbing search found the
     *         local maximum.
     */
    public Object getLastSearchState() {
        return lastState;
    }

    //
    // PRIVATE METHODS
    //
    private Node getHighestValuedNodeFrom(List<Node> children) {
        double highestValue = Double.NEGATIVE_INFINITY;
        Node nodeWithHighestValue = null;
        for (int i = 0; i < children.size(); i++) {
            Node child = (Node) children.get(i);
            double value = getValue(child);
            if (value > highestValue) {
                highestValue = value;
                nodeWithHighestValue = child;
            }
        }
        return nodeWithHighestValue;
    }

    private double getValue(Node n) {
        // assumption greater heuristic value =>
        // HIGHER on hill; 0 == goal state;
        return -1 * hf.h(n.getState());
    }

    /**
     * Returns the children obtained from expanding the specified node in the
     * specified problem.
     *
     * @param node
     *            the node to expand
     * @param problem
     *            the problem the specified node is within.
     *
     * @return the children obtained from expanding the specified node in the
     *         specified problem.
     */
    public List<Node> expandNode(Node node, Problem problem) {
        List<Node> childNodes = new ArrayList<Node>();

        ActionsFunction actionsFunction = problem.getActionsFunction();
        ResultFunction resultFunction = problem.getResultFunction();
        StepCostFunction stepCostFunction = problem.getStepCostFunction();

        for (Object action : actionsFunction.actions(node.getState())) {
            Object successorState = resultFunction.result(node.getState(),
                    action);

            double stepCost = stepCostFunction.c(node.getState(), action,
                    successorState);
            childNodes.add(new Node(successorState, node, action, stepCost));
        }
        return childNodes;
    }
}
