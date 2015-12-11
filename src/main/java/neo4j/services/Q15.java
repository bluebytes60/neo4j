package neo4j.services;

import com.google.gson.Gson;
import neo4j.domain.topicEntry;
import neo4j.json.Graph;
import neo4j.json.Node;
import neo4j.json.Relationship;
import util.MapUtil;
import util.Rest;

import java.util.*;

/**
 * Created by chenrangong on 12/9/15.
 */
public class Q15 {
    public Map<String, Object> getKeywords(int startYear, int endYear) {
        String query = String.format("MATCH (n:Paper) WHERE (toInt(n.year) <= %d AND toInt (n.year) >= %d) RETURN n", endYear, startYear);
        //Map<String> set = new HashSet<>();
        // words list and return list
        Graph g = Rest.query(query);
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        Set<String> ids = new HashSet<>();
        // process the titles to get keywords list
        for (Node node : g.getNodes()){

            //String title = node.getProperties().getTitle();
            if (node.getProperties().getTitle().toLowerCase().contains("tree") || node.getProperties().getTitle().toLowerCase().contains("data") || node.getProperties().getTitle().toLowerCase().contains("language") || node.getProperties().getTitle().toLowerCase().contains("programming")) {
                if(!ids.contains(node.getId())) {
                    nodes.add(MapUtil.map6("id", node.getId(), "label", node.getProperties().getTitle(), "cluster", "1", "value", 2, "group", "structure", "color", "red"));
                    ids.add(node.getId());
                }
            }else if (node.getProperties().getTitle().toLowerCase().contains("system") || node.getProperties().getTitle().toLowerCase().contains("operating")){
                if(!ids.contains(node.getId())) {
                    nodes.add(MapUtil.map6("id", node.getId(), "label", node.getProperties().getTitle(), "cluster", "2", "value", 2, "group", "system", "color", "blue"));
                    ids.add(node.getId());
                }
            }else if (node.getProperties().getTitle().toLowerCase().contains("Networks") || node.getProperties().getTitle().toLowerCase().contains("Process")) {
                if (!ids.contains(node.getId())) {
                    nodes.add(MapUtil.map6("id", node.getId(), "label", node.getProperties().getTitle(), "cluster", "3", "value", 2, "group", "network", "color", "pink"));
                    ids.add(node.getId());
                }
            }else {
                if (!ids.contains(node.getId())) {
                    nodes.add(MapUtil.map6("id", node.getId(), "label", node.getProperties().getTitle(), "cluster", "4", "value", 1, "group", "others", "color", "yellow"));
                    ids.add(node.getId());
                }
            }

        }
        for (Relationship relationship : g.getRelationships()) {
            rels.add(MapUtil.map3("from", relationship.getStartNode(), "to", relationship.getEndNode(), "title", "PUBLISH"));
        }

        return MapUtil.map("nodes", nodes, "edges", rels);
    }
}
