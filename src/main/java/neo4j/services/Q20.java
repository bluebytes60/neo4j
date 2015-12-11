package neo4j.services;

import neo4j.json.Graph;
import neo4j.json.Node;
import neo4j.json.Relationship;
import neo4j.repositories.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import util.LRUCache;
import util.MapUtil;
import util.Rest;

import java.util.*;

/**
 * Created by bluebyte60 on 12/1/15.
 */
public class Q20 {
    LRUCache<Integer, Map<String, Object>> cache = new LRUCache<>(100);
    public Map<String, Object> parse(int limit) {
        if (cache.containsKey(limit)) return cache.get(limit);
        String query = String.format("MATCH (firstAuthor:Author)<-[p:PUBLISHWITH]-(coworkers:Author) RETURN *  LIMIT %d", limit);
        System.out.println(query);
        java.util.Map<String, Object> map = toMap(query);
        cache.put(limit, map);
        return map;
    }

    private java.util.Map<String, Object> toMap(String query) {
        Graph g = Rest.query(query);
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<java.util.Map<String, Object>> rels = new ArrayList<>();

        for (Node node : g.getNodes()) {
            nodes.add(MapUtil.map5("id", node.getId(), "label", node.getProperties().getName(), "cluster", "1", "value", 1, "group", "author"));
        }

        for (Relationship relationship : g.getRelationships()) {
            rels.add(MapUtil.map3("from", relationship.getStartNode(), "to", relationship.getEndNode(), "title", "PUBLISHWITH"));
        }

        return MapUtil.map("nodes", nodes, "edges", rels);
    }
}
