package eu.linksmart.lc.rc.types;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Catalog {

	@Expose
	private String description;
	
	@SerializedName("api_version")
	@Expose
	private String apiVersion;
	
	@SerializedName("total_devices")
	@Expose
	private Integer totalDevices;
	
	@SerializedName("total_resources")
	@Expose
	private Integer totalResources;
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getApiVersion() {
		return apiVersion;
	}
	
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	
	public Integer getDevices() {
		return totalDevices;
	}
	
	public void setDevices(Integer totalDevices) {
		this.totalDevices = totalDevices;
	}
	
	public Integer getResources() {
		return totalDevices;
	}
	
	public void setResources(Integer totalResources) {
		this.totalResources = totalResources;
	}
}
