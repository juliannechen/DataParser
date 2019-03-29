import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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

    public static void writeDataToFile(String filePath, String data) {
        try (PrintWriter writer = new PrintWriter(new File("test.csv"))) {

            writer.write(data);

            System.out.println("done!");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());

//        File outFile = new File(filePath);
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
//            writer.write(data);
//
//        } catch (Exception e) {
//            e.printStackTrace();
       }
    }

    public void saveDataToFile (String file) {
        String result = "";
        writeDataToFile(file, result);
    }


    public static String[] cleanfile(String data, int startIndex) {
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
        String before = str.substring(0, firstQuote);
        String after = str.substring(secondQuote + 1);

        String temp = str.substring(firstQuote + 1, secondQuote);
        if (temp.contains("[a-zA-Z]+")) return before+temp+after;
        temp = temp.replaceAll(",", "");
        temp = temp.replaceAll("$", "");
        temp = temp.trim();

        return before + temp + after;
    }

}
