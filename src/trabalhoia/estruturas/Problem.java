package trabalhoia.estruturas;

public class Problem {

    private State initialState;
    private Function function;
    private double minInterval = 0;
    private double maxInterval = 0;
    private double maxError;

    public Problem(Function function, double initialValue, double minInterval, double maxInterval, double maxError) {
        this.initialState = new State(function, initialValue);
        this.minInterval = minInterval;
        this.maxInterval = maxInterval;
        this.maxError = maxError;
        this.function = function;
    }

    protected Problem() {
    }

    public State getInitialState() {
        return initialState;
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }

    public double getMaxError() {
        return maxError;
    }

    public void setMaxError(double maxError) {
        this.maxError = maxError;
    }

    public double getMaxInterval() {
        return maxInterval;
    }

    public void setMaxInterval(double maxInterval) {
        this.maxInterval = maxInterval;
    }

    public double getMinInterval() {
        return minInterval;
    }

    public void setMinInterval(double minInterval) {
        this.minInterval = minInterval;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "Initial X: " + initialState.getX() + ", interval: [" + minInterval + ", " + maxInterval + "], tolerance: " + maxError;
    }
}