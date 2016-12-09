package eu.linksmart.lc.sc.types;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service {

	@Expose
	private String id;
	@Expose
	private String type;
	@SerializedName("url")
	@Expose
	private String url;
	@Expose
	private String name;
	@Expose
	private String description;
	@Expose
	private Meta meta;
	@Expose
	private List<Protocol> protocols = new ArrayList<Protocol>();
	@Expose
	private Representation representation;
	@Expose
	private Integer ttl;
	@Expose
	private String created;
	@Expose
	private String updated;
	@Expose
	private String expires;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
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
	
	public void setDescription(String description) {
		this.description = description;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public List<Protocol> getProtocols() {
		return protocols;
	}

	public void setProtocols(List<Protocol> protocols) {
		this.protocols = protocols;
	}

	public Representation getRepresentation() {
		return representation;
	}

	public void setRepresentation(Representation representation) {
		this.representation = representation;
	}
	
	public Integer getTtl() {
		return ttl;
	}
	
	public void setTtl(Integer ttl) {
		this.ttl = ttl;
	}
	
	public String getCreated() {
		return created;
	}
	
	public void setCreated(String created) {
		this.created = created;
	}
	
	public String getUpdated() {
		return updated;
	}
	
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	
	public String getExpires() {
		return expires;
	}
	
	public void setExpires(String expires) {
		this.expires = expires;
	}
}