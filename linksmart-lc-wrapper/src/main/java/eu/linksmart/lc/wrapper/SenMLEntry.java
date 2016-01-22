package eu.linksmart.lc.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SenMLEntry {

	@SerializedName("n")
	@Expose
    public String name;
	
	@SerializedName("v")
	@Expose
    public String value;
	
	@SerializedName("u")
	@Expose
    public String units;

	@SerializedName("t")
	@Expose
    public long time;
	
	public SenMLEntry(String name, String value, String units, long time) {
        this.name = name;
        this.units = units;
        this.value = value;
        this.time = time;
    }
    
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
    
	public String getValue() {
		return this.value;
	}

	public String getUnits() {
		return this.units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public long getTime() {
		return this.time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
