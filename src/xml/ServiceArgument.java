package xml;

public class ServiceArgument {
	
	private String name;
	private String datatype;
	private String value;
	
	public ServiceArgument() {
		name = "";
		datatype = "";
		value = "";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
