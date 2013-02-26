package trabalhoia.estruturas;

import java.util.ArrayList;
import java.util.List;

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
        double firstValue = xVariance / 2;
        double secondValue = xVariance / 1;
        double thirdValue = xVariance * 2;
        List<Node> children = new ArrayList<Node>();
        Function f = getState().getFunction();
        double x = getState().getX();
        children.add(new Node(new State(f, x + firstValue), this, firstValue));
        children.add(new Node(new State(f, x - firstValue), this, -firstValue));
        children.add(new Node(new State(f, x + secondValue), this, secondValue));
        children.add(new Node(new State(f, x - secondValue), this, -secondValue));
        children.add(new Node(new State(f, x + thirdValue), this, thirdValue));
        children.add(new Node(new State(f, x - thirdValue), this, -thirdValue));
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