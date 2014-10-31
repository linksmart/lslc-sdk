package eu.linksmart.lc.rc.types;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Resource {

	@Expose
	private String id;
	@Expose
	private String type;
	@Expose
	private String name;
	@Expose
	private Meta meta;
	@Expose
	private List<Protocol> protocols = new ArrayList<Protocol>();
	@Expose
	private Representation representation;
	@Expose
	private String device;

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

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}
}