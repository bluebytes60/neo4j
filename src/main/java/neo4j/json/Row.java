
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

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "name",
    "ee",
    "volume",
    "number",
    "journal",
    "pages",
    "title",
    "fullContext"
})

public class Row {

    @JsonProperty("name")
    private String name;
    @JsonProperty("ee")
    private String ee;
    @JsonProperty("volume")
    private String volume;
    @JsonProperty("number")
    private String number;
    @JsonProperty("journal")
    private String journal;
    @JsonProperty("pages")
    private String pages;
    @JsonProperty("title")
    private String title;
    @JsonProperty("fullContext")
    private String fullContext;
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
     *     The ee
     */
    @JsonProperty("ee")
    public String getEe() {
        return ee;
    }

    /**
     * 
     * @param ee
     *     The ee
     */
    @JsonProperty("ee")
    public void setEe(String ee) {
        this.ee = ee;
    }

    /**
     * 
     * @return
     *     The volume
     */
    @JsonProperty("volume")
    public String getVolume() {
        return volume;
    }

    /**
     * 
     * @param volume
     *     The volume
     */
    @JsonProperty("volume")
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * 
     * @return
     *     The number
     */
    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    /**
     * 
     * @param number
     *     The number
     */
    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 
     * @return
     *     The journal
     */
    @JsonProperty("journal")
    public String getJournal() {
        return journal;
    }

    /**
     * 
     * @param journal
     *     The journal
     */
    @JsonProperty("journal")
    public void setJournal(String journal) {
        this.journal = journal;
    }

    /**
     * 
     * @return
     *     The pages
     */
    @JsonProperty("pages")
    public String getPages() {
        return pages;
    }

    /**
     * 
     * @param pages
     *     The pages
     */
    @JsonProperty("pages")
    public void setPages(String pages) {
        this.pages = pages;
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
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The fullContext
     */
    @JsonProperty("fullContext")
    public String getFullContext() {
        return fullContext;
    }

    /**
     * 
     * @param fullContext
     *     The fullContext
     */
    @JsonProperty("fullContext")
    public void setFullContext(String fullContext) {
        this.fullContext = fullContext;
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
