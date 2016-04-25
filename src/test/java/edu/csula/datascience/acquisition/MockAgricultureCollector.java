package edu.csula.datascience.acquisition;

import java.util.Collection;
import java.util.stream.Collectors;

import edu.csula.datascience.Model.Agriculture;
import edu.csula.datascience.Model.Climate;

public class MockAgricultureCollector implements Collector<Agriculture, Agriculture> {

	@Override
	public Collection<Agriculture> mungee(Collection<Agriculture> src) {
		return src.stream()
				.filter(data -> data.getSno() != 0 && data.getFlag() != null && data.getCountry_Code() != null
						&& data.getCountry() != null && data.getItem_Code() != null && data.getItem() != null
						&& data.getElement_Code() != null && data.getElement() != null && data.getYear_Code() != null
						&& data.getYear() != null && data.getUnit() != null && data.getValue() != null)
				.map(Agriculture::build).collect(Collectors.toList());
	}

	@Override
	public void save(Collection<Agriculture> data) {
		// TODO Auto-generated method stub

	}

}
