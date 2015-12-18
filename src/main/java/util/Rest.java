package util;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import neo4j.json.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bluebyte60 on 11/24/15.
 */
public class Rest {
    //"http://localhost:7474", "bmVvNGo6MTg2NTY="(actually 18656 in neo4j), will able to retrieve in the code section of the ui console
    final static String password = "bmVvNGo6MTg2NTY";
    final static String url= "http://localhost:7474";

    public static Graph query(String query) {
        JSONData r = new JSONData();
        try {
            HttpResponse<JsonNode> response = Unirest.post(url + "/db/data/transaction/commit")
                    .header("Authorization", "Basic " + password)
                    .header("Content-Type", "application/json;charset=utf-8")
                    .header("Accept", "application/json, text/plain, */*")
                    .header("X-stream", "true")
                    .body("{\n" +
                            "  \"statements\": [\n" +
                            "    {\n" +
                            "      \"statement\": \"" + query + "\"," +
                            "      \"resultDataContents\": [\n" +
                            "        \"row\",\n" +
                            "        \"graph\"\n" +
                            "      ],\n" +
                            "      \"includeStats\": true\n" +
                            "    }\n" +
                            "  ]\n" +
                            "}").asJson();
            ObjectMapper mapper = new ObjectMapper();
            // System.out.println(response.getBody().toString());
            r = mapper.readValue(response.getBody().toString(), JSONData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashSet<Node> nodeSet = new HashSet<>();
        HashSet<Relationship> relationSet = new HashSet<>();
        for (Result result : r.getResults()) {
            for (Datum datum : result.getData()) {
                for(Node node : datum.getGraph().getNodes()) nodeSet.add(node);
                for(Relationship relationship : datum.getGraph().getRelationships()) relationSet.add(relationship);
            }
        }
        Graph g = new Graph();
        g.setNodes(new ArrayList<>(nodeSet));
        g.setRelationships(new ArrayList<>(relationSet));
        return g;
    }

    public static void main(String[] args) {
        // Graph g = rest.query("MATCH p = shortestPath((bacon:Author {name:\\\"Victor Khomenko\\\"})-[*1..6]-(another:Author)) RETURN p limit 1");
        Graph g = Rest.query("MATCH (a)-[:PUBLISH]->(m)<-[:PUBLISH]-(d) where a.name = \\\"Victor Khomenko\\\" return a,m,d");
        List<Node> nodes = g.getNodes();
        for (Node node : nodes) {
            System.out.println(node.getId());
            System.out.println(node.getLabels().get(0));
            if (node.getProperties().getName() == null)
                System.out.println(node.getProperties().getTitle());
            else System.out.println(node.getProperties().getName());
        }

    }

}
