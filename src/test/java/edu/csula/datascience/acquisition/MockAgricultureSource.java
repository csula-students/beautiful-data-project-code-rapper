package edu.csula.datascience.acquisition;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;

import edu.csula.datascience.model.Agriculture;
import edu.csula.datascience.model.Climate;

public class MockAgricultureSource implements Source<Agriculture> {

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Agriculture> next() {
		return Lists.newArrayList(
	            new Agriculture(1,"12345","India","123","Item","123","Element","12","1977","2","23","flag"),
	            new Agriculture(2,null,"Syria","456",null,null,"Element","12","1977","2","23",null)
	        );
	}

	
}
