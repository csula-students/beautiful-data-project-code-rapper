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

import com.google.common.collect.Lists;

import edu.csula.datascience.model.Climate;

public class climateSource implements Source<List<Climate>>{
	
	String filename;
	
	public climateSource(String filename)
	{
		this.filename = filename;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<List<Climate>> next() {
		// TODO Auto-generated method stub'
		List<Climate> climateList= Lists.newArrayList();
		
		List<String> countries = new ArrayList();
		List<List<Climate>> climate_climateList = new ArrayList<List<Climate>>();
		
		int i=0;
		try {
			Reader in = new FileReader(filename);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
			
			for (CSVRecord record : records) {
				i++;
				String country = record.get("Country");
				String averageTemperature = record.get("AverageTemperature");
				String averageTemperatureUncertainty = record.get("AverageTemperatureUncertainty");
				String date = record.get("dt");
				
				if(!countries.contains(country))
					
				{
					countries.add(country);
				}
				
				Climate climateValue = new Climate(date,averageTemperature,averageTemperatureUncertainty,country);
				climateList.add(climateValue);
				
				if(climateList.size() == 2796493 )
				{
				climate_climateList.add(climateList);
				climateList.clear();
			}
				
				
				System.out.println(i);
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return climate_climateList;
	}

	

	
}
