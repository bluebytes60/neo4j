package neo4j.services;

import neo4j.json.Graph;
import neo4j.json.Node;
import neo4j.json.Relationship;
import util.LRUCache;
import util.MapUtil;
import util.Rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bluebyte60 on 12/1/15.
 */
public class Q5 {
    LRUCache<String, Map<String, Object>> cache = new LRUCache(100);

    public Map<String, Object> parse(String centralAuthor) {
        if (cache.containsKey(centralAuthor)) return cache.get(centralAuthor);
        String query = String.format("MATCH p = shortestPath((bacon:Author {name:\\\"%s\\\"})-[*1..2]-(another:Author)) RETURN p ", centralAuthor);
        System.out.println(query);
        java.util.Map<String, Object> map = toMap(centralAuthor, query);
        cache.put(centralAuthor, map);
        return map;
    }

    private java.util.Map<String, Object> toMap(String centralAuthor, String query) {
        Graph g = Rest.query(query);
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<java.util.Map<String, Object>> rels = new ArrayList<>();

        for (Node node : g.getNodes()) {
            if (node.getLabels().get(0).equals("Paper"))
                nodes.add(MapUtil.map5("id", node.getId(), "label", node.getProperties().getTitle(), "cluster", "1", "value", 2, "group", "paper"));
            else if (node.getProperties().getName().equals(centralAuthor))
                nodes.add(MapUtil.map6("id", node.getId(), "label", node.getProperties().getName(), "cluster", "2", "value", 1, "group", "author", "color", "red"));
            else
                nodes.add(MapUtil.map5("id", node.getId(), "label", node.getProperties().getName(), "cluster", "2", "value", 1, "group", "author"));
        }

        for (Relationship relationship : g.getRelationships()) {
            rels.add(MapUtil.map3("from", relationship.getStartNode(), "to", relationship.getEndNode(), "title", "PUBLISH"));
        }

        return MapUtil.map("nodes", nodes, "edges", rels);
    }
}
