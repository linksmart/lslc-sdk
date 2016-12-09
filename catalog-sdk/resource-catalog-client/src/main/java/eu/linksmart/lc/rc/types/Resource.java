package eu.linksmart.lc.rc.types;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resource {

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
	@SerializedName("protocols")
	@Expose
	private List<Protocol> protocols = new ArrayList<Protocol>();
	@SerializedName("device")
	@Expose
	private String device;
	@Expose
	private Meta meta;
	@Expose
	private Representation representation;

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
	* The protocols
	*/
	public List<Protocol> getProtocols() {
	return protocols;
	}

	/**
	*
	* @param protocols
	* The protocols
	*/
	public void setProtocols(List<Protocol> protocols) {
	this.protocols = protocols;
	}

	/**
	*
	* @return
	* The device
	*/
	public String getDevice() {
	return device;
	}

	/**
	*
	* @param device
	* The device
	*/
	public void setDevice(String device) {
	this.device = device;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public Representation getRepresentation() {
		return representation;
	}

	public void setRepresentation(Representation representation) {
		this.representation = representation;
	}
}