package eu.linksmart.lc.rc.types;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Catalog {

	@Expose
	private String id;
	@Expose
	private String type;
	@SerializedName("@context")
	@Expose
	private String context;
	@Expose
	private Devices devices;
	@Expose
	private List<Resource> resources = new ArrayList<Resource>();
	@Expose
	private Integer page;
	@SerializedName("per_page")
	@Expose
	private Integer perPage;
	@Expose
	private Integer total;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getContext() {
		return context;
	}
	
	public void setContext(String context) {
		this.context = context;
	}
	
	public Devices getDevices() {
		return devices;
	}
	
	public void setDevices(Devices devices) {
		this.devices = devices;
	}
	
	public List<Resource> getResources() {
		return resources;
	}
	
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	
	public Integer getPage() {
		return page;
	}
		
	public void setPage(Integer page) {
		this.page = page;
	}
		
	public Integer getPerPage() {
		return perPage;
	}
		
	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
	}
		
	public Integer getTotal() {
		return total;
	}
		
	public void setTotal(Integer total) {
		this.total = total;
	}
}
