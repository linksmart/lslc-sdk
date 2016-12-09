package eu.linksmart.lc.rc.types;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device {

	@SerializedName("@context")
	@Expose
	private String context;
	@SerializedName("id")
	@Expose
	private String id;
	@SerializedName("url")
	@Expose
	private String url;
	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("description")
	@Expose
	private String description;
	@SerializedName("ttl")
	@Expose
	private Integer ttl;
	@SerializedName("created")
	@Expose
	private String created;
	@SerializedName("updated")
	@Expose
	private String updated;
	@SerializedName("expires")
	@Expose
	private String expires;
	@SerializedName("resources")
	@Expose
	private List<String> resources = new ArrayList<String>();
	@SerializedName("meta")
	@Expose
	private Meta meta;

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
	* The url
	*/
	public String getUrl() {
	return url;
	}

	/**
	*
	* @param url
	* The url
	*/
	public void setUrl(String url) {
	this.url = url;
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
	* The name
	*/
	public String getName() {
	return name;
	}

	/**
	*
	* @param name
	* The name
	*/
	public void setName(String name) {
	this.name = name;
	}

	/**
	*
	* @return
	* The description
	*/
	public String getDescription() {
	return description;
	}

	/**
	*
	* @param description
	* The description
	*/
	public void setDescription(String description) {
	this.description = description;
	}

	/**
	*
	* @return
	* The ttl
	*/
	public Integer getTtl() {
	return ttl;
	}

	/**
	*
	* @param ttl
	* The ttl
	*/
	public void setTtl(Integer ttl) {
	this.ttl = ttl;
	}

	/**
	*
	* @return
	* The created
	*/
	public String getCreated() {
	return created;
	}

	/**
	*
	* @param created
	* The created
	*/
	public void setCreated(String created) {
	this.created = created;
	}

	/**
	*
	* @return
	* The updated
	*/
	public String getUpdated() {
	return updated;
	}

	/**
	*
	* @param updated
	* The updated
	*/
	public void setUpdated(String updated) {
	this.updated = updated;
	}

	/**
	*
	* @return
	* The expires
	*/
	public String getExpires() {
	return expires;
	}

	/**
	*
	* @param expires
	* The expires
	*/
	public void setExpires(String expires) {
	this.expires = expires;
	}

	/**
	*
	* @return
	* The resources
	*/
	public List<String> getResources() {
	return resources;
	}

	/**
	*
	* @param resources
	* The resources
	*/
	public void setResources(List<String> resources) {
	this.resources = resources;
	}
	
	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}
}
