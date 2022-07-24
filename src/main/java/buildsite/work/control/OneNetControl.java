package buildsite.work.control;

import buildsite.Utils.JSONUtils;
import cmcc.iot.onenet.javasdk.api.cmds.SendCmdsApi;
import cmcc.iot.onenet.javasdk.response.BasicResponse;
import cmcc.iot.onenet.javasdk.response.cmds.NewCmdsResponse;
import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class OneNetControl {

    private static final Logger LOG = LoggerFactory.getLogger(OneNetControl.class);

    private static String token ="202207101730";//用户自定义token和OneNet第三方平台配置里的token一致

    private static String aeskey ="tT9komC0ihozLxUpu+Tmprp+ksN118UWuC1ywzV+Lfw=";//aeskey和OneNet第三方平台配置里的token一致

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
        LOG.info("信息推送开始");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer("");
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            br.close();
            LOG.info("sb的内容是：" + sb);

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




    @GetMapping("/pushOrder")
    public void testSendStrCmdsApi() throws IOException {
        LOG.info("下达命令，测试开始");
        String devId = "968979623";
        String key = "6Q4jl6cL41tqLf2N7ATwtmCvIic=";
        String text = "hello";
        /**
         * 发送命令
         * @param devId：接收该数据的设备ID（必选），String
         * @param qos:是否需要响应，默认为0,Integer
         * 0：不需要响应，即最多发送一次，不关心设备是否响应；
         * 1：需要响应，如果设备收到命令后没有响应，则会在下一次设备登陆时若命令在有效期内(有效期定义参见timeout参数）则会继续发送。
         * 对响应时间无限制，多次响应以最后一次为准。
         * 本参数仅当type=0时有效；
         * @param timeOut:命令有效时间，默认0,Integer
         * 0：在线命令，若设备在线,下发给设备，若设备离线，直接丢弃；
         *  >0： 离线
         *                     命令，若设备在线，下发给设备，若设备离线，在当前时间加timeout时间内为有效期，有效期内，若设备上线，则下发给设备。单位：秒，有效围：0~2678400。
         *  本参数仅当type=0时有效；
         * @param type://默认0。0：发送CMD_REQ包，1：发送PUSH_DATA包
         * @param contents:用户自定义数据：json、string、二进制数据（小于64K）
         * @param key:masterkey或者设备apikey
         */
        SendCmdsApi api = new SendCmdsApi(devId, null, null, null, text, key);
        BasicResponse<NewCmdsResponse> response = api.executeApi();
        System.out.println(response.getJson());
    }

}
