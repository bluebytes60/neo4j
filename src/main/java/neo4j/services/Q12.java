package neo4j.services;

import neo4j.json.Graph;
import neo4j.json.Node;
import neo4j.json.Relationship;
import org.apache.lucene.queryparser.classic.ParseException;
import search.SimpleLucene;
import util.MapUtil;
import util.Rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bluebyte60 on 12/1/15.
 */
public class Q12 {
    //MATCH (a:Author)-[Publish]-(p:Paper {title: "Describing Semantic Domains with Sprouts."}) RETURN *

    SimpleLucene simpleLucene = new SimpleLucene();

    public Q12() {
        try {
            simpleLucene.load();
            simpleLucene.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return result.subList(0, K);
    }

    public Map<String, Object> parse(String keyword, int K) {
        String query = "MATCH (a:Author)-[Publish]-(p:Paper {title: \\\"%s\\\"}) RETURN *";
        List<String> titles = topK(keyword, K);
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<java.util.Map<String, Object>> rels = new ArrayList<>();
        for (String title : titles) {
            Graph g = Rest.query(String.format(query, title));
            for (Node node : g.getNodes()) {
                if (node.getLabels().get(0).equals("Paper"))
                    nodes.add(MapUtil.map5("id", node.getId(), "label", node.getProperties().getTitle(), "cluster", "1", "value", 2, "group", "paper"));
                else
                    nodes.add(MapUtil.map5("id", node.getId(), "label", node.getProperties().getName(), "cluster", "2", "value", 1, "group", "author"));
            }

            for (Relationship relationship : g.getRelationships()) {
                rels.add(MapUtil.map3("from", relationship.getStartNode(), "to", relationship.getEndNode(), "title", "PUBLISH"));
            }
        }
        return MapUtil.map("nodes", nodes, "edges", rels);
    }


}
