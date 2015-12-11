package neo4j.services;

import neo4j.json.Graph;
import neo4j.json.Node;
import neo4j.json.Relationship;
import util.MapUtil;
import util.Rest;

import java.util.*;

/**
 * Created by chenrangong on 12/9/15.
 */
public class Q16 {
    public Map<String, Object> getKeywords(int startYear, int endYear, String channal, String keyword) {
        //System.out.println(channal);
        channal = "'"+channal+"'";
        String query = String.format("MATCH (n:Paper) WHERE (toInt(n.year) <= %d AND toInt (n.year) >= %d AND n.journal = %s) RETURN n", endYear, startYear, channal);

        Set<String> set = new HashSet<>();
        Set<String> resultTitles = new HashSet<>();
        Map<String, Object> result = new HashMap<>();
        String[] keywords = keyword.split(" ");
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        // words list and return list
        Graph g = Rest.query(query);

        // process the titles to get keywords list
        for (Node node : g.getNodes()) {

            String title = node.getProperties().getTitle();
            //System.out.println(title);
            //String journal = node.getProperties().getJournal();
            if(!set.contains(title)){
                set.add(title);
            }
        }
        for(String title: set){
            String[] titles = title.split(" ");
            List<String> list = Arrays.asList(titles);
            boolean containsAll = true;
            for(String key: keywords){
                if(!list.contains(key)){
                    containsAll = false;
                }
            }
            if(containsAll){
                resultTitles.add(title);
            }
        }
        //System.out.println(resultTitles.size());
        for(String title: resultTitles){
            if (title.toLowerCase().contains("tree") || title.toLowerCase().contains("data") || title.toLowerCase().contains("language") || title.toLowerCase().contains("programming")) {
                nodes.add(MapUtil.map5("label", title, "cluster", "1", "value", 2, "group", "structure", "color", "red"));
            }else if (title.toLowerCase().contains("system") || title.toLowerCase().contains("operating")){
                nodes.add(MapUtil.map5("label", title, "cluster", "2", "value", 2, "group", "system", "color", "blue"));
            }else if (title.toLowerCase().contains("Networks") || title.toLowerCase().contains("Process")) {
                nodes.add(MapUtil.map5("label", title, "cluster", "3", "value", 2, "group", "network", "color", "pink"));
            }else {
                nodes.add(MapUtil.map5("label", title, "cluster", "4", "value", 1, "group", "others", "color", "yellow"));
            }
        }
        for (Relationship relationship : g.getRelationships()) {
            rels.add(MapUtil.map3("from", relationship.getStartNode(), "to", relationship.getEndNode(), "title", "PUBLISH"));
        }
        return MapUtil.map("nodes", nodes, "edges", rels);
    }

}
