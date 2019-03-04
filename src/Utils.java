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

        /* split by \n
            loop over rows starting at index 1
            for each row {
                remove , between " " and remove % characters
                split by comma
                create new ElectionResult object
                add to list
             }

         */

        String[] lines = data.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String dataline = lines[i];
            System.out.println(dataline.charAt(0));
            int indexOfFirstQuote = dataline.indexOf("\"");
            int indexOfSecondQuote = dataline.indexOf("\"", indexOfFirstQuote+1);
            String removedQuote = dataline.substring(indexOfFirstQuote+1, indexOfSecondQuote);
            String noQuotes = dataline.substring(2, indexOfFirstQuote) + removedQuote + dataline.substring(indexOfSecondQuote + 1, dataline.length());
            int indexOfPercent = dataline.indexOf("%");
            String noPercent = noQuotes.substring(0, indexOfPercent) + noQuotes.substring(indexOfPercent+1, noQuotes.length());
            String[] filteredData = noPercent.split(",");
            
            output.add(createObject(filteredData));
            
        }
        return output;
    }

    private static ElectionResult createObject(String[] filteredData) {
        ElectionResult e = null;
        e.setVotes_dem(Double.parseDouble(filteredData[0]));
        e.setVotes_gop(Double.parseDouble(filteredData[1]));
        e.setTotal_votes(Double.parseDouble(filteredData[2]));
        e.setPer_dem(Double.parseDouble(filteredData[3]));
        e.setPer_gop(Double.parseDouble(filteredData[4]));

        e.setDiff(Integer.parseInt(filteredData[5]));
        e.setPer_point_diff(Double.parseDouble(filteredData[6]));
        e.setPer_gop(Double.parseDouble(filteredData[7]));
        e.setPer_gop(Double.parseDouble(filteredData[8]));
        e.setDiff(Integer.parseInt(filteredData[9]));

        return e;
    }

}
