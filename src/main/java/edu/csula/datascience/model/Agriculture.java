package edu.csula.datascience.model;

public class Agriculture {

	private long Sno;
	private String Country_Code;
	private String Country;
	private String Item_Code;
	private String Item;
	private String Element_Code;
	private String Element;
	private String Year_Code;
	private String Year;
	private String Unit;
	private String Value;
	private String Flag;

	public Agriculture(long sno, String country_Code, String country, String item_Code, String item,
			String element_Code, String element, String year_Code, String year, String unit, String value,
			String flag) {
		super();
		Sno = sno;
		Country_Code = country_Code;
		Country = country;
		Item_Code = item_Code;
		Item = item;
		Element_Code = element_Code;
		Element = element;
		Year_Code = year_Code;
		Year = year;
		Unit = unit;
		Value = value;
		Flag = flag;
	}

	public static Agriculture build(Agriculture data) {
		return new Agriculture(data.getSno(), data.getCountry_Code(), data.getCountry(), data.getItem_Code(),
				data.getItem(), data.getElement_Code(), data.getElement(), data.getYear_Code(), data.getYear(),
				data.getUnit(), data.getValue(), data.getFlag());
	}

	public long getSno() {
		return Sno;
	}

	public void setSno(long sno) {
		Sno = sno;
	}

	public String getCountry_Code() {
		return Country_Code;
	}

	public void setCountry_Code(String country_Code) {
		Country_Code = country_Code;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getItem_Code() {
		return Item_Code;
	}

	public void setItem_Code(String item_Code) {
		Item_Code = item_Code;
	}

	public String getItem() {
		return Item;
	}

	public void setItem(String item) {
		Item = item;
	}

	public String getElement_Code() {
		return Element_Code;
	}

	public void setElement_Code(String element_Code) {
		Element_Code = element_Code;
	}

	public String getElement() {
		return Element;
	}

	public void setElement(String element) {
		Element = element;
	}

	public String getYear_Code() {
		return Year_Code;
	}

	public void setYear_Code(String year_Code) {
		Year_Code = year_Code;
	}

	public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

	public String getFlag() {
		return Flag;
	}

	public void setFlag(String flag) {
		Flag = flag;
	}

}
