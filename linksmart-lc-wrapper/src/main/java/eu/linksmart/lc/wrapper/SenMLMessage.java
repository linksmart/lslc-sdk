package eu.linksmart.lc.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public class SenMLMessage {
	
	@SerializedName("bn")
	@Expose
    public String baseName = "http://fit.fraunhofer.de/satisfactory/";
    
	@SerializedName("bt")
	@Expose
    public long baseTime;
	
	@SerializedName("ver")
	@Expose
    public int version = 1;
    
	@SerializedName("e")
	@Expose
    public ArrayList<SenMLEntry> entries = new ArrayList<>();

    public SenMLMessage() {	
    }
    
    public SenMLMessage(Properties props) {
        long time = System.currentTimeMillis();
        if (props != null) {
        	for (Map.Entry<Object, Object> e : props.entrySet()) {
        		this.entries.add(new SenMLEntry((String) e.getKey(), (String) e.getValue(), "unknown", time));
        	}
        }
    }
    
    public SenMLMessage(String key, String value) {
        long time = System.currentTimeMillis();
        this.entries.add(new SenMLEntry(key, value, "unknown", time));
    }
    
    protected void setEntries(ArrayList<SenMLEntry> entries) {
		this.entries = entries;
	}

	public ArrayList<SenMLEntry> getEntries() {
		return entries;
	}
}
