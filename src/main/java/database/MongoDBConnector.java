package database;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
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
	
    public MongoDBConnector () {
        try {
            MongoClient mongoClient = new MongoClient();
            // Create a database instance to get db
            database = mongoClient.getDatabase("project");

        } catch (Exception e) {
            System.err.println("Error connecting to MongoDB Client.");
            Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, e);
        }

        collection = database.getCollection("data");
    }
    
    @Override
    public InsertionResult insert(SampleData data) {

        // Get system time
        long startTime = System.currentTimeMillis();

        String insertionStatement = data.getInsertionStatements().get("mongodb");

        Document insertionCmd = Document.parse(insertionStatement);
        System.out.println(insertionCmd.toString());

        database.runCommand(insertionCmd);


        long endTime = System.currentTimeMillis();


        System.out.println("---");
        System.out.println("MongoDB Connector: " + data.getInsertionStatements().get("mongodb"));
        System.out.println("Time: " + String.valueOf(endTime - startTime) + "ms");

//
        return new InsertionResult("MongoDB", endTime - startTime, 0, data);
    }
    
/*
	// Insert an entry for a document
	static Document insertDocument(MongoCollection<org.bson.Document> collection) {
        int alpha = rng.nextInt();
        int beta = rng.nextInt();
        int gamma = rng.nextInt();
        int delta = rng.nextInt();
        int theta = rng.nextInt();
        
		Document document = new Document();
		document.put("alpha", alpha);
		document.put("beta", beta);
		document.put("gamma", gamma);
		document.put("delta", delta);
		document.put("theta", theta);
		collection.insertOne(document);
		return document;
	}*/
	

}
