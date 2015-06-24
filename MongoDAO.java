package com.paypal.test.gops.admin.listeener;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


	
	public class MongoDAO {

		/**
		 * @param args
		 */
		final MongoClientOptions options = MongoClientOptions.builder()
				.connectionsPerHost(100).build();
		final MongoClient client = new MongoClient(new ServerAddress("localhost", 27017),
				options);
		final MongoDatabase db = client.getDatabase("test").withReadPreference(
				ReadPreference.secondary());
		private String buildNumber;
		MongoCollection<Document> collection; 
		
		
		
		public MongoDAO(String collectionName){
			
			if(collectionName.contains(" ")){
				collectionName = collectionName.replace(" ", "");
			}
			System.out.println("Collection being used is: "+collectionName);
			collection = db.getCollection(collectionName);
			buildNumber = "4";
		}
		protected synchronized void writeIntoDb(Document doc, String ClassName){
			
			//System.out.println(.toJson());
			Bson filter = com.mongodb.client.model.Filters.and(eq("ClassName",ClassName),eq("BuildNumber",buildNumber));
			
			List<Document> all = collection.find(filter).into(new ArrayList<Document>());
			
			System.out.println(all.size());
			if(all.size()>0){
				collection.replaceOne(filter, doc.append("BuildNumber", buildNumber));
				//collection.updateMany(filter, new Document("$set",new Document("Status",doc.get("Status"))).append("BuildNumber", buildNumber));
				System.err.println("Doc Updated");
			}
			else{
				collection.insertOne(doc.append("BuildNumber", buildNumber));
				System.err.println("Doc Inserted");
				
			}
			
			
			
		}
		
		

	}
	


