package neo4j.services;

import neo4j.json.Graph;
import neo4j.json.Node;
import neo4j.json.Relationship;
import util.LRUCache;
import util.MapUtil;
import util.Rest;

import java.util.*;

/**
 * Created by bluebyte60 on 12/1/15.
 */
public class Q24 {
    LRUCache<String, Map<String, Object>> cache = new LRUCache<>(100);

    public Map<String, Object> parse(String keyword, int networkSize) {
        if (cache.containsKey(keyword + " " + networkSize)) return cache.get(keyword + " " + networkSize);
        Map<String, Object> graph = getGraph(keyword, networkSize, new HashSet<String>());
        cache.put(keyword + " " + networkSize, graph);
        return graph;
    }

    private Map<String, Object> getGraph(String keyword, int limit, Set<String> ids) {

        String[] keywords = keyword.split(" ");
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        for (String word : keywords) {
            nodes.add(MapUtil.map6("id", word.hashCode(), "label", word, "cluster", "3", "value", 2, "group", "paper", "color", "red"));
        }
        Set<String> fromTo = new HashSet<>();
        for (int i = 0; i < keywords.length; i++) {
            for (int j = i + 1; j < keywords.length; j++) {
                String keywordA = keywords[i];
                String keywordB = keywords[j];
                String query = "MATCH (p1:Paper), (p2:Paper), path = shortestpath((p1)-[:PUBLISH*]-(p2)) Where p1.title =~ '.*%s.*'and p2.title =~ '.*%s.*' " +
                        "RETURN path limit %d";
                System.out.println(String.format(query, keywordA, keywordB, limit));
                Graph g = Rest.query(String.format(query, keywordA, keywordB, limit));
                for (Node node : g.getNodes()) {
                    if (node.getLabels().get(0).equals("Paper")) {
                        if (!ids.contains(node.getId())) {
                            nodes.add(MapUtil.map5("id", node.getId(), "label", node.getProperties().getTitle(), "cluster", "1", "value", 2, "group", "paper"));
                            ids.add(node.getId());
                            for (String w : keywords) {
                                if (node.getProperties().getTitle().contains(w)) {
                                    rels.add(MapUtil.map3("from", node.getId(), "to", w.hashCode(), "title", "PUBLISH"));
                                }
                            }
                        }
                    } else {
                        if (!ids.contains(node.getId())) {
                            nodes.add(MapUtil.map5("id", node.getId(), "label", node.getProperties().getName(), "cluster", "2", "value", 1, "group", "author"));
                            ids.add(node.getId());
                        }
                    }
                    for (Relationship relationship : g.getRelationships()) {
                        if (!fromTo.contains(relationship.getStartNode() + "_" + relationship.getEndNode())) {
                            rels.add(MapUtil.map3("from", relationship.getStartNode(), "to", relationship.getEndNode(), "title", "PUBLISH"));
                            fromTo.add(relationship.getStartNode() + "_" + relationship.getEndNode());
                        }
                    }
                }
            }
        }
        return MapUtil.map("nodes", nodes, "edges", rels);
    }

}

