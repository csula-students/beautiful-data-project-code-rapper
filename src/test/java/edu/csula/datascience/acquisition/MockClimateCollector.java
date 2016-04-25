package edu.csula.datascience.acquisition;

import java.util.Collection;
import java.util.stream.Collectors;

//import edu.csula.datascience.Model.Climate;

public class MockClimateCollector implements Collector<Climate, Climate> {

	@Override
	public Collection<Climate> mungee(Collection<Climate> src) {
		return src.stream()
				.filter(data -> data.getCountry() != null && data.getAverageTemperature() != null
						&& data.getAvgTempUnc() != null && data.getCountry() != null)
				.map(Climate::build).collect(Collectors.toList());
	}

	@Override
	public void save(Collection<Climate> data) {
		// TODO Auto-generated method stub

	}

}
