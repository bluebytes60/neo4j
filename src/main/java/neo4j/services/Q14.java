package neo4j.services;

import org.apache.lucene.queryparser.classic.ParseException;
import search.SimpleLucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenrangong on 12/2/15.
 */
public class Q14 {

    SimpleLucene simpleLucene;

    public Q14(SimpleLucene simpleLucene) {
        this.simpleLucene=simpleLucene;
    }


    public Map<String, Integer> getExpert(String keyword) {
        Map<String, Integer> map = new HashMap<>();
        if (keyword.trim().equals("")) return map;
        try {
            map = simpleLucene.searchResearcher(keyword, "title");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

}
