package trabalhoia.estruturas;

public abstract class GoalTest {

    protected double maxError;

    public GoalTest(double maxError) {
        this.maxError = maxError;
    }

    public double getMaxError() {
        return maxError;
    }

    public void setMaxError(double maxError) {
        this.maxError = maxError;
    }

    public abstract boolean isGoal(Node node);
}