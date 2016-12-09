package eu.linksmart.lc.rc.types;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resources {
	
	@SerializedName("@context")
	@Expose
	private String context;
	@SerializedName("id")
	@Expose
	private String id;
	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("resources")
	@Expose
	private List<Resource> resources = new ArrayList<Resource>();
	@SerializedName("page")
	@Expose
	private Integer page;
	@SerializedName("per_page")
	@Expose
	private Integer perPage;
	@SerializedName("total")
	@Expose
	private Integer total;

	/**
	*
	* @return
	* The context
	*/
	public String getContext() {
	return context;
	}

	/**
	*
	* @param context
	* The @context
	*/
	public void setContext(String context) {
	this.context = context;
	}

	/**
	*
	* @return
	* The id
	*/
	public String getId() {
	return id;
	}

	/**
	*
	* @param id
	* The id
	*/
	public void setId(String id) {
	this.id = id;
	}

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
	* The resources
	*/
	public List<Resource> getResources() {
	return resources;
	}

	/**
	*
	* @param resources
	* The resources
	*/
	public void setResources(List<Resource> resources) {
	this.resources = resources;
	}

	/**
	*
	* @return
	* The page
	*/
	public Integer getPage() {
	return page;
	}

	/**
	*
	* @param page
	* The page
	*/
	public void setPage(Integer page) {
	this.page = page;
	}

	/**
	*
	* @return
	* The perPage
	*/
	public Integer getPerPage() {
	return perPage;
	}

	/**
	*
	* @param perPage
	* The per_page
	*/
	public void setPerPage(Integer perPage) {
	this.perPage = perPage;
	}

	/**
	*
	* @return
	* The total
	*/
	public Integer getTotal() {
	return total;
	}

	/**
	*
	* @param total
	* The total
	*/
	public void setTotal(Integer total) {
	this.total = total;
	}

}
