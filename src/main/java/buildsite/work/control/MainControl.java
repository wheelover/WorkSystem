package buildsite.work.control;


import buildsite.model.MapData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class MainControl {

    private static final Logger LOG = LoggerFactory.getLogger(MainControl.class);

    @PostConstruct
    public void init(){
        LOG.info("MainControl 启动啦");
        LOG.info("MainControl 注入啦");
    }

    @GetMapping("/index")
    private String index(Model model){
        return "index";
    }

    @GetMapping("/layout")
    private String layout(Model model){
        return "layout";
    }

    @GetMapping("/location")
    private String location(Model model){
        return "location";
    }

    @GetMapping("/login")
    private String login(Model model){
        return "login";
    }

}
