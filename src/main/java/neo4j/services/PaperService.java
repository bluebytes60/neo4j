package neo4j.services;

import neo4j.json.Graph;
import neo4j.json.Node;
import neo4j.json.Relationship;
import neo4j.repositories.PaperRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.MapUtil;
import util.Rest;

import java.util.*;

@Service
@Transactional
public class PaperService {

    @Autowired
    PaperRepository paperRepository;
    Q5 q5 = new Q5();
    Q22 q22 = new Q22();

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
        String query = String.format("MATCH p = shortestPath((bacon:Author {name:\\\"%s\\\"})-[*1..2]-(another:Author)) RETURN p ", name);
        return q5.parse(name, query);
    }

    public Map<String, Object> q22(String name) {
        String query = String.format("MATCH p = shortestPath((bacon:Author {name:\\\"%s\\\"})-[*1..5]-(another:Author)) RETURN p limit 1", name);
        return q22.parse(name, query);
    }
}

