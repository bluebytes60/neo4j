package neo4j.services;

import neo4j.json.Graph;
import neo4j.json.Node;
import util.Rest;

import java.util.*;

/**
 * Created by chenrangong on 12/9/15.
 */
public class Q16 {
    public Map<String, Integer> getKeywords(int startYear, int endYear, String channal, String keyword) {
        //System.out.println(channal);
        channal = "'"+channal+"'";
        String query = String.format("MATCH (n:Paper) WHERE (toInt(n.year) <= %d AND toInt (n.year) >= %d AND n.journal = %s) RETURN n", endYear, startYear, channal);

        Set<String> set = new HashSet<>();
        Map<String, Integer> result = new HashMap<>();
        String[] keywords = keyword.split(" ");
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
                result.put(title, 0);
            }
        }
        return result;
    }

}
