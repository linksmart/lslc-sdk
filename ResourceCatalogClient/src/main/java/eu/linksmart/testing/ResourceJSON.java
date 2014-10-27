package eu.linksmart.testing;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos on 23.10.14.
 */
/**
 * LSLC Resource
 * <p>
 * Resource schema for Linksmart LocalConnect
 *
 */
@Generated("org.jsonschema2pojo")

public class ResourceJSON {




        @Expose
        private String id;
        @Expose
        private String type = "Resource";
        @Expose
        private String name;
        @Expose
        private List<String> meta = new ArrayList<String>();
        @Expose
        private String device;
        @Expose
        private Object representation;
        @Expose
        private List<Object> protocols = new ArrayList<Object>();

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

        public List<String> getMeta() {
            return meta;
        }

        public void setMeta(List<String> meta) {
            this.meta = meta;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public Object getRepresentation() {
            return representation;
        }

        public void setRepresentation(Object representation) {
            this.representation = representation;
        }

        public List<Object> getProtocols() {
            return protocols;
        }

        public void setProtocols(List<Object> protocols) {
            this.protocols = protocols;
        }

}
