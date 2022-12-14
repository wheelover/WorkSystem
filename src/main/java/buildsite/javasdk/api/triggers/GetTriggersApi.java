package buildsite.javasdk.api.triggers;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import buildsite.javasdk.api.AbstractAPI;
import buildsite.javasdk.exception.OnenetApiException;
import buildsite.javasdk.http.HttpGetMethod;
import buildsite.javasdk.request.RequestInfo.Method;
import buildsite.javasdk.response.BasicResponse;
import buildsite.javasdk.response.triggers.TriggersResponse;
import buildsite.javasdk.utils.Config;

public class GetTriggersApi extends AbstractAPI {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private HttpGetMethod HttpMethod;
	private String tirggerId;
	
	/**
	 * 查询单个触发器
	 * @param tirggerId:触发器ID,String
	 * @param key:masterkey 或者 设备apikey
	 */
	public GetTriggersApi(String tirggerId,String key) {
		this.tirggerId = tirggerId;
		this.key=key;
		this.method = Method.GET;
		this.HttpMethod=new HttpGetMethod(method);
        Map<String, Object> headmap = new HashMap<String, Object>();
        headmap.put("api-key", key);
        HttpMethod.setHeader(headmap);
        this.url = Config.getString("test.url") + "/triggers" + "/" + tirggerId;
        HttpMethod.setcompleteUrl(url,null);
	}

	public BasicResponse<TriggersResponse> executeApi() {
		BasicResponse response;
//		ObjectMapper mapper = new ObjectMapper();
//        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		try {
			HttpResponse httpResponse=HttpMethod.execute();
			response = mapper.readValue(httpResponse.getEntity().getContent(), BasicResponse.class);
			response.setJson(mapper.writeValueAsString(response));
			Object newData = mapper.readValue(mapper.writeValueAsString(response.getDataInternal()), TriggersResponse.class);
			response.setData(newData);
			return response;
		} catch (Exception e) {
			logger.error("json error {}", e.getMessage());
			throw new OnenetApiException(e.getMessage());
		}
		finally {
			try {
				HttpMethod.httpClient.close();
			} catch (Exception e) {
				logger.error("http close error: {}", e.getMessage());
				throw new OnenetApiException(e.getMessage());
			}
		}
	}
}