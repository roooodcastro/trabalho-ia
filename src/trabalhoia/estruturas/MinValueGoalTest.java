/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia.estruturas;

/**
 *
 * @author rodrigo
 */
public class MinValueGoalTest extends GoalTest {

    public MinValueGoalTest(double maxError) {
        super(maxError);
    }

    @Override
    public boolean isGoal(Node node) {
        Node parent = node.getParent();
        if (parent != null) {
            double oldValue = parent.getState().getValue();
            double newValue = node.getState().getValue();
            if (newValue > oldValue) {
                return Math.abs(newValue - oldValue) < maxError;
            }
        }
        return false;
    }
}
