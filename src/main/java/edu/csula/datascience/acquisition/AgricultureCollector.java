package edu.csula.datascience.acquisition;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import edu.csula.datascience.model.Agriculture;

/**
 * An example of Collector implementation using Twitter4j with MongoDB Java driver
 */
public class AgricultureCollector implements Collector<Agriculture, Agriculture> {
    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection;
    public AgricultureCollector() {
        // establish database connection to MongoDB
        mongoClient = new MongoClient();

        // select `mydata` as testing database
        database = mongoClient.getDatabase("bgdata");

        // select collection by name `agriculture`
        collection = database.getCollection("agriculture");
    }
    
    public Collection<Agriculture> mungee(Collection<Agriculture> src) {
    	
    	ArrayList<Agriculture> list = new ArrayList<Agriculture>(); 
    	
//    	System.out.println("Data Mungee Started");
    	
    	for (Agriculture a : src)
    	{
    		//if(a.getCountry_Code()!= null && a.getElement_Code()!= null && a.getItem_Code()!=null && a.getYear_Code()!=null && a.getValue()!=null)
    		if(a.getCountry_Code().equals("") || a.getElement_Code().equals("") || a.getItem_Code().equals("") || a.getYear_Code().equals("") || a.getValue().equals("") || a.getFlag().equals("M"))
    		{
    			System.out.println("Invalid data record : "+a.getSno());
    		}
    		else
    		{
    			list.add(a);
    		}    		
    	}
    	
    	System.out.println("Data Mungee Done..."+list.size());
    	
        return list;
    }


    public void save(Collection<Agriculture> data) {

//    	List<Document> documents = new ArrayList<Document>();
    	
    	List<Document> documents = data.stream()
				.map(item -> new Document().append("Sno", item.getSno())
						.append("Country Code", item.getCountry_Code())
						.append("Country", item.getCountry())
						.append("Item Code", item.getItem_Code())
						.append("Item", item.getItem())
						.append("Element Code", item.getElement_Code())
						.append("Element", item.getElement())
						.append("Year Code", item.getYear_Code())
						.append("Year", item.getYear())
						.append("Unit", item.getUnit())
						.append("Value", item.getValue())
						.append("Flag", item.getFlag()))
				.collect(Collectors.toList());
    	
//    	for(Agriculture a :  data)
//    	{
//    		   	
//	    	Document document = new Document();
//	    	
//	        document.put("Sno", a.getSno());
//	       	document.put("Country Code", a.getCountry_Code());
//	       	document.put("Country", a.getCountry());
//	       	document.put("Item Code", a.getItem_Code());
//	       	document.put("Item", a.getItem());
//	       	document.put("Element Code", a.getElement_Code());
//	       	document.put("Element", a.getElement());
//	       	document.put("Year Code", a.getYear_Code());
//	       	document.put("Year", a.getYear());
//	       	document.put("Unit", a.getUnit());
//	       	document.put("Value", a.getValue());
//	       	document.put("Flag", a.getFlag());
//	       	
//	       	collection.insertOne(document);
//	       	
//	       	documents.add(document);
//	       	
//    	}
    	
//    	System.out.println("");
    	
    	collection.insertMany(documents);

    }
}
