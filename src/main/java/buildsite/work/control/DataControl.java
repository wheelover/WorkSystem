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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @PostConstruct
    public void init(){
        LOG.info("DataControl 启动啦");
        LOG.info("DataControl 注入啦");
    }

    @PostMapping("/saveCircle")
    @ResponseBody
    private Map saveCircle(@RequestParam String lng, @RequestParam String lat, @RequestParam String radius, HttpServletRequest request, HttpServletResponse response){

        LOG.info("数据上传调用");
        Map returnData = new HashMap();
        Map<String, String> point = new HashMap<>();
        if (lng != null && lat != null && radius != null) {
            point.put(lng, lat);
            MapData mapData = new MapData();

            List<Map<String, String>> points = new ArrayList<>();
            points.add(point);
            mapData.setId(id);
            mapData.setPoints(points);
            mapData.setRadius(radius);
            mongoTemplate.insert(mapData);
            id = id + 1;
            LOG.info("data put success");

            returnData.put("result", true);
            returnData.put("message", "data push successful");
            return returnData;
        }else {
            returnData.put("result", false);
            returnData.put("message", "data push not correct");
            LOG.error("data push false");
            return returnData;

        }
    }

    @PostMapping("/savaPoints")
    @ResponseBody
    private void savePoints(String lng, String lat){

    }

}
