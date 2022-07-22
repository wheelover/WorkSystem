package buildsite.work.control;

import buildsite.model.MapData;
import buildsite.model.Point;
import buildsite.service.impl.MapServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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
        List<MapData> mapDataList = mapService.getAll();
        for (MapData mapData : mapDataList){
          //  mapService.delete(mapData.getId());
        }
        LOG.info("DataControl 注入啦");
    }

    @PostMapping("/saveCircle")
    @ResponseBody
    private Map saveCircle(@RequestBody Map<String, Object> data){
        Map returnData = new HashMap();

        String lng = new String();
        String lat = new String();
        String radius = new String();

        try {
            lng = data.get("lng").toString();
            lat = data.get("lat").toString();
            radius = data.get("radius").toString();
        }catch (Exception e){
            System.out.println(e);
        }

        LOG.info("数据：" + "lng: " +lng + " lat: " + lat + " radius: " +radius);

        if (lng != null && lat != null && radius != null) {
            Point point = new Point();
            MapData mapData = new MapData();
            List<Point> points = new ArrayList<>();

            point.setLat(lat);
            point.setLng(lng);

            int intId = Integer.parseInt(id);
            id = Integer.toString(intId + 1);

            points.add(point);
            mapData.setId(id);
            mapData.setPoints(points);
            mapData.setRadius(radius);
            mapService.add(mapData);

            LOG.info("data put success / id = " + id);

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
    private Map savePoints(@RequestBody Map<String, Object> data){

        Map returnData = new HashMap();
        return returnData;

    }

    @GetMapping("/getData")
    @ResponseBody
    private MapData getData(@RequestParam(name = "id")String id){

        MapData mapData = mapService.get(id);
        return mapData;

    }

}
