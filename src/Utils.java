import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class Utils {

    public static String readFileAsString(String filepath) {
        StringBuilder output = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(filepath))) {

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                output.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public static DataManager parseAllData(String election, String education, String employment) {
        String[] electionLines = cleanfile(election, 1);
        String[] educationLines = cleanfile(education, 5);
        String[] employmentLines = cleanfile(employment, 8);

        ArrayList<State> stateList = createStateList(electionLines);
        DataManager states = new DataManager(stateList);
        ArrayList<County> countyList = createCountyList(electionLines, stateList);

        for (String[] dataline : electionLines) {
            Election e = new Election((Double.parseDouble(dataline[1])), (Double.parseDouble(dataline[2])), (Double.parseDouble(dataline[3])));
            int fips = Integer.parseInt(dataline[10]);
            String countyName = dataline[9];
            countyList.get(countyList.indexOf(countyName)).setFips(fips);
            countyList.get(countyList.indexOf(countyName)).setVote(e);
        }

        for (String[] dataline : educationLines) {
            Education e = new Education(Double.parseDouble(dataline[7]), Double.parseDouble(dataline[8]), Double.parseDouble(dataline[9]), Double.parseDouble(dataline[10]));
            int fips = Integer.parseInt(dataline[0]);
            String countyName = dataline[2];
            countyList.get(countyList.indexOf(countyName)).setFips(fips);
            countyList.get(countyList.indexOf(countyName)).setEducation(e);
        }

        for (String[] dataline : employmentLines) {
            Employment e = new Employment((Integer.parseInt(dataline[7])), (Integer.parseInt(dataline[8])), (Integer.parseInt(dataline[9])), (Integer.parseInt(dataline[10])));
            int fips = Integer.parseInt(dataline[0]);
            String countyName = dataline[2];
            countyList.get(countyList.indexOf(countyName)).setFips(fips);
            countyList.get(countyList.indexOf(countyName)).setEmployment(e);
        }

        return states;

    }

    private static ArrayList<County> createCountyList(ArrayList<String[]> employment) {
        ArrayList<County> countyList = new ArrayList<>();
        for (String[] dataLine : employment) {
            String stateName = dataLine[1];
            String countyName = dataLine[2];
            if (!countyList.contains(countyName)) {
                County c = new County(countyName);
                state.get(stateList.indexOf(stateName)).addCounty(c);
                countyList.add(c);
            }
        }
        return countyList;
    }

    private static ArrayList<State> createStateList(ArrayList<String[]> election) {
        ArrayList<State> stateList = new ArrayList<>();
        for (String[] dataLine : election) {
            String stateName = dataLine[8];
            if (!stateList.contains(stateList)) {
                State s = new State(stateName);
                stateList.add(s);
            }
        }
        return stateList;
    }

    public static String[] cleanfile(String data, int startIndex) {
        String[] lines = data.split("\n");
        for (int i = startIndex; i < lines.length; i++) {
            int currentIndex = 0;
            String dataline = lines[i];
            dataline = cleanLine(dataline);
            lines[i-i] = dataline;
        }
        return lines;
    }

    private static String cleanLine(String row) {
        row = row.replaceAll("%", "");
        int indexOfFirstQuote = row.indexOf("\"");
        int indexOfSecondQuote = row.indexOf("\"", indexOfFirstQuote + 1);

        while (indexOfFirstQuote != -1 && indexOfSecondQuote !=  -1) {
            row = cleanSubstring(row, indexOfFirstQuote, indexOfSecondQuote);
            indexOfFirstQuote = row.indexOf("\"");
            indexOfSecondQuote = row.indexOf("\"", indexOfFirstQuote + 1);
        }

        return row;
    }

    private static String cleanSubstring(String str, int firstQuote, int secondQuote) {
        if (str.contains("[a-zA-Z]+")) return str.substring(firstQuote + 1, secondQuote);
        String before = str.substring(0, firstQuote);
        String after = str.substring(secondQuote+1);

        String temp = str.substring(firstQuote + 1, secondQuote);
        temp = temp.replaceAll(" ", "");
        temp = temp.replaceAll(",", "");
        temp = temp.replaceAll("$", "");

        return before + temp + after;
    }
