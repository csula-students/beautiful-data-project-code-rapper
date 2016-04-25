package edu.csula.datascience.acquisition;

import twitter4j.Status;

import java.util.Collection;

import edu.csula.datascience.model.Agriculture;

/**
 * A simple example of using Twitter
 */
public class CollectorApp {
    public static void main(String[] args) {
    	AgricultureSource source = new AgricultureSource("C:/Users/genus_000/Desktop/Spring 2016/CS594-Big Data/Production_Crops_E_All_Data_(Norm)/Production_Crops_E_All_Data_(Norm).csv");
    	AgricultureCollector collector = new AgricultureCollector();
        Collection<Agriculture> records  = source.next();
        Collection<Agriculture> data = collector.mungee(records);
        collector.save(data);

    }
}
