package buildsite.javasdk.api.mqtt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

import buildsite.javasdk.api.AbstractAPI;
import buildsite.javasdk.exception.OnenetApiException;
import buildsite.javasdk.http.HttpGetMethod;
import buildsite.javasdk.request.RequestInfo.Method;
import buildsite.javasdk.response.BasicResponse;
import buildsite.javasdk.utils.Config;

public class GetDevicesTopicsApi extends AbstractAPI{
	private String devId;
	private HttpGetMethod HttpMethod;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 查询设备订阅的Topic列表
	 * @param devId：设备ID（必选）,String
	 * @param key：masterkey
	 */
	public GetDevicesTopicsApi(String devId,String key) {
		this.devId = devId;
		this.key=key;
		this.method = Method.GET;

        Map<String, Object> headmap = new HashMap<String, Object>();
        HttpMethod = new HttpGetMethod(method);
        this.url = Config.getString("test.url") +"/mqtt"+"/device_topic/"+devId;
        headmap.put("api-key", key);
        HttpMethod.setHeader(headmap);
        HttpMethod.setcompleteUrl(url,null);
	}


	public BasicResponse<List<String>> executeApi() {
		BasicResponse response = null;
		try {
			HttpResponse httpResponse = HttpMethod.execute();
			response = mapper.readValue(httpResponse.getEntity().getContent(), BasicResponse.class);
			response.setJson(mapper.writeValueAsString(response));
			Object newData = mapper.readValue(mapper.writeValueAsString(response.getDataInternal()), new TypeReference<List<String>>(){});
			response.setData(newData);
			return response;
		} catch (Exception e) {
			logger.error("error: {}" , e.getMessage());
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
