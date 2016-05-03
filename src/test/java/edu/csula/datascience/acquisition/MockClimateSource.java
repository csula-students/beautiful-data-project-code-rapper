package edu.csula.datascience.acquisition;

import java.util.ArrayList;



import java.util.Collection;

import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;

import edu.csula.datascience.model.Climate;

public class MockClimateSource implements Source<Climate> {

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Climate> next() {
		return Lists.newArrayList(
	            new Climate("1797-01-01", "10.5","12.5","Denmark"),
	            new Climate("2010-05-01", null,null,"India")
	        );
	}

	
}
