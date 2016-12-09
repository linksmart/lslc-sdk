package eu.linksmart.lc.rc.types;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Devices {

	@SerializedName("@context")
	@Expose
	private String context;
	@SerializedName("id")
	@Expose
	private String id;
	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("devices")
	@Expose
	private List<Device> devices = new ArrayList<Device>();
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
	* The devices
	*/
	public List<Device> getDevices() {
	return devices;
	}

	/**
	*
	* @param devices
	* The devices
	*/
	public void setDevices(List<Device> devices) {
	this.devices = devices;
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
