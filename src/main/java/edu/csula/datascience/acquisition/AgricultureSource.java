package edu.csula.datascience.acquisition;

import com.google.common.collect.Lists;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.csula.datascience.model.Agriculture;

/**
 * An example of Source implementation using Twitter4j api to grab tweets
 */
public class AgricultureSource implements Source<Agriculture> {

	private String CsvFile;

    
    public AgricultureSource(String file) {
		// TODO Auto-generated constructor stub
    	CsvFile = file;    	
	}

	public boolean hasNext() {
        return false;
    }

    public Collection<Agriculture> next() {


    	ArrayList<Agriculture> list = new ArrayList<Agriculture>();    	
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader();
		
		try {
			
			FileReader fileReader = new FileReader(CsvFile);			
			CSVParser parser = new CSVParser(fileReader, csvFileFormat);
				
			for (CSVRecord record : parser) {
				
				Agriculture a = new Agriculture(record.getRecordNumber(),record.get("Country Code"), record.get("Country"), record.get("Item Code"), record.get("Item"), record.get("Element Code"),
						record.get("Element"), record.get("Year Code"), record.get("Year"), record.get("Unit"), record.get("Value"), record.get("Flag"));
				
				list.add(a);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Done Totally " +list.size());
		
//		System.out.println("Rec " +list.get(1048574).getValue()+" ,no :"+list.get(1048574).getSno());
//		System.out.println("Rec " +list.get(list.size()-1).getValue()+" ,no :"+list.get(list.size()-1).getSno());

        return list;
        
    }

}
