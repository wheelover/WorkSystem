package buildsite.work.control;

import buildsite.javasdk.api.cmds.SendCmdsApi;
import buildsite.javasdk.response.BasicResponse;
import buildsite.javasdk.response.cmds.NewCmdsResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.annotation.PostConstruct;

import java.io.IOException;

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



    @GetMapping("/pushOrder-open")
    @ResponseBody
    public void testSendStrCmdsApi() throws IOException {
        LOG.info("下达开泵命令");
        String devId = "968979623";
        String key = "6Q4jl6cL41tqLf2N7ATwtmCvIic=";
        String text = "open";
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
        LOG.info(response.getJson());
    }

    @GetMapping("/pushOrder-close")
    @ResponseBody
    public void testSendStrCmdsApic() throws IOException {
        LOG.info("下达关机命令");
        String devId = "968979623";
        String key = "6Q4jl6cL41tqLf2N7ATwtmCvIic=";
        String text = "close";
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
        LOG.info(response.getJson());
    }

}
