import java.util.ArrayList;

public class DataManager {
    private static ArrayList<State> states;

    public DataManager(ArrayList<State> states) {
        this.states = states;
    }

    public static void loadAllData(String electionFile, String educationFile, String employmentFile, String communityCentersFile) {
        String[] electionLines = Utils.cleanfile(electionFile, 1);
        String[] educationLines = Utils.cleanfile(educationFile, 5);
        String[] employmentLines = Utils.cleanfile(employmentFile, 8);
        String[] communityCenterLines = Utils.cleanfile(communityCentersFile, 1);

        addStateObjects(electionLines, 1);
        addCountyObjects(electionLines, 1);
        //loadElectionDataIntoObject(electionLines, 1);
        loadEducationDataIntoObjects(educationLines, 5);
        loadEmploymentDataIntoObjects(employmentLines, 8);
        loadCommunityCenterIntoObjects(communityCenterLines, 1);
    }


    private static void loadCommunityCenterIntoObjects(String[] communityCenterLines, int startIndex) {
        for (int i = startIndex; i < communityCenterLines.length; i++) {
            String line = communityCenterLines[i];
            String[] dataLine = line.split(",");
            if (dataLine[13].equals("LICENSED")) {
                String countyName = dataLine[10];
                countyName = fixFormatting(countyName);
                County county = getCounty(countyName);
                if(county != null) {
                    county.setCenters(county.getCenters() + 1);
                }

            }

        }
    }

    private static String fixFormatting(String str) {
        if (str == null || str.isEmpty())
            return "";

        if (str.length() == 1)
            return str.toUpperCase();

        String[] parts = str.split(" ");

        StringBuilder sb = new StringBuilder(str.length());

        for (String part : parts) {
            if (part.length() > 1)
                sb.append(part.substring(0, 1).toUpperCase())
                        .append(part.substring(1).toLowerCase());
            else
                sb.append(part.toUpperCase());

            sb.append(" ");
        }

        return sb.toString().trim() + " County";
    }

    private static void loadEmploymentDataIntoObjects(String[] employmentLines, int startIndex) {
        for (int i = startIndex; i < employmentLines.length; i++) {
            String line = employmentLines[i];
            String[] dataline = line.split(",");
            if (dataline.length > 44) {
                int unemployed = (Integer.parseInt(dataline[44].trim()));
                Employment e = new Employment(unemployed);
                String countyName = dataline[2];
                if(countyName.contains(" ") || !countyName.equals("United States")) {
                    countyName = countyName.substring(0,countyName.length()-3);
                }
                County county = getCounty(countyName);
                if (county != null) {
                    county.setEmployment(e);
                }
            }
        }
    }

//    private static void loadELectionDataIntoObject(String[] electionLines, int startIndex) {
//        for (int i = startIndex; i < electionLines.length; i++) {
//            String line = electionLines[i];
//            String[] dataline = line.split(",");
//            Election e = new Election((Double.parseDouble(dataline[1])), (Double.parseDouble(dataline[2])), (Double.parseDouble(dataline[3])));
//            String countyName = dataline[9];
//            County county = getCounty(countyName);
//            county.setVote(e);
//        }
//
//    }

    private static void loadEducationDataIntoObjects(String[] educationLines, int startIndex) {
        for (int i = startIndex; i < educationLines.length; i++) {
            double onlyHighSchool = 0;
            String line = educationLines[i];
            String[] dataline = line.split(",");
            if (dataline.length > 40) {
                if (!(dataline[40].length() == 0)) {
                    onlyHighSchool = Double.parseDouble(dataline[40]);
                }

                Education e = new Education(onlyHighSchool);
                String countyName = dataline[2];
                County county = getCounty(countyName);
                if (county != null) {
                    county.setEducation(e);
                }
            }
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
            State state = getState(stateName);
            if (state == null) {
                System.out.println("ERROR: non-existant state " + stateName);
                continue;
            }
            String countyName = dataline[9];
            if(!state.getCounties().contains(countyName)) {
                state.addCounty(new County(countyName));
            }
        }
    }

    private static State getState(String name) {
        for (State state : states) {
            if (state.getName().equals(name)) return state;
        }
        return null;
    }

    private static State getState(String name, ArrayList<State> stateList) {
        for (State state : stateList) {
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
            if (!stateList.contains(getState(stateName, stateList))) {
                State s = new State(stateName);
                stateList.add(s);
            }
        }
        DataManager dataManager = new DataManager(stateList);
        dataManager.setStates(stateList);
    }

    public static void printData() {
        System.out.println("state, county, high school only, unemployed labor force, number of community centers");
        State state = getState("CA");
        ArrayList<County> counties = state.getCounties();
        for (County c : counties) {
            String countyName = c.getName();
            double highSchoolOnly = c.getEducation().getOnlyHighSchool();
            double unemployment = c.getEmployment().getUnemployedLaborForce();
            int numOfCommunityCenters = c.getCenters();
            System.out.println(state.getName() + ", " + countyName + ", " + highSchoolOnly + ", " + unemployment + ", " + numOfCommunityCenters);

        }

    }

    public static String getData() {
        String output = "State, County, People who only graduated high school, unemployed labor force, number of commuity centers" + "\n";
        for (int i = 0; i < states.size(); i++) {
            State s = states.get(i);
            if(s.getName().equals("CA")) {
                ArrayList<County> counties = s.getCounties();
                for (int j = 0; j < counties.size(); j++) {
                    County c = counties.get(j);
                    String stateName = s.getName();
                    String countyName = c.getName();
                    Education education = c.getEducation();
                    Employment employment = c.getEmployment();
                    double gradHighSchool = 0;
                    double unemployed = 0;
                    if(education != null) {
                        gradHighSchool = education.getOnlyHighSchool();
                    }
                    if(employment != null) {
                        unemployed = employment.getUnemployedLaborForce();
                    }
                    int communityCenters = c.getCenters();
                    output+=(stateName + ", " + countyName + ", " + gradHighSchool + ", " + unemployed + ", " + communityCenters + "\n");
                }
            }
        }
        return output;
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




