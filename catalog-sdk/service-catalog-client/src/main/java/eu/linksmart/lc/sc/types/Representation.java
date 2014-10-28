package eu.linksmart.lc.sc.types;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Representation {
	
	@SerializedName("text/plain")
	@Expose
	private TextPlain textPlain;
	
	@SerializedName("application/json")
	@Expose
	private ApplicationJson applicationJson;

	public TextPlain getTextPlain() {
		return textPlain;
	}

	public void setTextPlain(TextPlain textPlain) {
		this.textPlain = textPlain;
	}
	
	public ApplicationJson getApplicationJson() {
		return applicationJson;
	}

	public void setApplicationJson(ApplicationJson applicationJson) {
		this.applicationJson = applicationJson;
	}

}
