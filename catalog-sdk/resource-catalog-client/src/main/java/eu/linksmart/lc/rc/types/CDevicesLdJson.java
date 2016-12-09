package eu.linksmart.lc.rc.types;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CDevicesLdJson {
	
	@SerializedName("application/ld+json")
	@Expose
	private DevicesLdJson devicesLdJson;

	/**
	*
	* @return
	* The devicesLdJson
	*/
	public DevicesLdJson getDevicesLdJson() {
	return devicesLdJson;
	}

	/**
	*
	* @param devicesLdJson
	* The application/ld+json
	*/
	public void setDevicesLdJson(DevicesLdJson devicesLdJson) {
	this.devicesLdJson = devicesLdJson;
	}

}
