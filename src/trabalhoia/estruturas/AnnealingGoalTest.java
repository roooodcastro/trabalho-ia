package trabalhoia.estruturas;

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