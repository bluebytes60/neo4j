
package neo4j.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
//@JsonPropertyOrder({
//    "row",
//    "graph"
//})
@JsonIgnoreProperties({"row"})
public class Datum {

    @JsonProperty("row")
    private List<List<Row>> row = new ArrayList<List<Row>>();
    @JsonProperty("graph")
    private Graph graph;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The row
     */
    @JsonProperty("row")
    public List<List<Row>> getRow() {
        return row;
    }

    /**
     * 
     * @param row
     *     The row
     */
    @JsonProperty("row")
    public void setRow(List<List<Row>> row) {
        this.row = row;
    }

    /**
     * 
     * @return
     *     The graph
     */
    @JsonProperty("graph")
    public Graph getGraph() {
        return graph;
    }

    /**
     * 
     * @param graph
     *     The graph
     */
    @JsonProperty("graph")
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
