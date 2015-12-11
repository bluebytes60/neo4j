package neo4j.services;

import neo4j.json.Graph;
import neo4j.json.Node;
import neo4j.json.Relationship;
import org.apache.lucene.queryparser.classic.ParseException;
import search.SimpleLucene;
import util.LRUCache;
import util.MapUtil;
import util.Rest;

import java.io.IOException;
import java.util.*;

/**
 * Created by bluebyte60 on 12/1/15.
 */
public class Q7 {


    SimpleLucene simpleLucene;
    LRUCache<String, Map<String, Object>> cache = new LRUCache(100);

    public Q7(SimpleLucene simpleLucene) {
        this.simpleLucene = simpleLucene;
    }

    public List<String> topK(String keyword, int K) {
        List<String> result = new ArrayList<>();
        if (K == 0 || keyword.trim().equals("")) return result;
        try {
            result = simpleLucene.search(keyword, "title");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.subList(0, K > result.size() ? result.size() : K);
    }

    public Map<String, Object> parse(String keyword, int K) {
        if (cache.containsKey(keyword + "_" + K)) return cache.get(keyword + "_" + K);
        String query = "MATCH (a:Author)-[Publish]-(p:Paper {title: \\\"%s\\\"}) RETURN *";
        List<String> titles = topK(keyword, K);
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        //prevent duplicate data
        Set<String> ids = new HashSet<>();
        //first step find top k paper and their co-author
        for (String title : titles) {
            Graph g = Rest.query(String.format(query, title));
            System.out.println(String.format(query, title));
            for (Node node : g.getNodes()) {
                if (node.getLabels().get(0).equals("Paper")) {
                    if (!ids.contains(node.getId())) {
                        nodes.add(MapUtil.map6("id", node.getId(), "label", node.getProperties().getTitle(), "cluster", "1", "value", 2, "group", "paper", "color", "red"));
                        ids.add(node.getId());
                    }
                } else {
                    if (!ids.contains(node.getId())) {
                        nodes.add(MapUtil.map5("id", node.getId(), "label", node.getProperties().getName(), "cluster", "2", "value", 1, "group", "author"));
                        ids.add(node.getId());
                    }
                }
            }

            for (Relationship relationship : g.getRelationships()) {
                rels.add(MapUtil.map3("from", relationship.getStartNode(), "to", relationship.getEndNode(), "title", "PUBLISH"));
            }
        }
        java.util.Map<String, Object> map = MapUtil.map("nodes", nodes, "edges", rels);
        cache.put(keyword + "_" + K, map);
        return map;
    }


}
