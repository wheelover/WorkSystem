package buildsite.work.control;

import buildsite.model.User;
import buildsite.model.UserLoginInfo;
import buildsite.param.UserQueryParam;
import buildsite.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserControl {
    private static final Logger LOG = LoggerFactory.getLogger(UserControl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void init() {
        LOG.info("UserControl 启动啦");
        List<User> userList = userService.getAll();
        for (User user : userList){
            userService.delete(user.getId());
        }
        LOG.info("userService 注入啦");
    }


    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @GetMapping("/unlogin")
    public String unlogin(Model model){
        return "unlogin";
    }


    @PostMapping("/authenticate")
    @ResponseBody
    public Map login(String name, String password, HttpServletRequest request,
                     HttpServletResponse response)throws Exception{
        Map returnData = new HashMap();
        // 根据登录名查询用户
        User regedUser = getUserByLoginName(name);


        LOG.info("检验登录");

        // 找不到此登录用户
        if (regedUser == null) {
            returnData.put("result", false);
            returnData.put("message", "userName not correct");
            return returnData;
        }

        if (regedUser.getPassword().equals(password)) {
            UserLoginInfo userLoginInfo = new UserLoginInfo();
            userLoginInfo.setUserId("123456789abcd");
            userLoginInfo.setUserName(name);
            // 取得 HttpSession 对象
            HttpSession session = request.getSession();
            // 写入登录信息
            session.setAttribute("userLoginInfo", userLoginInfo);
            returnData.put("result", true);
            returnData.put("message", "login successful");
        } else {
            returnData.put("result", false);
            returnData.put("message", "userName or password not correct");
            return returnData;
        }



        // 跳转登录
        String url = "/index";
        response.sendRedirect(url);

        return returnData;

    }

    private User getUserByLoginName(String loginName) {
        User regedUser = null;
        UserQueryParam param = new UserQueryParam();
        param.setName(loginName);
        Page<User> users = userService.list(param);

        // 如果登录名正确，只取第一个，要保证用户名不能重复
        if (users != null && users.getContent() != null && users.getContent().size() > 0) {
            regedUser = users.getContent().get(0);
        }

        return regedUser;
    }
}
