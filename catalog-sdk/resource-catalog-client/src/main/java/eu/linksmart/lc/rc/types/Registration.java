package eu.linksmart.lc.rc.types;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Registration {

	@Expose
	private String name;
	@Expose
	private String description;
	@Expose
	private Meta meta;
	@Expose
	private Integer ttl;
	@Expose
	private List<Resource> resources = new ArrayList<Resource>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTtl() {
		return ttl;
	}

	public void setTtl(Integer ttl) {
		this.ttl = ttl;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
}