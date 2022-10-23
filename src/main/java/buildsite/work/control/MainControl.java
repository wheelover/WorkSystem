package buildsite.work.control;


import buildsite.model.MapData;
import buildsite.service.impl.MapServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class MainControl {

    private static final Logger LOG = LoggerFactory.getLogger(MainControl.class);

    @Autowired
    private MapServiceImpl mapService;

    @PostConstruct
    public void init(){
        LOG.info("MainControl 启动啦");
        LOG.info("MainControl 注入啦");
    }

    @GetMapping("/index")
    public String index(Model model){
        setMapData(model);
        return "index";
    }

    @GetMapping("/layout")
    public String layout(Model model){
        return "layout";
    }

    @GetMapping("/location")
    public String location(Model model){
        return "location";
    }

    private void setMapData(Model model){

        MapData mapData = mapService.get("815");
        model.addAttribute("mapData", mapData);

    }

}
