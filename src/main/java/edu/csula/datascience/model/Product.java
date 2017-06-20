package edu.csula.datascience.model;

public class Product {

  
    private String Country;                       
    private String Item;    
    private String Element;
    private String Duration;   
    private int Year;     
    private String Unit;    
    private Double W_XValue;  
    private Double W_XValue_p_1;
    private Double W_XValue_P_5;
    private Double W_XValue_P_10;
    private Double W_XValue_f_1;
    private Double W_XValue_f_5;
    private Double W_XValue_f_10;
    private Double O_XValue; 	
    private Double O_XValue_p_1;
    private Double O_XValue_p_5;
    private Double O_XValue_p_10;            
    private Double O_XValue_f_1;
    private Double O_XValue_f_5;
    private Double O_XValue_f_10;  
    private Double W_per_inc;  
    private Double O_per_inc;
    
    
	public Product(String country, String item, String element, String duration, int year, String unit, Double w_XValue,
			Double w_XValue_p_1, Double w_XValue_P_5, Double w_XValue_P_10, Double w_XValue_f_1, Double w_XValue_f_5,
			Double w_XValue_f_10, Double o_XValue, Double o_XValue_p_1, Double o_XValue_p_5, Double o_XValue_p_10,
			Double o_XValue_f_1, Double o_XValue_f_5, Double o_XValue_f_10, Double w_per_inc, Double o_per_inc) {
		super();
		Country = country;
		Item = item;
		Element = element;
		Duration = duration;
		Year = year;
		Unit = unit;
		W_XValue = w_XValue;
		W_XValue_p_1 = w_XValue_p_1;
		W_XValue_P_5 = w_XValue_P_5;
		W_XValue_P_10 = w_XValue_P_10;
		W_XValue_f_1 = w_XValue_f_1;
		W_XValue_f_5 = w_XValue_f_5;
		W_XValue_f_10 = w_XValue_f_10;
		O_XValue = o_XValue;
		O_XValue_p_1 = o_XValue_p_1;
		O_XValue_p_5 = o_XValue_p_5;
		O_XValue_p_10 = o_XValue_p_10;
		O_XValue_f_1 = o_XValue_f_1;
		O_XValue_f_5 = o_XValue_f_5;
		O_XValue_f_10 = o_XValue_f_10;
		W_per_inc = w_per_inc;
		O_per_inc = o_per_inc;
	}


	public String getCountry() {
		return Country;
	}


	public void setCountry(String country) {
		Country = country;
	}


	public String getItem() {
		return Item;
	}


	public void setItem(String item) {
		Item = item;
	}


	public String getElement() {
		return Element;
	}


	public void setElement(String element) {
		Element = element;
	}


	public String getDuration() {
		return Duration;
	}


	public void setDuration(String duration) {
		Duration = duration;
	}


	public int getYear() {
		return Year;
	}


	public void setYear(int year) {
		Year = year;
	}


	public String getUnit() {
		return Unit;
	}


	public void setUnit(String unit) {
		Unit = unit;
	}


	public Double getW_XValue() {
		return W_XValue;
	}


	public void setW_XValue(Double w_XValue) {
		W_XValue = w_XValue;
	}


	public Double getW_XValue_p_1() {
		return W_XValue_p_1;
	}


	public void setW_XValue_p_1(Double w_XValue_p_1) {
		W_XValue_p_1 = w_XValue_p_1;
	}


	public Double getW_XValue_P_5() {
		return W_XValue_P_5;
	}


	public void setW_XValue_P_5(Double w_XValue_P_5) {
		W_XValue_P_5 = w_XValue_P_5;
	}


	public Double getW_XValue_P_10() {
		return W_XValue_P_10;
	}


	public void setW_XValue_P_10(Double w_XValue_P_10) {
		W_XValue_P_10 = w_XValue_P_10;
	}


	public Double getW_XValue_f_1() {
		return W_XValue_f_1;
	}


	public void setW_XValue_f_1(Double w_XValue_f_1) {
		W_XValue_f_1 = w_XValue_f_1;
	}


	public Double getW_XValue_f_5() {
		return W_XValue_f_5;
	}


	public void setW_XValue_f_5(Double w_XValue_f_5) {
		W_XValue_f_5 = w_XValue_f_5;
	}


	public Double getW_XValue_f_10() {
		return W_XValue_f_10;
	}


	public void setW_XValue_f_10(Double w_XValue_f_10) {
		W_XValue_f_10 = w_XValue_f_10;
	}


	public Double getO_XValue() {
		return O_XValue;
	}


	public void setO_XValue(Double o_XValue) {
		O_XValue = o_XValue;
	}


	public Double getO_XValue_p_1() {
		return O_XValue_p_1;
	}


	public void setO_XValue_p_1(Double o_XValue_p_1) {
		O_XValue_p_1 = o_XValue_p_1;
	}


	public Double getO_XValue_p_5() {
		return O_XValue_p_5;
	}


	public void setO_XValue_p_5(Double o_XValue_p_5) {
		O_XValue_p_5 = o_XValue_p_5;
	}


	public Double getO_XValue_p_10() {
		return O_XValue_p_10;
	}


	public void setO_XValue_p_10(Double o_XValue_p_10) {
		O_XValue_p_10 = o_XValue_p_10;
	}


	public Double getO_XValue_f_1() {
		return O_XValue_f_1;
	}


	public void setO_XValue_f_1(Double o_XValue_f_1) {
		O_XValue_f_1 = o_XValue_f_1;
	}


	public Double getO_XValue_f_5() {
		return O_XValue_f_5;
	}


	public void setO_XValue_f_5(Double o_XValue_f_5) {
		O_XValue_f_5 = o_XValue_f_5;
	}


	public Double getO_XValue_f_10() {
		return O_XValue_f_10;
	}


	public void setO_XValue_f_10(Double o_XValue_f_10) {
		O_XValue_f_10 = o_XValue_f_10;
	}


	public Double getW_per_inc() {
		return W_per_inc;
	}


	public void setW_per_inc(Double w_per_inc) {
		W_per_inc = w_per_inc;
	}


	public Double getO_per_inc() {
		return O_per_inc;
	}


	public void setO_per_inc(Double o_per_inc) {
		O_per_inc = o_per_inc;
	}	   
    
    
	
	
}
