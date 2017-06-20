package database;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.csula.datascience.model.Agriculture;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;



public class DbConnect {
	
	private MongoClient mg = null;
	private MongoDatabase db = null;
	private MongoCollection<Document> collection = null;

	private String host;
	private String port;
	private String dbName;
	private String colName;
	
	
	public DbConnect(String host, String port, String dbName, String colName) {
		
		this.host = host;
		this.port = port;
		this.dbName = dbName;
		this.colName = colName;
		
		System.out.println("Host : "+this.host+"Port : "+this.port+"DB : "+this.dbName+"Collection : "+this.colName);
		
		connect();
	}




	public void connect()
	{
		 //mg= new MongoClient(host,Integer.parseInt(port));
		 mg = new MongoClient();
		 db = mg.getDatabase(dbName);
		 System.out.println("Connect to database successfully");		       
		 collection = db.getCollection(colName);
		 System.out.println("Connect to Collection "+colName); 
	}
	
	
	
	
	
	
	
	
	public ArrayList<Agriculture> Retrive(String field, String s) {
		
		ArrayList<Agriculture> list = new ArrayList<Agriculture>();

        try {
        	
        	System.out.println("Element Code : "+s);
        	
           	FindIterable<Document> iterable = collection.find(new Document(field,s));
 
        		
           	if (iterable.first()==null)
           	{
           		System.out.println("Nothing");
           	}
            for(Document doc : iterable) { 
            	
            	System.out.println(doc);

//               DBObject resultElement = cursor.next();
//
//               Map resultElementMap = resultElement.toMap();
//
//               Collection resultValues = resultElementMap.values();
//               
//               Raw r = new Raw(resultValues.toArray()[1].toString(), resultValues.toArray()[2].toString(), resultValues.toArray()[3].toString());
//               
//               list.add(r);

            }

        }
        catch (MongoException e) {
        	
        	System.out.println(e);
        	e.printStackTrace();
            return null;
            
        }

        return list;
    }




	public ArrayList<Agriculture> RetriveAgri() {
		
		
		
		FindIterable<Document> iterable;
		
		ArrayList<Agriculture> agri = new ArrayList<Agriculture>();

        try {
        	
//        	System.out.println("Element Code : ");
        	
        	BasicDBObject whereQuery = new BasicDBObject();
//    	    whereQuery.put("Country", "India");
    	    whereQuery.put("Element Code", "5510");
        	
           	iterable = collection.find(whereQuery);
           	
           	
           	
    		
    		for (Document doc : iterable)
    		{
//    			System.out.println(doc.get("Sno"));
    			
    			Agriculture a = new Agriculture((Long) doc.get("Sno"), (String)doc.get("Country Code"), (String)doc.get("Country"), (String)doc.get("Item Code"), (String)doc.get("Item"), (String)doc.get("Element Code"), (String)doc.get("Element"), (String)doc.get("Year Code"), (String)doc.get("Year"), (String)doc.get("Unit"), (String)doc.get("Value"), (String)doc.get("Flag"));
    			
    			agri.add(a);
    			
    		}
 
        		
           	if (iterable.first()==null)
           	{
           		System.out.println("Nothing");
           	}
 

        }
        catch (MongoException e) {
        	
        	System.out.println(e);
        	e.printStackTrace();
            return null;
            
        }

        return agri;
    }
	
	public Double Retrive_Val(int country, int year, int item_Code, int element_code) {
		// TODO Auto-generated method stub
		BasicDBObject whereQuery = new BasicDBObject();
	    whereQuery.put("Country Code", Integer.toString(country));
	    whereQuery.put("Element Code", Integer.toString(element_code));
	    whereQuery.put("Item Code", Integer.toString(item_Code));
	    whereQuery.put("Year", ""+year+"");
		FindIterable<Document> out  = collection.find(whereQuery);
		
		if (out.first()!=null)
       	{
//       		System.out.println((String)out.first().get("Value"));  
       		return Double.parseDouble((String)out.first().get("Value"));
       	}
		else 
		{
			return null;
		}
	}
	
	
	public Double Retrive_Out(int Country_Code,int Element_Code,int Item_Code,int Year)
	{
		
		BasicDBObject whereQuery = new BasicDBObject();
	    whereQuery.put("Country Code", Integer.toString(Country_Code));
	    whereQuery.put("Element Code", Integer.toString(Element_Code));
	    whereQuery.put("Item Code", Integer.toString(Item_Code));
	    whereQuery.put("Year", ""+Year+"");
		FindIterable<Document> out  = collection.find(whereQuery);
		
		
		if (out.first()!=null)
       	{
//       		System.out.println((String)out.first().get("Value"));  
       		return Double.parseDouble((String)out.first().get("Value"));
       	}
		else 
		{
			return null;
		}
	}
	
	
	public Double RetriveTemp(String country, int year) {
		// TODO Auto-generated method stub
		
		BasicDBObject whereQuery = new BasicDBObject();
	    whereQuery.put("Country", country);
//	    whereQuery.put("Year", year);
	    
//	    BasicDBObject q = new BasicDBObject();
	    whereQuery.put("dt",  java.util.regex.Pattern.compile(""+year+""));
	    
	    FindIterable<Document> out  = collection.find(whereQuery).sort(new Document("AverageTemperature",-1)).limit(50); 
//	    FindIterable<Document> out  = collection.find(whereQuery).limit(50); 
	    int count = 0;
	    Double sum = 0.0;
	    if(out.first()==null)
	    {
	    	return null;
	    }
	    for (Document doc : out)
	    {
	    	count = count+1;
	    	sum = sum + ((Double) doc.get("AverageTemperature"));
//	    	System.out.println("sum: "+sum+" count : "+count+" Avg : "+sum/count);
	    }
		return sum/count;
	}
	
	
}