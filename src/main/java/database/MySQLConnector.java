package database;

import java.sql.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import sampledata.SampleData;

/**
 * Used to connect to a MongoDB database, insert a SampleData object query and
 * return an InsertionResult
 */
public class MySQLConnector implements DatabaseConnector {

	Random rng = new Random();

	// create mysql connection
	private Connection connection = null;
	private final String connectionString = "jdbc:mysql://localhost/cst8276project?useSSL=false";
	private final String username = "cst8276project";
	private final String password = "password";

	private String log;

	/**
	 * Connect to mysql
	 * 
	 * @throws SQLException
	 */
	public MySQLConnector() {

		log = "";

		try {
			if (connection != null) {
				System.out.println("Cannot create new connection, one exists already");
			} else {
				connection = DriverManager.getConnection(connectionString, username, password);

				// Truncate tables
				Statement stmt = connection.createStatement();
				stmt.execute("TRUNCATE TABLE SampleDataLevelOne;");
				stmt.execute("TRUNCATE TABLE SampleDataLevelTwo;");
				stmt.execute("TRUNCATE TABLE SampleDataLevelThree;");
				stmt.execute("TRUNCATE TABLE Salary;");
				stmt.execute("TRUNCATE TABLE Title;");
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			try {
				throw ex;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		log += "Connected to MySQL database\n";
	}

	@SuppressWarnings("null")
	@Override
	public InsertionResult insert(SampleData data) {
		// Get system time
		long startTime = System.currentTimeMillis();

		String insertionStatement = data.getInsertionStatements().get("mysql");

		String[] individualStmts = insertionStatement.split("\\s*;\\s*");


		System.out.println("---");
		System.out.println("MySQL Connector: " + insertionStatement);

		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			for(int i = 0; i < individualStmts.length; i++) {
				stmt.execute(individualStmts[i] + ";");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println(stmt.toString());

		long endTime = System.currentTimeMillis();

		log += "---\n";
		log += "MySQL Connector\n" + data.getInsertionStatements().get("mysql")+"\n";

		return new InsertionResult("MySQL", endTime - startTime, 0, data);
	}

	public String getLog() {
		return this.log;
	}
}
