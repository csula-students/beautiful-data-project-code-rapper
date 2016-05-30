package edu.csula.datascience.acquisition;

import twitter4j.Status;

import java.util.Collection;
import java.util.List;

import edu.csula.datascience.model.Agriculture;
import edu.csula.datascience.model.Climate;

/**
 * A simple example of using Twitter
 */
public class CollectorApp {
    public static void main(String[] args) {
    	
    	int count = 0;
    	int item_count = 0;

//    	AgricultureSource source = new AgricultureSource("C:/Users/genus_000/Desktop/Spring 2016/CS594-Big Data/Production_Crops_E_All_Data_(Norm)/Production_Crops_E_All_Data_(Norm).csv");
//    	AgricultureCollector collector = new AgricultureCollector();
//        Collection<List<Agriculture>> recordsList  = source.next();
//        
//        System.out.println("After fetching size =" + recordsList.size());
//        
//        System.out.println("Agriculture Insert Start ...");
//        for (List<Agriculture> records : recordsList) {
//        	count++;
//        	System.out.println("to_mungee  : "+ records.size());
//        	Collection<Agriculture> data = collector.mungee(records);
//        	item_count= item_count + data.size();
//            collector.save(data);
//            System.out.println("Agriculture Insert Set : "+count +" item_count : "+ item_count);
//        }
//        System.out.println("Agriculture Insert End ...");
//        count =0;

    	
    	String fileName = "C:/Users/genus_000/Desktop/Spring 2016/CS594-Big Data/DataSet/GlobalLandTemperaturesByCountry.csv";//GlobalLandTemperaturesByCity.csv
		climateSource source2 = new climateSource(fileName);
		climateCollector collector2 = new climateCollector();

		Collection<List<Climate>> climateList = source2.next();
		System.out.println("After fetching size =" + climateList.size());
		
		System.out.println("Climate Insert Start ...");

		for (List<Climate> climate : climateList) {
			Collection<Climate> cleanedList = collector2.mungee(climate);
			count++;
			collector2.save(cleanedList);
			System.out.println("Climate Set : "+count);
		}
		
		System.out.println("Climate Insert End ...");
//		
		
		
		
    	
//    	AgricultureSource source2 = new AgricultureSource("C:/Users/genus_000/Desktop/Spring 2016/CS594-Big Data/DataSet/Production_Crops_E_All_Data_(Norm).csv");
//    	AgricultureCollector collector2 = new AgricultureCollector();
//        Collection<Agriculture> records  = source2.next();
//        Collection<Agriculture> data = collector2.mungee(records);
//        collector2.save(data);


    }
}
