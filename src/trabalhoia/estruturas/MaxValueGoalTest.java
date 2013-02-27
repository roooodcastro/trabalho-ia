package trabalhoia.estruturas;

public class MaxValueGoalTest extends GoalTest {

    public MaxValueGoalTest(double maxError) {
        super(maxError);
    }

    @Override
    public boolean isGoal(Node node) {
        Node parent = node.getParent();
        if (parent != null) {
            double oldValue = parent.getState().getValue();
            double newValue = node.getState().getValue();
            if (newValue >= oldValue) {
                return Math.abs(newValue - oldValue) < maxError || node.getxVariance() <= maxError;
            }
        }
        return false;
    }
}
