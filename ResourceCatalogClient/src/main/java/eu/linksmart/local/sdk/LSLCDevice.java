package eu.linksmart.local.sdk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos on 24.10.14.
 */
public class LSLCDevice {

    public String id;
    public final String type = "Device";
    public String name;
    public String description;
    public int ttl;
    public List<LSLCResource> resources;

    public LSLCDevice(){
        resources = new ArrayList<LSLCResource>();
    }

}
