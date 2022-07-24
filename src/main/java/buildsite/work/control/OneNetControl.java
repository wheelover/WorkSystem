package buildsite.work.control;

import buildsite.Utils.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Controller
public class OneNetControl {

    private static final Logger LOG = LoggerFactory.getLogger(OneNetControl.class);

    @PostConstruct
    public void init(){
        LOG.info("OneNetControl 启动啦");
        LOG.info("OneNetControl 注入啦");
    }

    /**
     * 移动消息订阅接口
     * @since
     * //{"msg":{"at":1556504219767,"imei":"867726030xxxxxx","type":1,"ds_id":"3311_0_5706",
    //"value":"5A0xxx0D3DxxxxC50DB62C37164C0xxx005CC6CF1900004BFFFFxxx01802","dev_id":524552229}
    //,"msg_signature":"Db+kYcaCTjYBcCW04naOpA==","nonce":"O9JpI(1o"}
     * @param request
     * @param response
     */
    @RequestMapping("/getMessage")
    public void msgSubscription(HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer("");
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            br.close();
            String result = sb.toString();
            LOG.info("callbackUrl data >> "+result);
            String msg = JSONUtils.getJosnValue(result, "msg"); //数据内容
            String imei = JSONUtils.getJosnValue(msg, "imei"); //IMEI
            String value = JSONUtils.getJosnValue(msg, "value"); //内容
            String ds_id = JSONUtils.getJosnValue(msg, "ds_id"); //标识 体脂称设备固定值 3311_0_5706
            String dev_id = JSONUtils.getJosnValue(msg, "ds_id"); //设备ID
            String type = JSONUtils.getJosnValue(msg, "type"); //1：设备上传数据点消息 ,2：设备上下线消息 	7：缓存命令下发后结果上报（仅支持NB设备）
            LOG.info(" imei  <<< ===="+imei);
            LOG.info("value  <<< ===="+value);
            LOG.info("dev_id <<< ===="+dev_id);


//	        response.getOutputStream().write(msg.getBytes());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", 0);
            JSONObject jsonArray = new JSONObject(map);
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonArray.toString());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }



}
