package edu.csula.datascience.acquisition;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import edu.csula.datascience.model.Agriculture;
import edu.csula.datascience.model.Climate;

/**
 * An example of Source implementation using Twitter4j api to grab tweets
 */
public class AgricultureSource implements Source<List<Agriculture>> {

	private String CsvFile;

    
    public AgricultureSource(String file) {
		// TODO Auto-generated constructor stub
    	CsvFile = file;    	
	}

	public boolean hasNext() {
        return false;
    }
	
	@Override
    public Collection<List<Agriculture>> next() {


    	//List<Agriculture> list = Lists.newArrayList();
		List<Agriculture> list = new ArrayList<Agriculture>();
    	List<List<Agriculture>> agriculture_list = new ArrayList<List<Agriculture>>();
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader();
		int count = 0 ;
		int item = 0;
		
		try {
			
//			FileReader fileReader = new FileReader(CsvFile);			
//			CSVParser parser = new CSVParser(fileReader, csvFileFormat);
			
			Reader in = new FileReader(CsvFile);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
			
			System.out.println("Agriculture Start ...");
				
			for (CSVRecord record : records) {
				item++;
				
				Agriculture a = new Agriculture(record.getRecordNumber(),record.get("Country Code"), record.get("Country"), record.get("Item Code"), record.get("Item"), record.get("Element Code"),
						record.get("Element"), record.get("Year Code"), record.get("Year"), record.get("Unit"), record.get("Value"), record.get("Flag"));
				
				list.add(a);
				
				
				if(list.size() == 50000 )
				{
					count++;
					agriculture_list.add(list);
					
					System.out.println("Agriculture Set : "+count+" list.size :"+list.size() );
//					list.clear();
					list = new ArrayList<Agriculture>();
					
				}
				
					
				
			}
			agriculture_list.add(list);
			
			System.out.println("Agriculture end ...");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Done Totally " +item);
		
//		System.out.println("Rec " +list.get(1048574).getValue()+" ,no :"+list.get(1048574).getSno());
//		System.out.println("Rec " +list.get(list.size()-1).getValue()+" ,no :"+list.get(list.size()-1).getSno());
		
		
		for (List<Agriculture> records :  agriculture_list)
		{
			System.out.println("Before send  : "+ records.size());
		}

        return agriculture_list;
        
    }

}
