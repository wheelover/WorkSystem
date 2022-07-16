package buildsite.work.control;

import buildsite.model.MapData;
import buildsite.service.impl.MapServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DataControl {

    private  static final Logger LOG = LoggerFactory.getLogger(DataControl.class);

    @Autowired
    private MapServiceImpl mapService;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static String id = "0";


    @PostMapping("/saveCircle")
    @ResponseBody
    private void saveCircle(String lng, String lat, String radius){

        Map<String, String> point = new HashMap<>();
        point.put(lng, lat);
        MapData mapData = new MapData();

        List<Map<String, String>> points = new ArrayList<>();
        points.add(point);
        mapData.setId(id);
        mapData.setPoints(points);
        mapData.setRadius(radius);
        mongoTemplate.insert(mapData);
        id = id + 1;

    }

    @PostMapping("/savaPoints")
    @ResponseBody
    private void savePoints(String lng, String lat){

    }

}
