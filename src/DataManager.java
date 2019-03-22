import java.util.ArrayList;

public class DataManager {
    private static ArrayList<State> states;

    public DataManager(ArrayList<State> states) {
        this.states = states;
    }

    public static void loadAllData(String electionFile, String educationFile, String employmentFile) {
        String[] electionLines = Utils.cleanfile(electionFile, 1);
        String[] educationLines = Utils.cleanfile(educationFile, 5);
        String[] employmentLines = Utils.cleanfile(employmentFile, 8);

        addStateObjects(electionLines, 1);
        addCountyObjects(electionLines, 1);
        loadELectionDataIntoObject(electionLines, 1);
        loadEducationDataIntoObjects(educationLines, 5);
        loadEmploymentDataIntoObjects(employmentLines, 8);
    }

    private static void loadEmploymentDataIntoObjects(String[] employmentLines, int startIndex) {
        for (int i = startIndex; i < employmentLines.length; i++) {
            String line = employmentLines[i];
            String[] dataline = line.split(",");
            Employment e = new Employment((Integer.parseInt(dataline[7])), (Integer.parseInt(dataline[8])), (Integer.parseInt(dataline[9])), (Integer.parseInt(dataline[10])));
            String countyName = dataline[2];
            County county = getCounty(countyName);
            county.setEmployment(e);
        }

    }

    private static void loadELectionDataIntoObject(String[] electionLines, int startIndex) {
        for (int i = startIndex; i < electionLines.length; i++) {
            String line = electionLines[i];
            String[] dataline = line.split(",");
            Election e = new Election((Double.parseDouble(dataline[1])), (Double.parseDouble(dataline[2])), (Double.parseDouble(dataline[3])));
            String countyName = dataline[9];
            County county = getCounty(countyName);
            county.setVote(e);
        }

    }

    private static void loadEducationDataIntoObjects(String[] educationLines, int startIndex) {
        for (int i = startIndex; i < educationLines.length; i++) {
            String line = educationLines[i];
            String[] dataline = line.split(",");
            Education e = new Education(Double.parseDouble(dataline[7]), Double.parseDouble(dataline[8]), Double.parseDouble(dataline[9]), Double.parseDouble(dataline[10]));
            String countyName = dataline[2];
            if (countyName == null) {
                System.out.println("ERROR: non-existant state " + countyName);
                continue;
            }
            County county = getCounty(countyName);
            county.setEducation(e);
        }
    }

    private static County getCounty(String name) {
        for (State state : states) {
            ArrayList<County> counties = state.getCounties();
            for (County county : counties) {
                if (county.getName().equals(name)) {
                    return county;
                }
            }
        }
        return null;
    }

    private static void addCountyObjects(String[] lines, int startIndex) {
        for (int i = startIndex; i < lines.length; i++) {
            String line = lines[i];
            String[] dataline = line.split(",");
            String stateName = dataline[8];
            System.out.println("state name: " + stateName);

            State state = getState(stateName);
            if (state == null) {
                System.out.println("ERROR: non-existant state " + stateName);
                continue;
            }

            String countyName = dataline[9];
            System.out.println("county name: " + countyName);
            int fips = Integer.parseInt(dataline[10]);
            state.addCounty(new County(countyName, fips));
        }
    }

    private static State getState(String name) {
        for (State state : states) {
            if (state.getName().equals(name)) return state;
        }
        return null;
    }

    private static void addStateObjects(String[] lines, int startIndex) {
        ArrayList<State> stateList = new ArrayList<>();
        for (int i = startIndex; i < lines.length; i++) {
            String line = lines[i];
            String[] dataline = line.split(",");
            String stateName = dataline[8];
            if (!stateList.contains(stateName)) {
                State s = new State(stateName);
                stateList.add(s);
            }
        }
        DataManager dataManager = new DataManager(stateList);
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


