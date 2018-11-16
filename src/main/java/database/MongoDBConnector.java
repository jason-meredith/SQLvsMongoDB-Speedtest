package main.java.database;

import main.java.sampledata.SampleData;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.omg.CORBA.portable.UnknownException;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.omg.CORBA.portable.UnknownException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used to connect to a MongoDB database, insert a SampleData object query and
 * return an InsertionResult
 */
public class MongoDBConnector implements DatabaseConnector {
    static Random rng = new Random();
	/** Create mongoDB instance */
	MongoDatabase database = null;
	
    public MongoDBConnector (MongoDatabase database) {
    	this.database = database;
    };
    
    @Override
    public InsertionResult insert(SampleData data) {

		// Create a collection instance to get collection
		MongoCollection<org.bson.Document> collection = database.getCollection("data");

		// insert document
		Document insert = insertDocument(collection);

        System.out.println("MongoDB Connector: " + data.getInsertionStatements().get("mongodb") + '(' + insert +')');
        double time = (rng.nextDouble()*500);
        try {
            Thread.sleep(Math.round(time));
        } catch (Exception e) {
            e.printStackTrace();;
        }
//
        return new InsertionResult("MongoDB", time, 0, data);
    }
    

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
	}
	

}
