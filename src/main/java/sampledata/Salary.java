package sampledata;

import java.util.HashMap;
import java.util.Random;

public class Salary {

    public static String tableName = "Salary";

    private int identifier;

    // Salaries
    private int sal_empNum, sal_salary, sal_fromDate, sal_toDate;

    protected Salary(int sal_empNum) {
        Random rng = new Random();

        identifier = rng.nextInt();

        this.sal_empNum = sal_empNum;

        this.sal_fromDate = rng.nextInt();
        this.sal_toDate = rng.nextInt();
        this.sal_salary = rng.nextInt();

    }

    public HashMap<String, String> getInsertionStatements() {

        HashMap<String, String> insertStatements = new HashMap<>();

        String sqlStatement = "INSERT INTO " + tableName + " VALUES ("
                + String.valueOf(this.identifier) + ", "
                + String.valueOf(this.sal_empNum) + ", "
                + String.valueOf(this.sal_fromDate) + ", "
                + String.valueOf(this.sal_toDate) + ", "
                + String.valueOf(this.sal_salary) + "); ";

        String mongoStatement = " { "
                + "fromDate: " + String.valueOf(this.sal_fromDate) + ", "
                + "toDate: " + String.valueOf(this.sal_toDate) + ", "
                + "salary: " + String.valueOf(this.sal_salary)
                +" }";

        insertStatements.put("mysql", sqlStatement);
        insertStatements.put("mongodb", mongoStatement);



        return insertStatements;
    }

}
