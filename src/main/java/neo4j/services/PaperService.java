package neo4j.services;

import neo4j.json.Graph;
import neo4j.json.Node;
import neo4j.json.Relationship;
import neo4j.repositories.PaperRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import search.SimpleLucene;
import util.MapUtil;
import util.Rest;

import java.util.*;

@Service
@Transactional
public class PaperService {

    @Autowired
    PaperRepository paperRepository;
    Q5 q5;
    Q6 q6;
    Q7 q7;
    Q12 q12;
    Q14 q14;
    Q13 q13;
    Q22 q22;
    Q17 q17;

    SimpleLucene simpleLucene;

    public PaperService() {
        simpleLucene = new SimpleLucene();
        try {
            simpleLucene.load();
            simpleLucene.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        q5 = new Q5();
        q7 = new Q7(simpleLucene);
        q6 = new Q6();
        q12 = new Q12(simpleLucene);
        q13 = new Q13(simpleLucene);
        q14 = new Q14(simpleLucene);
        q22 = new Q22();
        q17 = new Q17();
    }

    public Map<String, Object> graphAlc(int limit) {
        Iterator<Map<String, Object>> result = paperRepository.graph(limit).iterator();
        return toAlcFormat(result);
    }

    private Map<String, Object> toAlcFormat(Iterator<Map<String, Object>> result) {
        List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> rels = new ArrayList<Map<String, Object>>();
        int i = 1;
        int target = 0;
        while (result.hasNext()) {
            Map<String, Object> row = result.next();
            nodes.add(MapUtil.map6("id", i, "title", row.get("paper"), "label", row.get("paper"), "cluster", "1", "value", 2, "group", "paper"));
            target = i++;
            for (Object name : (Collection) row.get("cast")) {
                Map<String, Object> author = MapUtil.map5("title", name, "label", name, "cluster", "2", "value", 1, "group", "author");
                int source = 0;
                for (int j = 0; j < nodes.size(); j++) {
                    if (nodes.get(j).get("title").equals(name)) {
                        source = (int) nodes.get(j).get("id");
                        break;
                    }
                }
                if (source == 0) {
                    author.put("id", i);
                    source = i;
                    i++;
                    nodes.add(author);
                }

                rels.add(MapUtil.map3("from", source, "to", target, "title", "PUBLISH"));
            }
        }
        return MapUtil.map("nodes", nodes, "edges", rels);
    }

    public Map<String, Object> q5(String name) {
        return q5.parse(name);
    }
    public Map<String, Object> q6(String name, int hop) {
        return q6.parse(name, hop);
    }

    public List<String> q7Part1(String keyword, int K) {
        return q7.topK(keyword, K);
    }

    public Map<String, Object> q7Part2(String keyword, int K) {
        return q7.parse(keyword, K);
    }

    public List<Map<String, Object>> q9(String journal, int limit) { return paperRepository.q9(journal, limit);}

    public List<String> q12Part1(String keyword, int K) {
        return q12.topK(keyword, K);
    }

    public Map<String, Object> q12Part2(String keyword, int K) {
        return q12.parse(keyword, K);
    }

    public Map<Integer, String> q13(String keyword) {
        return q13.getCollaborators(keyword);
    }

    public Map<String, Integer> q14(String keyword) {
        return q14.getExpert(keyword);
    }

    public String q17(int startYear, int endYear) {
        return q17.getKeywords(startYear, endYear);
    }

    public Map<String, Object> q22(String name1, String name2) {
        return q22.parse(name1, name2);
    }


}

