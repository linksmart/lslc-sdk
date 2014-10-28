package eu.linksmart.lc.rc.types;

import com.google.gson.annotations.Expose;

public class ApplicationJson {
	
	@Expose
	private String jsonString;
	
	public String getJsonString() {
		return jsonString;
	}
	
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	} 

}
