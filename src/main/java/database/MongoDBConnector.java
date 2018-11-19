package database;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.async.SingleResultCallback;
import sampledata.SampleData;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Used to connect to a MongoDB database, insert a SampleData object query and
 * return an InsertionResult
 */
public class MongoDBConnector implements DatabaseConnector {


	/** Create mongoDB instance */
	private MongoDatabase database;

	private MongoCollection<Document> collection;

	private String log;

    long startTime, endTime;
	
	/**
	 * Connect to mongodb
	 */
    public MongoDBConnector () {

        log = "";

        try {
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://127.0.0.1:27017/?maxPoolSize=1"));
            //MongoClient mongoClient = new MongoClient();
            // Create a database instance to get db
            database = mongoClient.getDatabase("project");

        } catch (Exception e) {
            System.err.println("Error connecting to MongoDB Client.");
            Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, e);
        }

        log = log + "Connected to MongoDB database\n";
        collection = database.getCollection("data");
    }
    
    @Override
    public InsertionResult insert(SampleData data) {

        // Get system time
        startTime = System.currentTimeMillis();

        String insertionStatement = data.getInsertionStatements().get("mongodb");

        Document insertionCmd = Document.parse(insertionStatement);



        SingleResultCallback<Void> printResult = new SingleResultCallback<Void>() {

            @Override
            public void onResult(Void aVoid, Throwable throwable) {
                endTime = System.currentTimeMillis();
                log += "---\n";
                log += "MongoDB Connector\n" + data.getInsertionStatements().get("mongodb")+"\n";
                log += "Time: " + String.valueOf(endTime - startTime) + "ms\n";
            }
        };


        System.out.println(insertionCmd.toString());

        database.runCommand(insertionCmd);


        long endTime = System.currentTimeMillis();


        //log += "---\n";
        //log += "MongoDB Connector\n" + data.getInsertionStatements().get("mongodb")+"\n";
        //log += "Time: " + String.valueOf(endTime - startTime) + "ms\n";

//
        return new InsertionResult("MongoDB", endTime - startTime, 0, data);
    }

    public String getLog() {
        return log;
    }

}
