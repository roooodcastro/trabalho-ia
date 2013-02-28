package trabalhoia.estruturas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Node {

    private State state;
    private double xVariance;
    private Node parent;

    public Node(State state) {
        this.state = state;
    }

    public Node(State state, Node parent) {
        this(state);
        this.parent = parent;
    }

    public Node(State state, Node parent, double xVariance) {
        this(state, parent);
        this.xVariance = xVariance;
    }

    public State getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public boolean isRootNode() {
        return parent == null;
    }

    public double getxVariance() {
        return xVariance;
    }

    public void setxVariance(double xVariance) {
        this.xVariance = xVariance;
    }

    public List<Node> expandNode() {
        double[] nextValues = new double[]{xVariance * 0.2, xVariance, xVariance * 5, xVariance * -0.2, -xVariance, xVariance * -5};
        double[] scrambledNextValues = new double[nextValues.length];
        boolean[] checked = new boolean[]{false, false, false, false, false, false};
        int i = 0;
        while (i < nextValues.length) {
            int index = new Random().nextInt(nextValues.length);
            if (checked[index] == false) {
                checked[index] = true;
                scrambledNextValues[index] = nextValues[i++];
            }
        }
        List<Node> children = new ArrayList<Node>();
        Function f = getState().getFunction();
        double x = getState().getX();
        for (i = 0; i < 6; i++) {
            children.add(new Node(new State(f, x + scrambledNextValues[i]), this, scrambledNextValues[i]));
        }
        return children;
    }

    public List<Node> getPathFromRoot() {
        List<Node> path = new ArrayList<Node>();
        Node current = this;
        while (!current.isRootNode()) {
            path.add(0, current);
            current = current.getParent();
        }
        // ensure the root node is added
        path.add(0, current);
        return path;
    }

    @Override
    public String toString() {
        return "[x: " + state.getX() + ", value: " + state.getValue() + ", xVariance: " + xVariance + "]";
    }
}
