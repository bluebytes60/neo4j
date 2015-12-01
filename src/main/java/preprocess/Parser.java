package preprocess;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by bluebyte60 on 9/8/15.
 */

public class Parser {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    String filePath; //file path of xml file
    int max = 10000; //max number of instances saved

    public Parser(String filePath) {
        this.filePath = filePath;
    }

    public List<Article> getAricles() throws Exception {
        List<Article> articles = new ArrayList<Article>();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new FileInputStream(new File(this.filePath)));
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Article article = new Article();
                NodeList childNodes = node.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node cNode = childNodes.item(j);
                    if (cNode instanceof Element) {
                        String content = cNode.getLastChild().getTextContent().trim();
                        switch (cNode.getNodeName()) {
                            case "author":
                                article.authors.add(content);
                                break;
                            case "title":
                                article.title = content;
                                break;
                            case "pages":
                                article.pages = content;
                                break;
                            case "year":
                                article.year = content;
                                break;
                            case "volume":
                                article.volume = content;
                                break;
                            case "journal":
                                article.journal = content;
                                break;
                            case "number":
                                article.number = content;
                                break;
                            case "url":
                                article.url = content;
                                break;
                            case "ee":
                                article.ee = content;
                                break;
                        }
                    }
                }
                articles.add(article);
                if (articles.size() == max)
                    return articles;
            }

        }
        return articles;
    }

    public static void main(String[] args) throws Exception {
        for(Article article:new Parser("/Users/bluebyte60/Documents/school/DataFlow/Project/KG-DBLP/small_dblp.xml").getAricles()) {
            System.out.println(article);
        }
    }
}
