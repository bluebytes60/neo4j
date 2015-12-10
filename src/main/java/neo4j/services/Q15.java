package neo4j.services;

import com.google.gson.Gson;
import neo4j.domain.topicEntry;
import neo4j.json.Graph;
import neo4j.json.Node;
import util.Rest;

import java.util.*;

/**
 * Created by chenrangong on 12/9/15.
 */
public class Q15 {
    public Map<String, Integer> getKeywords(int startYear, int endYear) {
        String query = String.format("MATCH (n:Paper) WHERE (toInt(n.year) <= %d AND toInt (n.year) >= %d) RETURN n", endYear, startYear);
        Map<String, Integer> map = new HashMap<>();
        // words list and return list
        Graph g = Rest.query(query);

        // process the titles to get keywords list
        for (Node node : g.getNodes()) {

            String title = node.getProperties().getTitle();
            //String color = node.getProperties().;
            //String journal = node.getProperties().getJournal();
            if(!map.containsKey(title)){
                map.put(title, 0);
            }
        }

        return map;
    }
}
