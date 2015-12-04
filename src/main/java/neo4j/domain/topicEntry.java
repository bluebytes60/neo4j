package neo4j.domain;

/**
 * Created by tongtongbao on 12/3/15.
 */
public class topicEntry {

    String text;
    int weight;

    public topicEntry(String text, int weight)
    {
        this.text = text;
        this.weight = weight;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
