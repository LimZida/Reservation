/*
 * AdapterUtil.java
 * Copyright Since 2010, MOBILE C&C LTD. All rights reserved.
 */
package adapter.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcnc.bizmob.common.config.SmartConfig;
import com.mcnc.bizmob.common.util.JsonUtil;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject.TYPE;


public abstract class AdapterUtil {
	
	private static Logger logger = LoggerFactory
			.getLogger(AdapterUtil.class);

    /**
     * 세션에 저장되어 있는 TRANSACTION_ID를 얻는다.
     * @param obj Biz Logic에서 Adapter로 전달한 객체
     * @return TRANSACTION_ID 값. 만약, 세션 data가 없거나, TRANSACTION_ID가 없으면 0
     */
    public static long getTransactionId(JsonAdaptorObject obj) {
        long id = 0;
        
        if(obj.containsKey(TYPE.META)) {
            JsonNode node = (JsonNode) obj.get(TYPE.META);
            
            try {
                id = (Long) JsonUtil.getValue(node, "TRANSACTION_ID");
            } catch(Exception e) {
                id = 0;
            }
        }
        
        return id;
    }
    
    /**
     * 세션에 저장되어 있는 값을 얻는다.
     * @param obj Biz Logic에서 Adapter로 전달한 객체
     * @param key 세션에 저장한 key 이름
     * @return 세션에 저장되어 있는 값 값. 만약, 세션 data가 없거나, key가 없으면 null
     */
    public static Object getSessionValue(JsonAdaptorObject obj, String key) {
        Object data = null;
        
        if(obj.containsKey(TYPE.META)) {
            JsonNode node = (JsonNode) obj.get(TYPE.META);
            
            try {
                JsonNode aNode = JsonUtil.find(node, key);
                
                if(!aNode.isMissingNode()) {
                    data = JsonUtil.getValue(node, key);
                }
            } catch(Exception e) {
                data = null;
            }
        }
        
        return data;
    }
    
    /**
     * 세션 ID를 얻는다.
     * @param obj Biz Logic에서 Adapter로 전달한 객체
     * @return 세션 ID
     */
    public static String getSessionId(JsonAdaptorObject obj) {
        String id = "Unknown";
        
        if(obj.containsKey(TYPE.META)) {
            JsonNode node = (JsonNode) obj.get(TYPE.META);
            
            try {
                id = (String) JsonUtil.getValue(node, "id");
            } catch(Exception e) {
                id = "Unknown";
            }
        }
        
        return id;
    }
        
    public static void adapterInfo(long tID, long adapterTime, long legacyTime, String trCode, String addInfo) {
    	
    	StringBuffer logMessage			= new StringBuffer();
    	
    	String 			trInfo 			= SmartConfig.getString(trCode);
		
		logMessage.append("\n");
		logMessage.append("##############################################");
		logMessage.append("\n");
		logMessage.append("### 전문번호 : " + trCode);
		logMessage.append("\n");
		logMessage.append("### 서비스명 : " + trInfo);
		logMessage.append("\n");
		logMessage.append("### 요청 파라미터 : " + addInfo);
		logMessage.append("\n");
    	logMessage.append("### 레거시 처리시간 : " + legacyTime + "ms");
		logMessage.append("\n");
    	logMessage.append("### 어댑터 처리시간 : " + adapterTime + "ms");    	
    	logMessage.append("\n");
    	logMessage.append("##############################################");
    	
		logger.info(logMessage.toString());
		
    }
    
    /**
     * String 형태의 JSON을 JsonNode로 변환
     * @param msg
     * @return JsonNode
     */
    public static JsonNode jsonParserToString(String msg) throws Exception{
    	ObjectMapper mapper = new ObjectMapper(); 
		JsonFactory f = new JsonFactory();
		JsonParser p = f.createJsonParser(msg); 
		JsonNode actualObj = mapper.readTree(p);
		
		return actualObj;
    }
    
}
