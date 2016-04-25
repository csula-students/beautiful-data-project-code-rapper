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
    	
    	String fileName = "C:/Users/genus_000/Desktop/Spring 2016/CS594-Big Data/DataSet/GlobalLandTemperaturesByCity.csv";
		climateSource source = new climateSource(fileName);
		climateCollector collector = new climateCollector();

		Collection<List<Climate>> climateList = source.next();
		System.out.println("After fetching size =" + climateList.size());

		for (List<Climate> climate : climateList) {
			Collection<Climate> cleanedList = collector.mungee(climate);

			collector.save(cleanedList);
		}
    	
    	AgricultureSource source2 = new AgricultureSource("C:/Users/genus_000/Desktop/Spring 2016/CS594-Big Data/DataSet/Production_Crops_E_All_Data_(Norm).csv");
    	AgricultureCollector collector2 = new AgricultureCollector();
        Collection<Agriculture> records  = source2.next();
        Collection<Agriculture> data = collector2.mungee(records);
        collector2.save(data);

    }
}
