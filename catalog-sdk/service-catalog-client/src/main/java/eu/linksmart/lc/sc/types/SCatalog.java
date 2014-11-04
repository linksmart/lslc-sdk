package eu.linksmart.lc.sc.types;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SCatalog {

	@SerializedName("@context")
	@Expose
	private String context;
	@Expose
	private String id;
	@Expose
	private String type;
	@Expose
	private List<Service> services = new ArrayList<Service>();
	@Expose
	private Integer page;
	@SerializedName("per_page")
	@Expose
	private Integer perPage;
	@Expose
	private Integer total;
	
	public String getContext() {
		return context;
	}
	
	public void setContext(String context) {
		this.context = context;
	}
	
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
	
	public List<Service> getServices() {
		return services;
	}
	
	public void setServices(List<Service> services) {
		this.services = services;
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
