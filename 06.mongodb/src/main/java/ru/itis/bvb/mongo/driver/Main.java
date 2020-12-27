package ru.itis.bvb.mongo.driver;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.itis.bvb.mongo.driver.config.Configuration;

import java.util.Arrays;

import static com.mongodb.client.model.Projections.*;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration("javalab");
        MongoDatabase mongoDatabase = configuration.getMongoDatabase();
        //first example(Use getCollection)
        MongoCollection<Document> usersCollections = mongoDatabase.getCollection("users");
        usersCollections.find().forEach(document -> System.out.println(document.get("name", "no name")));
        //second example(Use Document,FindIterable with projection)
        Document searchQuery = new Document();
        searchQuery
                .append("age", new Document("$lte", 30));
        FindIterable<Document> resultDocuments = usersCollections.find(searchQuery)
                .projection(fields(include("name", "age"), excludeId()));
        for (Document document : resultDocuments) {
            System.out.println(document.toJson());
        }
    }
}
