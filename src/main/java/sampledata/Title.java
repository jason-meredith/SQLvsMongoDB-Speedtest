package sampledata;

import java.util.HashMap;
import java.util.Random;

public class Title {

    public static String tableName = "Title";

    private int identifier;


    private static int id = 0;


    // Salaries
    private int ttl_empNum, ttl_title, ttl_fromDate, ttl_toDate;

    protected Title(int ttl_empNum) {
        Random rng = new Random();

        this.identifier = id++;

        this.ttl_empNum = ttl_empNum;

        this.ttl_title = rng.nextInt(500);
        this.ttl_fromDate = rng.nextInt(500);
        this.ttl_toDate = rng.nextInt(500);

    }

    public HashMap<String, String> getInsertionStatements() {

        HashMap<String, String> insertStatements = new HashMap<>();

        String sqlStatement = "INSERT INTO " + tableName + " VALUES ("
                + String.valueOf(this.identifier) + ", "
                + String.valueOf(this.ttl_empNum) + ", "
                + String.valueOf(this.ttl_fromDate) + ", "
                + String.valueOf(this.ttl_toDate) + ", "
                + String.valueOf(this.ttl_title) + "); ";

        String mongoStatement = " { "
                + "fromDate: " + String.valueOf(this.ttl_fromDate) + ", "
                + "toDate: " + String.valueOf(this.ttl_toDate) + ", "
                + "title: " + String.valueOf(this.ttl_title)
                +" }";

        insertStatements.put("mysql", sqlStatement);
        insertStatements.put("mongodb", mongoStatement);



        return insertStatements;
    }

}
