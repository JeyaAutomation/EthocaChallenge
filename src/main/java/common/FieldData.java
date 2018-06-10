package common;


import java.io.Serializable;

public class FieldData implements Serializable {
	private static final long serialVersionUID = 1000000L;
	  public String name;
	  public String value;
	  public String expected;
	  
	  public FieldData(String name, String value)
	  {
	    this.name = name;
	    this.value = value;
	    this.expected = value;
	  }
	  
	  public FieldData(String name, String value, String expected)
	  {
	    this.name = name;
	    this.value = value;
	    this.expected = expected;
	  }
	}
