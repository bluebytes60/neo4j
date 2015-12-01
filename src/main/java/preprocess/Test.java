package preprocess;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.util.Map;

/**
 * Created by bluebyte60 on 11/16/15.
 */

public class Test {
    final static String dbPath = "/Users/bluebyte60/Documents/Neo4j/dblp";

    public static void main(String[] args) {
        GraphDatabaseService graphDb;

        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
        registerShutdownHook(graphDb);

        truncteDB(graphDb);

        try (Transaction tx = graphDb.beginTx()) {

            Node myNode = graphDb.createNode();
            myNode.setProperty("name", "my node");
            tx.success();
        }
        String rows = "";
        try (Transaction ignored = graphDb.beginTx();
             Result result = graphDb.execute("match (n {name: 'my node'}) return n, n.name")) {
            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                for (Map.Entry<String, Object> column : row.entrySet()) {
                    rows += column.getKey() + ": " + column.getValue() + "; ";
                }
                rows += "\n";
            }
        }

        System.out.println(rows);
    }

    private static void truncteDB(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            graphDb.execute( "match (n)  delete n" );
            tx.success();
        }
    }

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }
}
