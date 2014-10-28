package eu.linksmart.local.sdk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos on 24.10.14.
 */
public class LSLCResource {

    public String id;
    public final String type = "Resource";
    public String name;
    public String device;
    public LSLCRepresentation representation;
    public List<LSLCProtocol> protocols;
    public LSLCResource(){
        protocols = new ArrayList<LSLCProtocol>();
    }
}
