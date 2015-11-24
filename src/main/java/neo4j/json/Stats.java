
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
    "nodes_deleted",
    "relationship_deleted",
    "nodes_created",
    "labels_added",
    "relationships_created",
    "indexes_added",
    "properties_set",
    "contains_updates",
    "indexes_removed",
    "constraints_added",
    "labels_removed",
    "constraints_removed"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stats {

    @JsonProperty("nodes_deleted")
    private Integer nodesDeleted;
    @JsonProperty("relationship_deleted")
    private Integer relationshipDeleted;
    @JsonProperty("nodes_created")
    private Integer nodesCreated;
    @JsonProperty("labels_added")
    private Integer labelsAdded;
    @JsonProperty("relationships_created")
    private Integer relationshipsCreated;
    @JsonProperty("indexes_added")
    private Integer indexesAdded;
    @JsonProperty("properties_set")
    private Integer propertiesSet;
    @JsonProperty("contains_updates")
    private Boolean containsUpdates;
    @JsonProperty("indexes_removed")
    private Integer indexesRemoved;
    @JsonProperty("constraints_added")
    private Integer constraintsAdded;
    @JsonProperty("labels_removed")
    private Integer labelsRemoved;
    @JsonProperty("constraints_removed")
    private Integer constraintsRemoved;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The nodesDeleted
     */
    @JsonProperty("nodes_deleted")
    public Integer getNodesDeleted() {
        return nodesDeleted;
    }

    /**
     * 
     * @param nodesDeleted
     *     The nodes_deleted
     */
    @JsonProperty("nodes_deleted")
    public void setNodesDeleted(Integer nodesDeleted) {
        this.nodesDeleted = nodesDeleted;
    }

    /**
     * 
     * @return
     *     The relationshipDeleted
     */
    @JsonProperty("relationship_deleted")
    public Integer getRelationshipDeleted() {
        return relationshipDeleted;
    }

    /**
     * 
     * @param relationshipDeleted
     *     The relationship_deleted
     */
    @JsonProperty("relationship_deleted")
    public void setRelationshipDeleted(Integer relationshipDeleted) {
        this.relationshipDeleted = relationshipDeleted;
    }

    /**
     * 
     * @return
     *     The nodesCreated
     */
    @JsonProperty("nodes_created")
    public Integer getNodesCreated() {
        return nodesCreated;
    }

    /**
     * 
     * @param nodesCreated
     *     The nodes_created
     */
    @JsonProperty("nodes_created")
    public void setNodesCreated(Integer nodesCreated) {
        this.nodesCreated = nodesCreated;
    }

    /**
     * 
     * @return
     *     The labelsAdded
     */
    @JsonProperty("labels_added")
    public Integer getLabelsAdded() {
        return labelsAdded;
    }

    /**
     * 
     * @param labelsAdded
     *     The labels_added
     */
    @JsonProperty("labels_added")
    public void setLabelsAdded(Integer labelsAdded) {
        this.labelsAdded = labelsAdded;
    }

    /**
     * 
     * @return
     *     The relationshipsCreated
     */
    @JsonProperty("relationships_created")
    public Integer getRelationshipsCreated() {
        return relationshipsCreated;
    }

    /**
     * 
     * @param relationshipsCreated
     *     The relationships_created
     */
    @JsonProperty("relationships_created")
    public void setRelationshipsCreated(Integer relationshipsCreated) {
        this.relationshipsCreated = relationshipsCreated;
    }

    /**
     * 
     * @return
     *     The indexesAdded
     */
    @JsonProperty("indexes_added")
    public Integer getIndexesAdded() {
        return indexesAdded;
    }

    /**
     * 
     * @param indexesAdded
     *     The indexes_added
     */
    @JsonProperty("indexes_added")
    public void setIndexesAdded(Integer indexesAdded) {
        this.indexesAdded = indexesAdded;
    }

    /**
     * 
     * @return
     *     The propertiesSet
     */
    @JsonProperty("properties_set")
    public Integer getPropertiesSet() {
        return propertiesSet;
    }

    /**
     * 
     * @param propertiesSet
     *     The properties_set
     */
    @JsonProperty("properties_set")
    public void setPropertiesSet(Integer propertiesSet) {
        this.propertiesSet = propertiesSet;
    }

    /**
     * 
     * @return
     *     The containsUpdates
     */
    @JsonProperty("contains_updates")
    public Boolean getContainsUpdates() {
        return containsUpdates;
    }

    /**
     * 
     * @param containsUpdates
     *     The contains_updates
     */
    @JsonProperty("contains_updates")
    public void setContainsUpdates(Boolean containsUpdates) {
        this.containsUpdates = containsUpdates;
    }

    /**
     * 
     * @return
     *     The indexesRemoved
     */
    @JsonProperty("indexes_removed")
    public Integer getIndexesRemoved() {
        return indexesRemoved;
    }

    /**
     * 
     * @param indexesRemoved
     *     The indexes_removed
     */
    @JsonProperty("indexes_removed")
    public void setIndexesRemoved(Integer indexesRemoved) {
        this.indexesRemoved = indexesRemoved;
    }

    /**
     * 
     * @return
     *     The constraintsAdded
     */
    @JsonProperty("constraints_added")
    public Integer getConstraintsAdded() {
        return constraintsAdded;
    }

    /**
     * 
     * @param constraintsAdded
     *     The constraints_added
     */
    @JsonProperty("constraints_added")
    public void setConstraintsAdded(Integer constraintsAdded) {
        this.constraintsAdded = constraintsAdded;
    }

    /**
     * 
     * @return
     *     The labelsRemoved
     */
    @JsonProperty("labels_removed")
    public Integer getLabelsRemoved() {
        return labelsRemoved;
    }

    /**
     * 
     * @param labelsRemoved
     *     The labels_removed
     */
    @JsonProperty("labels_removed")
    public void setLabelsRemoved(Integer labelsRemoved) {
        this.labelsRemoved = labelsRemoved;
    }

    /**
     * 
     * @return
     *     The constraintsRemoved
     */
    @JsonProperty("constraints_removed")
    public Integer getConstraintsRemoved() {
        return constraintsRemoved;
    }

    /**
     * 
     * @param constraintsRemoved
     *     The constraints_removed
     */
    @JsonProperty("constraints_removed")
    public void setConstraintsRemoved(Integer constraintsRemoved) {
        this.constraintsRemoved = constraintsRemoved;
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
