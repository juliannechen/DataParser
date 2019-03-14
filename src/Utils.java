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

    public static String[] cleanfile(String data, int startIndex) {
        //TODO: eliminate lines 0-startIndex
        String[] lines = data.split("\n");
        for (int i = startIndex; i < lines.length; i++) {
            int currentIndex = 0;
            String dataline = lines[i];
            dataline = cleanLine(dataline);
            lines[i - currentIndex] = dataline;
            currentIndex++;
        }
        return lines;
    }

    private static String cleanLine(String row) {
        row = row.replaceAll("%", "");
        int indexOfFirstQuote = row.indexOf("\"");
        int indexOfSecondQuote = row.indexOf("\"", indexOfFirstQuote + 1);

        while (indexOfFirstQuote != -1 && indexOfSecondQuote != -1) {
            row = cleanSubstring(row, indexOfFirstQuote, indexOfSecondQuote);
            indexOfFirstQuote = row.indexOf("\"");
            indexOfSecondQuote = row.indexOf("\"", indexOfFirstQuote + 1);
        }

        return row;
    }

    private static String cleanSubstring(String str, int firstQuote, int secondQuote) {
        if (str.contains("[a-zA-Z]+")) return str.substring(firstQuote + 1, secondQuote);
        String before = str.substring(0, firstQuote);
        String after = str.substring(secondQuote + 1);

        String temp = str.substring(firstQuote + 1, secondQuote);
        temp = temp.replaceAll(" ", "");
        temp = temp.replaceAll(",", "");
        temp = temp.replaceAll("$", "");

        return before + temp + after;
    }

}
