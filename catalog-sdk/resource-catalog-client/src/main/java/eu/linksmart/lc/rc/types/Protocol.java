package eu.linksmart.lc.rc.types;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Protocol {

	@Expose
	private String type;
	@Expose
	private Endpoint endpoint;
	@Expose
	private List<String> methods = new ArrayList<String>();
	@SerializedName("content-types")
	@Expose
	private List<String> contentTypes = new ArrayList<String>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	public List<String> getMethods() {
		return methods;
	}

	public void setMethods(List<String> methods) {
		this.methods = methods;
	}

	public List<String> getContentTypes() {
		return contentTypes;
	}

	public void setContentTypes(List<String> contentTypes) {
		this.contentTypes = contentTypes;
	}
}
