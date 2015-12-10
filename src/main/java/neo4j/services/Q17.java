package neo4j.services;

import com.google.gson.Gson;
import neo4j.domain.topicEntry;
import neo4j.json.Graph;
import neo4j.json.Node;
import util.Rest;

import java.util.*;

/*
* Things to do:
* 1) ctrl + R problem
* */

/**
 * Created by tongtongbao on 12/2/15.
 */
public class Q17 {

    public String getKeywords(int startYear, int endYear) {
        String query = String.format("MATCH (n:Paper) WHERE (toInt(n.year) <= %d AND toInt (n.year) >= %d) RETURN n", endYear, startYear);

        // words list and return list
        String wordsAndWeight;
        HashMap<String, Integer> keywordMap = new HashMap<>();
        Graph g = Rest.query(query);

        // process the titles to get keywords list
        for (Node node : g.getNodes()) {
            String title = node.getProperties().getTitle();
            String[] words = title.split(" ");
            for (String word : words) {
                // remove possible punctuations
                word=word.replaceAll(",", "").replaceAll("\\.", "").replaceAll("\\?", "").replaceAll("\\!", "");
                word = word.trim();
                if (word.length() == 1) {
                    continue;
                }
                // update the hash map
                if (keywordMap.containsKey(word)) {
                    keywordMap.put(word, keywordMap.get(word) + 1);
                } else {
                    keywordMap.put(word, 1);
                }
            }
        }
        //System.out.println(keywordMap);
        // reverse key with value
        TreeMap<Integer, List<String>> reverseMap = new TreeMap<>();

        for (Map.Entry<String, Integer> entry : keywordMap.entrySet()) {
            String word = entry.getKey();
            Integer freq = entry.getValue();

            if (reverseMap.containsKey(freq)) {
                List<String> updateList = reverseMap.get(freq);
                updateList.add(word);
                reverseMap.put(freq, updateList);
            } else {
                List<String> iniList = new ArrayList<>();
                iniList.add(word);
                reverseMap.put(freq, iniList);
            }
        }
        //System.out.println(reverseMap);

        List<topicEntry> topicEntries = new ArrayList<>();
        int count = 30;
        for (Map.Entry<Integer, List<String>> entry : reverseMap.entrySet()) {
            if (count < 0) break;

            List<String> values = entry.getValue();

            for (String str : values) {
                if (count > 27) {
                    topicEntries.add(new topicEntry(str, 10));
                    count--;
                    continue;
                }
                if (count > 24) {
                    topicEntries.add(new topicEntry(str, 9));
                    count--;
                    continue;
                }
                if (count > 21) {
                    topicEntries.add(new topicEntry(str, 8));
                    count--;
                    continue;
                }
                if (count > 18) {
                    topicEntries.add(new topicEntry(str, 6));
                    count--;
                    continue;
                }
                if (count > 15) {
                    topicEntries.add(new topicEntry(str, 5));
                    count--;
                    continue;
                }
                if (count > 12) {
                    topicEntries.add(new topicEntry(str, 4));
                    count--;
                    continue;
                }
                if (count > 9) {
                    topicEntries.add(new topicEntry(str, 3));
                    count--;
                    continue;
                }
                if (count > 3) {
                    topicEntries.add(new topicEntry(str, 2));
                    count--;
                    continue;
                }
                if (count >= 0) {
                    topicEntries.add(new topicEntry(str, 1));
                    count--;
                    continue;
                }
                if (count < 0) {
                    break;
                }
            }
        }

        Gson gson = new Gson();
        wordsAndWeight = gson.toJson(topicEntries);
        return wordsAndWeight;
    }
}
