package neo4j.services;

import neo4j.json.Graph;
import neo4j.json.Node;
import neo4j.json.Relationship;
import org.apache.lucene.queryparser.classic.ParseException;
import search.SimpleLucene;
import util.MapUtil;
import util.Rest;

import java.io.IOException;
import java.util.*;

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
        return result.subList(0, K > result.size() ? result.size() : K);
    }

    public Map<String, Object> parse(String keyword, int K) {
        String query = "MATCH (a:Author)-[Publish]-(p:Paper {title: \\\"%s\\\"}) RETURN *";
        List<String> titles = topK(keyword, K);
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<java.util.Map<String, Object>> rels = new ArrayList<>();
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

        //second step, find shortest path between these papers
        //MATCH p = shortestPath((a1:Paper { title:"Fair sticker languages." })-[*1..15]-(a2:Paper { title:"On the Generative Power of Regular Pattern Grammars." })) RETURN p
        for (int i = 0; i < titles.size(); i++) {
            for (int j = i + 1; j < titles.size(); j++) {
                query = "MATCH p = shortestPath((a1:Paper { title:\\\"%s\\\" })-[*1..5]-(a2:Paper { title:\\\"%s\\\" })) RETURN p";
                System.out.println(String.format(query, titles.get(i), titles.get(j)));
                Graph g = Rest.query(String.format(query, titles.get(i), titles.get(j)));
                for (Node node : g.getNodes()) {
                    if (node.getLabels().get(0).equals("Paper")) {
                        if (!ids.contains(node.getId())) {
                            nodes.add(MapUtil.map5("id", node.getId(), "label", node.getProperties().getTitle(), "cluster", "1", "value", 2, "group", "paper"));
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
        }
        return MapUtil.map("nodes", nodes, "edges", rels);
    }


}
