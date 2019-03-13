import java.util.ArrayList;

public class DataManager {
    private ArrayList<State> states;

    public DataManager(ArrayList<State> states) {
        this.states = states;
    }

    public void addState(State s) {
        states.add(s);
    }

    public ArrayList<State> getStates() {
        return states;
    }

    public void setStates(ArrayList<State> states) {
        this.states = states;
    }
}
