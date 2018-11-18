package sampledata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SampleDataLevelThree implements SampleData {

    public int identifier;


    public static String tableName = "SampleDataLevelThree";

    // Employee table
    private int emp_birthDate, emp_fname, emp_lname, emp_gender, emp_hireDate;

    // Salaries
    private ArrayList<Salary> salaries;

    // Titles
    private ArrayList<Title> titles;


    public SampleDataLevelThree() {
        Random rng = new Random();

        identifier = rng.nextInt();

        emp_birthDate = rng.nextInt();
        emp_fname = rng.nextInt();
        emp_lname = rng.nextInt();
        emp_gender = rng.nextInt();
        emp_hireDate = rng.nextInt();

        salaries = new ArrayList<>();
        titles = new ArrayList<>();

        // Add a random number of salaries between 2 and 12
        for(int i = 0; i < Math.abs(rng.nextInt() % 10) + 2; i++) {
            salaries.add(new Salary(identifier));
        }

        // Add a random number of titles between 2 and 12
        for(int i = 0; i < Math.abs(rng.nextInt() % 10) + 2; i++) {
            titles.add(new Title(identifier));
        }

    }



    @Override
    public SampleData getClone() {
        SampleDataLevelThree clone = new SampleDataLevelThree();

        clone.emp_birthDate = this.emp_birthDate;
        clone.emp_fname = this.emp_fname;
        clone.emp_lname = this.emp_lname;
        clone.emp_gender = this.emp_gender;
        clone.emp_hireDate = this.emp_hireDate;

        clone.salaries = this.salaries;
        clone.titles = this.titles;

        clone.identifier = this.identifier;

        return clone;
    }


    /**
     * Return a HashMap of database insertion statements representing this object based on different database
     * types given as the HashMap key
     * @return
     */
    public HashMap<String, String> getInsertionStatements() {
        HashMap<String, String> insertStatements = new HashMap<>();

        String sqlStatement = "INSERT INTO " + tableName + " VALUES("
                + String.valueOf(getIdentifier()) + ", "
                + String.valueOf(this.emp_birthDate) + ", "
                + String.valueOf(this.emp_fname) + ", "
                + String.valueOf(this.emp_lname) + ", "
                + String.valueOf(this.emp_gender) + ", "
                + String.valueOf(this.emp_hireDate) + "); \n";

        for(Salary salary : this.salaries) {
            sqlStatement += salary.getInsertionStatements().get("mysql");
            sqlStatement += " \n";
        }

        for(Title title : this.titles) {
            sqlStatement += title.getInsertionStatements().get("mysql");
            sqlStatement += " \n";
        }


        // MongoDB Insert statement
        String mongoStatement = "{"
                + "insert: \"" + tableName + "\", "
                + "documents: [ { "
                    + "_id:" + String.valueOf(getIdentifier()) + ", "
                    + "birthdate: " + String.valueOf(this.emp_birthDate) + ", "
                    + "fname: " + String.valueOf(this.emp_fname) + ", "
                    + "lname: " + String.valueOf(this.emp_lname) + ", "
                    + "gender: " + String.valueOf(this.emp_gender) + ", "
                    + "hiredate: " + String.valueOf(this.emp_hireDate) + ", "
                    + "salaries: [ ";
        for(Salary salary : this.salaries) {
            mongoStatement += salary.getInsertionStatements().get("mongodb");
            mongoStatement += ", ";
        }
        mongoStatement += "], ";
        mongoStatement += "titles: [ ";
        for(Title title : this.titles) {
            mongoStatement += title.getInsertionStatements().get("mongodb");
            mongoStatement += ", ";
        }
        mongoStatement += "] } ] } ";



        insertStatements.put("mysql", sqlStatement);
        insertStatements.put("mongodb", mongoStatement);

        return insertStatements;
    }



    public int getIdentifier() {
        return this.identifier;
    }

}