package preprocess;

import java.util.*;

/**
 * Created by bluebyte60 on 9/8/15.
 */

/*
    <article mdate="2011-01-11" key="journals/acta/Saxena96">
    <author>Sanjeev Saxena</author>
    <title>Parallel Integer Sorting and Simulation Amongst CRCW Models.</title>
    <pages>607-619</pages>
    <year>1996</year>
    <volume>33</volume>
    <journal>Acta Inf.</journal>
    <number>7</number>
    <url>db/journals/acta/acta33.html#Saxena96</url>
    <ee>http://dx.doi.org/10.1007/BF03036466</ee>
    </article>
*/
public class Article {

    Set<String> authors = new HashSet<String>();
    String title;
    String pages;
    String year;
    String volume;
    String journal;
    String number;
    String url;
    String ee;

    public String toString() {
        return String.format("authors:%s, title:%s, years:%s, vol:%s, journal:%s, number:%s, url:%s, ee:%s, pages:%s",
                authors, title, year, volume, journal, number, url, ee, pages);
    }

}
