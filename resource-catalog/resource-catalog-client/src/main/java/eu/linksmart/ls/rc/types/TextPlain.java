package eu.linksmart.ls.rc.types;

import com.google.gson.annotations.Expose;

public class TextPlain {

	@Expose
	private String type;
	
	public String getType() {
	return type;
	}
	
	public void setType(String type) {
	this.type = type;
	}

}
