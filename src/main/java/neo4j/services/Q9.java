package neo4j.services;

import neo4j.repositories.PaperRepository;
import util.LRUCache;
import util.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tongtongbao on 12/9/15.
 */
public class Q9 {
    LRUCache<String, List<Map<String, Object>>> cache = new LRUCache<>(100);

    List<Map<String, Object>> getTreeData(String journal, int limit, PaperRepository paperRepository) {
        if (cache.containsKey(journal + "_" + limit)) return cache.get(journal + "_" + limit);
        // data from neo4j
        List<Map<String, Object>> listData = paperRepository.q9(journal, limit);
        // return value
        List<Map<String, Object>> jsonData = new ArrayList<>();
        // 2nd level
        List<Map<String, Object>> childrenVol = new ArrayList<>();
        //System.out.println("listsize: " + listData.size());
        for (int i = 0; i < listData.size(); i++) {
            //System.out.println("index: " + i);
            String vol = "";
            List<Map<String, Object>> childrenArticle = new ArrayList<>();
            for (Map.Entry<String, Object> entry : listData.get(i).entrySet()) {
                String key = entry.getKey();
                //System.out.println("key: " + key);
                if (key.equals("vol")) {
                    vol = (String) entry.getValue();
                    //System.out.println("vol: " + vol);
                } else {
                    List<String> articleNames = (ArrayList<String>) entry.getValue();
                    for (int j = 0; j < articleNames.size(); j++) {
                        childrenArticle.add(MapUtil.map1("name", articleNames.get(j)));
                    }
                }
            }
            String volume = "volume " + vol;
            childrenVol.add(MapUtil.map("name", volume, "children", childrenArticle));
        }
        jsonData.add(MapUtil.map("name", journal, "children", childrenVol));
        cache.put(journal + "_" + limit, jsonData);
        return jsonData;
    }
}
