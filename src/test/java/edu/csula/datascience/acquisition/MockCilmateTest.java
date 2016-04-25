package edu.csula.datascience.acquisition;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.csula.datascience.model.Climate;


public class MockCilmateTest {
	
	private Collector<Climate, Climate> collector;
	private Source<Climate> source;
	
	 @Before
	    public void setup() {
	        collector = new MockClimateCollector();
	        source = new MockClimateSource();
	    }
	 
	 @Test
	    public void mungee() throws Exception {
	        List<Climate> list = (List<Climate>) collector.mungee(source.next());
	        List<Climate> expectedList = Lists.newArrayList(
	        		new Climate("1797-01-01", "10.5","12.5","Denmark")
	        );

	        Assert.assertEquals(list.size(), 1);

	        for (int i = 0; i < 1; i ++) {
	        	
	        	Assert.assertEquals(list.get(i).getCountry(), expectedList.get(i).getCountry());
	        	Assert.assertEquals(list.get(i).getAverageTemperature(), expectedList.get(i).getAverageTemperature());
	        	Assert.assertEquals(list.get(i).getAvgTempUnc(), expectedList.get(i).getAvgTempUnc());
	        	
	        }
	    }


}
