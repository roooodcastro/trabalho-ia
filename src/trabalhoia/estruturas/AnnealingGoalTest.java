/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trabalhoia.estruturas;

/**
 *
 * @author rodrigo
 */
public class AnnealingGoalTest extends GoalTest {

    public AnnealingGoalTest(double maxError) {
        super(maxError);
    }

    @Override
    public boolean isGoal(Node node) {
        return false;
    }

    public boolean isAnnealingGoal(double deltaE) {
        return deltaE < maxError;
    }

}
