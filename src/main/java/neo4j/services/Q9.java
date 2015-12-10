package neo4j.services;
import neo4j.repositories.PaperRepository;

import java.util.List;
import java.util.Map;

/**
 * Created by tongtongbao on 12/9/15.
 */
public class Q9 {

    List<Map<String, Object>> getTreeData(String journal, int limit, PaperRepository paperRepository) {
        List<Map<String, Object>> listData = paperRepository.q9(journal, limit);

        return listData;
    }
}
