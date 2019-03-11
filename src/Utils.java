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

    }

    private static ArrayList<State> createStateList(ArrayList<String[]> election) {
        ArrayList<State> stateList = new ArrayList<>();
        for (String[] line : election) {
            String stateName = line[1];
            if(!stateList.contains(stateList)) {
                State s = new State(stateName);
                stateList.add(s);
            }
        }
        return stateList;
    }

//    public static ArrayList<Election> parse2016ElectionResults(String data) {
//        ArrayList<Election> output = new ArrayList<>();
//
//        String[] lines = data.split("\n");
//        for (int i = 1; i < lines.length; i++) { ;
//            String dataline = lines[i];
//            int indexOfFirstQuote = dataline.indexOf("\"");
//            int indexOfSecondQuote = dataline.indexOf("\"", indexOfFirstQuote + 1);
//            String filter = cleanSubstring(dataline,indexOfFirstQuote, indexOfSecondQuote);
//            String cleanString = cleanString(filter);
//            String[] filteredData = cleanString.split(",");
//
//            Election election = new Election((Double.parseDouble(filteredData[1])), (Double.parseDouble(filteredData[2])), (Double.parseDouble(filteredData[3])));
//            output.add(election);
//        }
//
//        return output;
//    }

    public static ArrayList<String[]> parse(String data, int startIndex) {
        ArrayList<String[]> output = new ArrayList<>();
        String[] lines = data.split("\n");
        for (int i = startIndex; i < lines.length; i++) {
            int currentIndex = 0;
            String dataline = lines[i];
            dataline = cleanString(dataline);
            while (dataline.contains("\"")) {
                int indexOfFirstQuote = dataline.indexOf("\"", currentIndex);
                int indexOfSecondQuote = dataline.indexOf("\"", indexOfFirstQuote + 1);
                dataline = cleanSubstring(dataline, indexOfFirstQuote, indexOfSecondQuote);
                currentIndex = indexOfSecondQuote;
            }
            String[] filteredData = dataline.split(",");
            output.add(filteredData);
        }
        return output;
    }
//
//    public static ArrayList<Education> parseEducation(String data) {
//        ArrayList<Education> output = new ArrayList<>();
//        String[] lines = data.split("\n");
//        for (int i = 5; i < lines.length; i++) {
//            int currentIndex = 0;
//            String dataline = lines[i];
//            while (dataline.contains("\"")) {
//                int indexOfFirstQuote = dataline.indexOf("\"", currentIndex);
//                int indexOfSecondQuote = dataline.indexOf("\"", indexOfFirstQuote + 1);
//                dataline = cleanSubstring(dataline, indexOfFirstQuote, indexOfSecondQuote);
//                currentIndex = indexOfSecondQuote;
//            }
//            String[] filteredData = dataline.split(",");
//            Education education = new Education(Double.parseDouble(filteredData[7]), Double.parseDouble(filteredData[8]), Double.parseDouble(filteredData[9]), Double.parseDouble(filteredData[10]));
//            output.add(education);
//        }
//        return output;
//    }

    private static String cleanString(String temp) {
        temp = temp.replaceAll("$", "");
        temp = temp.replaceAll("%", "");
        return temp;
    }

    private static String cleanSubstring(String str, int start, int end) {
        if(start == -1) return str;
        if(str.contains("[a-zA-Z]+")) return str.substring(start+1, end);
        String temp = str.substring(start, end+1);
        temp = temp.replaceAll("\"", "");
        temp = temp.replaceAll(" ", "");
        temp = temp.replaceAll( ",", "");

        return str.substring(0, start) + temp + str.substring(end+1, str.length());

    }

}
