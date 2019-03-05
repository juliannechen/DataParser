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

    public static ArrayList<ElectionResult> parse2016ElectionResults(String data) {
        ArrayList<ElectionResult> output = new ArrayList<>();

        String[] lines = data.split("\n");
        for (int i = 1; i < lines.length; i++) {
            int test = i;
            System.out.println("line: " + (test-1));
            String dataline = lines[i];
            int indexOfFirstQuote = dataline.indexOf("\"");
            int indexOfSecondQuote = dataline.indexOf("\"", indexOfFirstQuote + 1);
            String filterComma = removeCommas(dataline.substring(indexOfFirstQuote + 1, indexOfSecondQuote));
            String filterQuote = dataline.substring(0, indexOfFirstQuote) + filterComma + dataline.substring(indexOfSecondQuote + 1, dataline.length());
            String filterPrecent = filterQuote.replace("%", "");
            String[] filteredData = filterPrecent.split(",");
            output.add(createObject(filteredData));

        }
        return output;
    }

    private static String removeCommas(String str) {
        String[] temp = str.split(",");
        String output = "";
        for (int i = 0; i < temp.length; i++) {
            output += temp[i];
        }
        return output;
    }

    private static ElectionResult createObject(String[] filteredData) {
        double votes_dem = (Double.parseDouble(filteredData[1]));
        double votes_gop = (Double.parseDouble(filteredData[2]));
        double total_votes = (Double.parseDouble(filteredData[3]));
        double per_dem = (Double.parseDouble(filteredData[4]));
        double per_gop = (Double.parseDouble(filteredData[5]));
        int diff = (Integer.parseInt(filteredData[6]));
        double per_point_diff = (Double.parseDouble(filteredData[7]));
        String state_abbr = filteredData[8];
        String county_name = filteredData[9];
        int combined_fips = (Integer.parseInt(filteredData[10]));

        ElectionResult e = new ElectionResult(votes_dem,votes_gop,total_votes,per_dem,per_gop,diff,per_point_diff,state_abbr,county_name, combined_fips);
        return e;

    }

}
