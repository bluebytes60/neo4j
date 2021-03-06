package preprocess;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.util.*;

/**
 * Created by bluebyte60 on 11/17/15.
 */
public class Preprocess {

    Parser parser;

    GraphDatabaseService graphDb;

    Map<String, Node> authors = new HashMap<>();

    Map<String, List<String>> PublishWith = new HashMap<>();

    Map<String, Node> articles = new HashMap<>();

    Map<String, List<Article>> wrote = new HashMap<>();

    public Preprocess(String xmlPath, String dbPath) {
        parser = new Parser(xmlPath);
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
        registerShutdownHook(graphDb);
        truncteDB(graphDb);
    }

    public void process() throws Exception {
        List<Article> transactions = parser.getAricles();

        //store all authors
        Set<String> authors = new HashSet<>();

        //store who write what
        for (Article article : transactions) {
            //store who lead project
            if (article.authors.size() > 1) {
                String firstAuthor = article.authors.get(0);
                for (int i = 1; i < article.authors.size(); i++) {
                    if (PublishWith.containsKey(firstAuthor)) {
                        PublishWith.get(firstAuthor).add(article.authors.get(i));
                    } else {
                        PublishWith.put(firstAuthor, new ArrayList<>(Arrays.asList(article.authors.get(i))));
                    }
                }
            }
            for (String author : article.authors) {
                authors.add(author);
                //store relation
                if (wrote.containsKey(author)) wrote.get(author).add(article);
                else wrote.put(author, new ArrayList<>(Arrays.asList(article)));
                //create node for article
                this.articles.put(article.title, create(DynamicLabel.label("Paper"), getProperty(article)));
            }
        }

        for (String userName : authors) {
            this.authors.put(userName, create(DynamicLabel.label("Author"), getProperty(userName)));
        }

        for (Map.Entry<String, List<Article>> entry : wrote.entrySet()) {
            String userName = entry.getKey();
            for (Article article : entry.getValue()) {
                linkTo(this.authors.get(userName), this.articles.get(article.title), RelTypes.PUBLISH);
            }
        }

        for (Map.Entry<String, List<String>> entry : PublishWith.entrySet()) {
            String firstAuthor = entry.getKey();
            List<String> coWorkWuthors = entry.getValue();
            for (String coworker : coWorkWuthors)
                linkTo(this.authors.get(firstAuthor), this.authors.get(coworker), RelTypes.PUBLISHWITH);
        }
    }

    private Node create(Label label, Map<String, String> property) {

        Node n = null;
        try (Transaction tx = graphDb.beginTx()) {
            n = graphDb.createNode(label);
            for (Map.Entry<String, String> entry : property.entrySet())
                n.setProperty(entry.getKey(), check(entry.getValue()));
            tx.success();
        }
        return n;
    }

    private String check(String val) {
        return val == null ? "" : val;
    }

    private Map<String, String> getProperty(Article article) {
        Map<String, String> map = new HashMap<>();
        map.put("title", article.title);
        map.put("pages", article.pages);
        map.put("volume", article.volume);
        map.put("journal", article.journal);
        map.put("number", article.number);
        map.put("year", article.year);
        map.put("ee", article.ee);
        map.put("fullContext", article.toString());
        return map;
    }

    private Map<String, String> getProperty(String userName) {
        Map<String, String> map = new HashMap<>();
        map.put("name", userName);
        return map;
    }


    private void truncteDB(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            graphDb.execute("match (n)  delete n");
            tx.success();
        }
    }

    private void linkTo(Node author, Node article, RelTypes relTypes) {
        try (Transaction tx = graphDb.beginTx()) {
            author.createRelationshipTo(article, relTypes);
            tx.success();
        }
    }

    private void registerShutdownHook(final GraphDatabaseService graphDb) {
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

    private enum RelTypes implements RelationshipType {
        PUBLISH,
        PUBLISHWITH;
    }

    public static void main(String[] args) throws Exception {
        String xmlPath = "/Users/bluebyte60/Documents/school/DataFlow/Project/KG-DBLP/smalldblp.xml";

        String dbPath = "/Users/bluebyte60/Documents/Neo4j/dblpLarge";

        Preprocess preprocess = new Preprocess(xmlPath, dbPath);

        preprocess.process();
    }
}
