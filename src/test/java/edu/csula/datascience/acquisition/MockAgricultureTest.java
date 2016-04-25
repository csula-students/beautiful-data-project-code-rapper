package edu.csula.datascience.acquisition;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.csula.datascience.Model.Agriculture;
import edu.csula.datascience.Model.Climate;

public class MockAgricultureTest {

	private Collector<Agriculture, Agriculture> collector;
	private Source<Agriculture> source;

	@Before
	public void setup() {
		collector = new MockAgricultureCollector();
		source = new MockAgricultureSource();
	}

	@Test
	public void mungee() throws Exception {
		List<Agriculture> list = (List<Agriculture>) collector.mungee(source.next());
		List<Agriculture> expectedList = Lists.newArrayList(
				new Agriculture(1, "12345", "India", "123", "Item", "123", "Element", "12", "1977", "2", "23", "flag"));

		Assert.assertEquals(list.size(), 1);

		for (int i = 0; i < 1; i++) {

			Assert.assertEquals(list.get(i).getCountry(), expectedList.get(i).getCountry());
			Assert.assertEquals(list.get(i).getElement(), expectedList.get(i).getElement());
			Assert.assertEquals(list.get(i).getYear(), expectedList.get(i).getYear());

		}
	}

}
