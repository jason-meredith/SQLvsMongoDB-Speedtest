package database;

import com.mongodb.Block;
import com.mongodb.ServerAddress;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.ConnectionString;
import com.mongodb.event.ClusterEventMulticaster;
import org.bson.Document;
import sampledata.SampleData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.inc;
import static com.mongodb.client.model.Updates.set;
import static java.util.Arrays.asList;

/**
 * Used to connect to a MongoDB database, insert a SampleData object query and
 * return an InsertionResult
 */
public class MongoAsyncDBConnector implements DatabaseConnector {

    long startTime, endTime;

    private MongoDatabase database;

    private String log;

    public MongoAsyncDBConnector() {
/*
        ClusterSettings clusterSettings = ClusterSettings.builder()
            .hosts(asList(new ServerAddress("127.0.0.1"))).build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .clusterSettings(clusterSettings).build();
        MongoClient client = MongoClients.create(settings);
*/
//        MongoClient client = MongoClients.create(new ConnectionString("mongodb://localhost:27017"));

        MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1/?maxPoolSize=1");
        database = mongoClient.getDatabase("project");

        log = "Connected to MongoDB database";
    }



    @Override
    public InsertionResult insert(SampleData data) {


        final CountDownLatch executionComplete = new CountDownLatch(1);

        // Get system time
        startTime = System.currentTimeMillis();

        String insertionStatement = data.getInsertionStatements().get("mongodb");

        Document insertionCmd = Document.parse(insertionStatement);

        database.runCommand(insertionCmd, (document, throwable) -> {
            endTime = System.currentTimeMillis();
            log += "---\n";
            log += "MongoDB Connector\n" + data.getInsertionStatements().get("mongodb")+"\n";
            executionComplete.countDown();
        });

        try {
            executionComplete.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new InsertionResult("MongoDB", endTime - startTime, 0, data);
    }

    @Override
    public String getLog() {
        return this.log;
    }
}