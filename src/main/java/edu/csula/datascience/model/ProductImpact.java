package edu.csula.datascience.model;

public class ProductImpact {
	
	private Long Sno;
	private int Country_Code;   
    private String Country;              
    private int Item_Code;           
    private String Item;    
    private String Element;
    private int Element_Code;
    private String Duration;   
    private int Year;     
    private int Outcome_Code;
    private String Unit;    
    // Temperature Data
    private Double W_XValue;   // Average Temp of the year
    private Double W_XValue_p_1;
    private Double W_XValue_P_5;
    private Double W_XValue_P_10;
    private Double W_XValue_f_1;
    private Double W_XValue_f_5;
    private Double W_XValue_f_10;
    // Production Data
    private Double O_XValue; 	// Value of producction in tons
    private Double O_XValue_p_1;
    private Double O_XValue_p_5;
    private Double O_XValue_p_10;            
    private Double O_XValue_f_1;
    private Double O_XValue_f_5;
    private Double O_XValue_f_10;  
    // Second Order
    private Double W_per_inc;  // Weather percentage increase 
    private Double O_per_inc;	// Production percentage increase
    
    
    private String Flag;
	





	




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


	public ProductImpact(Long sno, int country_Code, String country, int item_Code, String item, String element,
			int element_Code, String duration, int year, int outcome_Code, String unit, Double w_XValue,
			Double w_XValue_p_1, Double w_XValue_P_5, Double w_XValue_P_10, Double w_XValue_f_1, Double w_XValue_f_5,
			Double w_XValue_f_10, Double o_XValue, Double o_XValue_p_1, Double o_XValue_p_5, Double o_XValue_p_10,
			Double o_XValue_f_1, Double o_XValue_f_5, Double o_XValue_f_10, String flag) {
		super();
		Sno = sno;
		Country_Code = country_Code;
		Country = country;
		Item_Code = item_Code;
		Item = item;
		Element = element;
		Element_Code = element_Code;
		Duration = duration;
		Year = year;
		Outcome_Code = outcome_Code;
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
		Flag = flag;
	}


	public ProductImpact() {
		// TODO Auto-generated constructor stub
	}
	
	
	

	public Long getSno() {
		return Sno;
	}




	public void setSno(Long sno) {
		Sno = sno;
	}




	public int getCountry_Code() {
		return Country_Code;
	}




	public void setCountry_Code(int country_Code) {
		Country_Code = country_Code;
	}




	public int getItem_Code() {
		return Item_Code;
	}




	public void setItem_Code(int item_Code) {
		Item_Code = item_Code;
	}




	public int getElement_Code() {
		return Element_Code;
	}




	public void setElement_Code(int element_Code) {
		Element_Code = element_Code;
	}








	public String getDuration() {
		return Duration;
	}




	public void setDuration(String duration) {
		Duration = duration;
	}




	public int getOutcome_Code() {
		return Outcome_Code;
	}




	public void setOutcome_Code(int outcome_Code) {
		Outcome_Code = outcome_Code;
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

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public String getFlag() {
		return Flag;
	}

	public void setFlag(String flag) {
		Flag = flag;
	}




	public int getYear() {
		return Year;
	}




	public void setYear(int year) {
		Year = year;
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
	
	

	
	
    

}
