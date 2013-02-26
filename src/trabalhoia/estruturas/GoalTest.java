/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia.estruturas;

/**
 *
 * @author rodrigo
 */
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