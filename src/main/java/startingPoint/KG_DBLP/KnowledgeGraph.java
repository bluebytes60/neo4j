package startingPoint.KG_DBLP;

import com.google.gson.Gson;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import neo4j.domain.*;
import neo4j.repositories.*;
import neo4j.services.DatasetService;
import neo4j.services.PaperService;

@Configuration
@Import(App.class)
@RestController("/")
public class KnowledgeGraph extends WebMvcConfigurerAdapter {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(KnowledgeGraph.class, args);
    }

    @Autowired
    PaperService paperService;
    @Autowired
    DatasetService datasetService;

    @Autowired
    PaperRepository paperRepository;
    @Autowired
    DatasetRepository datasetRepository;

    @RequestMapping("/graphTest")
    public String graphTest(@RequestParam(value = "limit", required = false) Integer limit) {
        Map<String, Object> map = paperService.graphAlc(limit == null ? 200 : limit);
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            //convert map to JSON string
            json = mapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping("/graphUserDataset")
    public String graphUserDataset(@RequestParam(value = "limit", required = false) Integer limit) {
        Map<String, Object> map = datasetService.graphAlc(limit == null ? 100 : limit);
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            //convert map to JSON string
            json = mapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping("/getPapers")
    public Collection<Paper> getPapers(String title) {
        return paperRepository.findByTitleContaining(title);
        //return paperRepository.findByTitleLike(title);
    }

    @RequestMapping("/getPaper")
    public Paper getPaper(String title) {
        //return movieRepository.findByTitleContaining(title);
        return paperRepository.findByTitle(title);
    }

    @RequestMapping(value = "/q5/{name}", method = RequestMethod.GET)
    public Map<String, Object> q5(@PathVariable String name) {
        return paperService.q5(name);
    }

    @RequestMapping(value = "/q12/part1/{k}/{keyword}", method = RequestMethod.GET)
    public List<String> q12Part1(@PathVariable int k, @PathVariable String keyword) {
        return paperService.q12Part1(keyword, k);
    }

    @RequestMapping(value = "/q12/part2/{k}/{keyword}", method = RequestMethod.GET)
    public Map<String, Object> q12Part2(@PathVariable int k, @PathVariable String keyword) {
        return paperService.q12Part2(keyword, k);
    }

    @RequestMapping(value = "/q22/{name}", method = RequestMethod.GET)
    public Map<String, Object> q22(@PathVariable String name) {
        return paperService.q22(name);
    }

}