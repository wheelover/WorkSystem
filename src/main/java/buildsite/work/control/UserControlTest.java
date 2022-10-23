package buildsite.work.control;

import buildsite.model.MapData;
import buildsite.model.User;
import buildsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/test/user")
public class UserControlTest {
    @Autowired
    private UserService userService;

    @PostConstruct
    public void init(){
        userService.delete("0");
        User user = new User();
        user.setId("0");
        user.setName("admin");
        user.setPassword("admin12345");
        userService.add(user);
    }

}
