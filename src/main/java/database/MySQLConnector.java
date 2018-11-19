package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

	/**
	 * Connect to mysql
	 * 
	 * @throws SQLException
	 */
	public MySQLConnector() {
		try {
			if (connection != null) {
				System.out.println("Cannot create new connection, one exists already");
			} else {
				connection = DriverManager.getConnection(connectionString, username, password);
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
	}

	@SuppressWarnings("null")
	@Override
	public InsertionResult insert(SampleData data) {
		// Get system time
		long startTime = System.currentTimeMillis();

		String insertionStatement = data.getInsertionStatements().get("mysql");

		System.out.println("---");
		System.out.println("MySQL Connector: " + insertionStatement);

		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(insertionStatement);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(pstmt.toString());

		long endTime = System.currentTimeMillis();

		System.out.println("---");
		System.out.println("MySQL Connector: " + data.getInsertionStatements().get("mysql"));
		System.out.println("Time: " + String.valueOf(endTime - startTime) + "ms");

		return new InsertionResult("MySQL", endTime - startTime, 0, data);
	}
}
