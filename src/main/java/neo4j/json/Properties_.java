
package neo4j.json;

import java.util.HashMap;
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
@JsonPropertyOrder({
    "name"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Properties_ {

    @JsonProperty("name")
    private String name;
    @JsonProperty("title")
    private String title;

    @JsonProperty("journal")
    private String journal;
    @JsonProperty("volumn")
    private String volumn;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    /**
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param name
     *
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @param journal
     * @return
     */
    @JsonProperty("journal")
    public void setJournal(String Journal) { this.journal = journal; }

    /**
     *
     * @return
     *      The journal
     */
    @JsonProperty("journal")
    public String getJournal() {
        return journal;
    }

    /**
     *
     * @param volumn
     * @return
     */
    @JsonProperty("journal")
    public void setVolumn(String Volumn) { this.volumn = journal; }

    /**
     *
     * @return
     *      The volumn
     */
    @JsonProperty("volumn")
    public String getVolumn() {
        return journal;
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



















