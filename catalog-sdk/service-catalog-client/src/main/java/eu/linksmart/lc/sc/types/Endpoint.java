package eu.linksmart.lc.sc.types;

import com.google.gson.annotations.Expose;

public class Endpoint {
	
	@Expose
	private String url;
	@Expose
	private String broker;
	@Expose
	private String topic;
	
	public String getURL() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

}
