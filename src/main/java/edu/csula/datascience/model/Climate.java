package edu.csula.datascience.model;

import edu.csula.datascience.acquisition.MockData;
import edu.csula.datascience.acquisition.SimpleModel;

public class Climate {

	String dt;
	String AverageTemperature;
	String AvgTempUnc;
	String country;

	public static Climate build(Climate data) {
		return new Climate(data.getDt(), data.getAverageTemperature(), data.getAvgTempUnc(), data.getCountry());
	}

	public Climate(String dt, String averageTemperature, String avgTempUnc, String country) {
		super();
		this.dt = dt;
		this.AverageTemperature = averageTemperature;
		this.AvgTempUnc = avgTempUnc;
		this.country = country;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public String getAverageTemperature() {
		return AverageTemperature;
	}

	public void setAverageTemperature(String averageTemperature) {
		AverageTemperature = averageTemperature;
	}

	public String getAvgTempUnc() {
		return AvgTempUnc;
	}

	public void setAvgTempUnc(String avgTempUnc) {
		AvgTempUnc = avgTempUnc;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
