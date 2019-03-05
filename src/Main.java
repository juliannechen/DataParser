import java.util.ArrayList;

/***
 * main class for data parser
 */
public class Main {
    public static void main(String[] args) {
        //Test of utils

        String data = Utils.readFileAsString("data/2016_Presidential_Results.csv");

        ArrayList<ElectionResult> results = Utils.parse2016ElectionResults(data);
        System.out.println(results);

    }
}
