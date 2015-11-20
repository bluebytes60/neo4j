package neo4j.repositories;

import neo4j.domain.Paper;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public interface PaperRepository extends GraphRepository<Paper> {
    Paper findByTitle(@Param("title") String title);

    @Query("MATCH (p:Paper) WHERE p.title =~ ('(?i).*'+{title}+'.*') RETURN p")
    Collection<Paper> findByTitleContaining(@Param("title") String title);

    @Query("MATCH (p:Paper)<-[:PUBLISH]-(a:Author) RETURN p.title as paper, collect(a.name) as cast LIMIT {limit}")
    List<Map<String, Object>> graph(@Param("limit") int limit);

    @Query("MATCH (a)-[:PUBLISH]->(m)<-[:PUBLISH]-(d) where a.name = {name} RETURN m.title as paper, collect(d.name) as cast LIMIT {limit}")
    List<Map<String, Object>> q5(@Param("name") String name, @Param("limit") int limit);


}


