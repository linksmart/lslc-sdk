package eu.linksmart.lc.rc.types;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Endpoint {
	
	@SerializedName("url")
	@Expose
	private String url;
	@SerializedName("pub_topic")
	@Expose
	private String pubTopic;
	@SerializedName("sub_topic")
	@Expose
	private String subTopic;
	
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
	* The pubTopic
	*/
	public String getPubTopic() {
	return pubTopic;
	}

	/**
	*
	* @param pubTopic
	* The pub_topic
	*/
	public void setPubTopic(String pubTopic) {
	this.pubTopic = pubTopic;
	}

	/**
	*
	* @return
	* The subTopic
	*/
	public String getSubTopic() {
	return subTopic;
	}

	/**
	*
	* @param subTopic
	* The sub_topic
	*/
	public void setSubTopic(String subTopic) {
	this.subTopic = subTopic;
	}
}
