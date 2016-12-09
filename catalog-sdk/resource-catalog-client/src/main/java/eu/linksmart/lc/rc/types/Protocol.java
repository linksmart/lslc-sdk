package eu.linksmart.lc.rc.types;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Protocol {

	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("endpoint")
	@Expose
	private Endpoint endpoint;
	@SerializedName("methods")
	@Expose
	private List<String> methods = new ArrayList<String>();
	@SerializedName("content-types")
	@Expose
	private List<String> contentTypes = new ArrayList<String>();
	
	/**
	*
	* @return
	* The type
	*/
	public String getType() {
	return type;
	}

	/**
	*
	* @param type
	* The type
	*/
	public void setType(String type) {
	this.type = type;
	}

	/**
	*
	* @return
	* The endpoint
	*/
	public Endpoint getEndpoint() {
	return endpoint;
	}

	/**
	*
	* @param endpoint
	* The endpoint
	*/
	public void setEndpoint(Endpoint endpoint) {
	this.endpoint = endpoint;
	}

	/**
	*
	* @return
	* The methods
	*/
	public List<String> getMethods() {
	return methods;
	}

	/**
	*
	* @param methods
	* The methods
	*/
	public void setMethods(List<String> methods) {
	this.methods = methods;
	}

	/**
	*
	* @return
	* The contentTypes
	*/
	public List<String> getContentTypes() {
	return contentTypes;
	}

	/**
	*
	* @param contentTypes
	* The content-types
	*/
	public void setContentTypes(List<String> contentTypes) {
	this.contentTypes = contentTypes;
	}
}
