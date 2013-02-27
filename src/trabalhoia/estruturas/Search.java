/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia.estruturas;

/**
 *
 * @author rodrigo
 */
public abstract class Search {

    public enum SearchOutcome {

        FAILURE, SOLUTION_FOUND
    };

    public enum SearchType {

        LOCAL_MIN, LOCAL_MAX
    };
    protected Problem problem = null;
    protected SearchOutcome outcome = null;
    protected State lastState = null;
    protected GoalTest goalTest;
    protected boolean printLog = true;

    public Search(Problem problem) {
        this.problem = problem;
    }

    public abstract SearchOutcome search(SearchType type);

    public SearchOutcome getOutcome() {
        return outcome;
    }

    public Object getLastSearchState() {
        return lastState;
    }

    protected boolean isStateValid(State state) {
        return state.getX() >= problem.getMinInterval() && state.getX() <= problem.getMaxInterval();
    }

    public abstract double getLocalMin();

    public abstract double getLocalMax();

    public void log(String log) {
        if (printLog)
            System.out.println(log);
    }

    public boolean isPrintLog() {
        return printLog;
    }

    public void setPrintLog(boolean printLog) {
        this.printLog = printLog;
    }
}
