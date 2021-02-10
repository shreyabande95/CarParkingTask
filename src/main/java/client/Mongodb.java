package client;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import core.CarOperation;
import core.DbBaseClient;
import dto.Car;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Mongodb extends DbBaseClient {

    MongoClient mongoClient;
    static DB db;
    static DBCollection col;
    MongoCollection<Document> carsCollection;
    static Logger log = Logger.getLogger(Mongodb.class.getName());

    public Mongodb() throws IllegalAccessException, ClassNotFoundException, InstantiationException {

        mongoClient = new MongoClient( "localhost" , 27017 );
        db = mongoClient.getDB("Car");
        col = db.getCollection("parkedCars");
        MongoDatabase carDB = mongoClient.getDatabase("Car");
        carsCollection = carDB.getCollection("parkedCars");

    }

    public void createObject(Car car) {

        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
        int registrationNumber=car.getRegistrationNo();
        docBuilder.append("Registration Number",car.getRegistrationNo() );
        docBuilder.append("Color", car.getColor());
        docBuilder.append("Slot", ((car.getSlot()%20)+1));
        docBuilder.append("Floor", ((car.getSlot()/20)+1));
        DBObject newdoc=docBuilder.get();
        WriteResult result = col.insert(newdoc);
        getSlot(car.getRegistrationNo());
    }

    public void getSlot(int rno){

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("Registration Number", rno);
        DBCursor cursor = col.find(whereQuery);
        log.info("You have been alloted slot:");
        while(cursor.hasNext()) {

            String doc=cursor.next().toString();
            List<String> allotedSlot = Arrays.asList(doc.split(","));
            log.info(allotedSlot.get(3));
            log.info(allotedSlot.get(4).substring(0,(allotedSlot.get(4).length())-1));
        }
    }

    public void getSlot(String color){

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("Color", color);
        DBCursor cursor = col.find(whereQuery);
        while(cursor.hasNext()) {
            String doc=cursor.next().toString();
            List<String> allotedSlot = Arrays.asList(doc.split(","));
            log.info(allotedSlot.get(3));
            log.info(allotedSlot.get(4).substring(0,(allotedSlot.get(4).length())-1));
        }

    }

    public void getRegNo(String color){

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("Color", color);
        DBCursor cursor = col.find(whereQuery);
         while(cursor.hasNext()) {
             String doc=cursor.next().toString();
            List<String> allotedSlot = Arrays.asList(doc.split(","));
            log.info(allotedSlot.get(1));
        }
    }

    public void dePark(int registrationNo){

        carsCollection.deleteOne(Filters.eq("Registration Number", registrationNo));
        log.info("Car has been deparked");

    }

    public void deleteCollection(){

        col.drop();
    }

}
