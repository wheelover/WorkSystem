package buildsite.work.control;

import buildsite.model.Environment;
import buildsite.model.MapData;
import buildsite.service.impl.MapServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

@Controller
public class MapDataTestControl {

    private static final Logger LOG = LoggerFactory.getLogger(MapDataTestControl.class);

    @Autowired
    private MapServiceImpl mapService;

    @PostConstruct
    public void init(){
        LOG.info("测试启动啦！");
        LOG.info("测试注入啦！");
        mapService.delete("815");

        MapData mapData = new MapData();
        Environment environment = new Environment();

        environment.setLightIntensity("5");
        environment.setTemperature("23");
        environment.setHumidity("4d");
        environment.setSmoke("50");

        mapData.setId("815");
        mapData.setEnvironment(environment);
        mapService.add(mapData);

        System.out.println(mapService.get("815"));

    }

    @GetMapping("/add")
    public MapData putData(){

        MapData mapData = new MapData();
        Environment environment = new Environment();

        environment.setLightIntensity("5");
        environment.setTemperature("23");
        environment.setHumidity("4d");
        environment.setSmoke("50");

        mapData.setId("815");
        mapData.setEnvironment(environment);

        return mapService.add(mapData);


    }

}
