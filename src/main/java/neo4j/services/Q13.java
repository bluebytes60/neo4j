package neo4j.services;

/**
 * Created by tongtongbao on 12/8/15.
 */

import org.apache.lucene.queryparser.classic.ParseException;
import search.SimpleLucene;
import util.LRUCache;

import java.io.IOException;
import java.util.*;

public class Q13 {

    SimpleLucene simpleLucene;
    LRUCache<String, Map<Integer, String>> cache = new LRUCache(100);

    public Q13(SimpleLucene simpleLucene) {
        this.simpleLucene = simpleLucene;
    }

    public Map<Integer, String> getCollaborators(String keyword) {
        if (cache.containsKey(keyword)) return cache.get(keyword);
        Map<String, Integer> map = new HashMap<>();
        TreeMap<Integer, List<String>> reversedMap = new TreeMap<>();
        Map<Integer, String> collabMap = new TreeMap<>();

        if (keyword.trim().equals("")) return collabMap;
        try {
            map = simpleLucene.searchResearcher(keyword, "title");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String author = entry.getKey();
            Integer freq = entry.getValue();

            if (reversedMap.containsKey(freq)) {
                List<String> updateList = reversedMap.get(freq);
                updateList.add(author);
                reversedMap.put(freq, updateList);
            } else {
                List<String> iniList = new ArrayList<>();
                iniList.add(author);
                reversedMap.put(freq, iniList);
            }
        }
        Map<Integer, List<String>> newMap = new TreeMap(Collections.reverseOrder());
        newMap.putAll(reversedMap);

        //System.out.println(newMap);

        int count = 9;
        for (Map.Entry<Integer, List<String>> entry : newMap.entrySet()) {
            if (count < 0) break;
            List<String> authors = entry.getValue();
            for (String str : authors) {
                if (count < 0) break;
                collabMap.put(10 - count, str);
                count--;
            }
        }
        cache.put(keyword, collabMap);
        return collabMap;
    }

}
