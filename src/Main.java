/***
 * main class for data parser
 */
public class Main {
    public static void main(String[] args) {
        //Test of utils

        String election = Utils.readFileAsString("data/2016_Presidential_Results.csv");
        String education = Utils.readFileAsString("data/Education.csv");
        String employment = Utils.readFileAsString("data/Unemployment.csv");
        String communityCenters = Utils.readFileAsString("data/community-care-licensing-adult-residential-facility-locations.csv");

        DataManager.loadAllData(election, education, employment, communityCenters);
        String data = DataManager.getData();
        Utils.writeDataToFile("/Users/Julianne/Documents/DataParser/data", data);

    }

}
