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
public class Q22 {
    LRUCache<String, Map<String, Object>> cache = new LRUCache<>(100);

    public Map<String, Object> parse(String name1, String name2) {
        if (cache.containsKey(name1 + "_" + name2)) return cache.get(name1 + "_" + name2);
        String query = String.format("MATCH (p1:Author {name: \\\"%s\\\"}), (p2:Author {name:\\\"%s\\\" }), path = shortestpath((p1)-[:PUBLISH*]-(p2)) RETURN path", name1, name2);
        //String query = String.format("MATCH p = shortestPath((a1:Author { name:\\\"%s\\\" })-[*1..6]-(a2:Author { name:\\\"%s\\\" })) RETURN p", name1, name2);
        System.out.println(query);
        java.util.Map<String, Object> map = toMap(name1, name2, query);
        cache.put(name1 + "_" + name2, map);
        return map;
    }

    public java.util.Map<String, Object> toMap(String name1, String name2, String query) {
        Graph g = Rest.query(query);
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<java.util.Map<String, Object>> rels = new ArrayList<>();

        for (Node node : g.getNodes()) {
            if (node.getLabels().get(0).equals("Paper"))
                nodes.add(MapUtil.map5("id", node.getId(), "label", node.getProperties().getTitle(), "cluster", "1", "value", 2, "group", "paper"));
            else if (node.getProperties().getName().equals(name1) || node.getProperties().getName().equals(name2))
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
