package buildsite.work.control;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainControl {

    private static final Logger LOG = LoggerFactory.getLogger(MainControl.class);

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

}
