package buildsite.javasdk.api.device;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import buildsite.javasdk.api.AbstractAPI;
import buildsite.javasdk.exception.OnenetApiException;
import buildsite.javasdk.http.HttpDeleteMethod;
import buildsite.javasdk.request.RequestInfo.Method;
import buildsite.javasdk.response.BasicResponse;
import buildsite.javasdk.utils.Config;

public class DeleteDeviceApi extends AbstractAPI {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String devId;
	private HttpDeleteMethod HttpMethod;

	/**
	 * 设备删除
	 * @param devId: 设备ID,String
	 * @param key: //masterkey 或者 设备key
	 */
	public DeleteDeviceApi(String devId,String key) {
		this.devId = devId;
		this.key=key;
		this.method = Method.DELETE;
        Map<String, Object> headmap = new HashMap<String, Object>();
        HttpMethod = new HttpDeleteMethod(method);
        headmap.put("api-key", key);
        HttpMethod.setHeader(headmap);
        this.url = Config.getString("test.url") + "/devices" + "/" + devId;
        HttpMethod.setcompleteUrl(url,null);
	}


	public BasicResponse<Void> executeApi() {
		BasicResponse response = null;
		try {
			HttpResponse httpResponse = HttpMethod.execute();
			response = mapper.readValue(httpResponse.getEntity().getContent(), BasicResponse.class);
			response.setJson(mapper.writeValueAsString(response));
			return response;
		} catch (Exception e) {
			logger.error("json error {}", e.getMessage());
			throw new OnenetApiException(e.getMessage());
		}finally {
			try {
				HttpMethod.httpClient.close();
			} catch (Exception e) {
				logger.error("http close error: {}", e.getMessage());
				throw new OnenetApiException(e.getMessage());
			}
		}
	
	}
}
