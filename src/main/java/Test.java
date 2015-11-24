import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by bluebyte60 on 11/24/15.
 */
public class Test {
    public static void main(String[] args) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:7474/db/data/transaction/commit")
                .header("Authorization", "Basic bmVvNGo6MTg2NTY=")
                .header("Content-Type", "application/json;charset=utf-8")
                .header("Accept", "application/json, text/plain, */*")
                .header("X-stream", "true")
                .body("{\n" +
                        "  \"statements\": [\n" +
                        "    {\n" +
                        "      \"statement\": \"MATCH p = shortestPath((bacon:Author {name:\\\"Victor Khomenko\\\"})-[*1..6]-(another:Author)) RETURN p limit 1\",\n" +
                        "      \"resultDataContents\": [\n" +
                        "        \"row\",\n" +
                        "        \"graph\"\n" +
                        "      ],\n" +
                        "      \"includeStats\": true\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}").asJson();
        System.out.println(response.getStatus());
        System.out.println(response.getStatusText());
        System.out.println(response.getBody());
    }
}
