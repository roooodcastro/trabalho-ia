package trabalhoia.estruturas;

public class State {

    private Function function;
    private double x;

    public State(Function function, double x) {
        this.function = function;
        this.x = x;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public double getValue() {
        return function.value(x);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }
}