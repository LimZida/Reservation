package adapter.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.common.util.JsonUtil;
import com.mcnc.bizmob.hybrid.adapter.api.Adapter;
import com.mcnc.bizmob.hybrid.adapter.api.IAdapterJob;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject.TYPE;

import adapter.common.AdapterUtil;
import adapter.common.CodesEx;
import adapter.demo.model.DM0001.DM0001Request;
import adapter.demo.model.DM0001.DM0001Request_Body;
import adapter.demo.model.DM0001.DM0001Response;
import adapter.demo.model.DM0001.DM0001Response_Body;
import adapter.demo.model.header.DMHeader;

@Adapter(trcode = { "DM0001" })
public class DM0001_Adapter extends AbstractTemplateAdapter implements
		IAdapterJob {

	private static final Logger logger = LoggerFactory
			.getLogger(DM0001_Adapter.class);

	@Autowired
	private DBAdapter dbAdapter;
	
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		long 		startAdapter	= System.currentTimeMillis();
		long 		endAdapter	= 0;
		long		startLegacy 	= 0 ;
		long		endLegacy 	= 0;
		
		JsonNode	reqRootNode 	= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		long 			tID 				= AdapterUtil.getTransactionId(obj);
		logger.debug(tID + "Start::onProcess()");
		
		/** 1. 전문의 Request Header, Body (전문별 코딩)  **/		
		DM0001Request			request	= new DM0001Request(obj);
		DMHeader				header	= request.getHeader();
		DM0001Request_Body	reqBody	= request.getBody();
		
		// 세션 Node
		ObjectNode 				sessionNode 	= (ObjectNode)obj.get(JsonAdaptorObject.TYPE.META);
				
		try {
			
			/** 2. 전문 데이터 추출 **/
			String userId 	= reqBody.getUserId();
			String passwd 	= reqBody.getPasswd();
			
			//Legacy 시작시간
			startLegacy		= System.currentTimeMillis();
			
			/** 3. Legacy 연동 코드 코딩 (DB, HTTP, SMTP 등등) **/
			
			
			//Legacy 종료시간
			endLegacy		= System.currentTimeMillis();
			
			/** 4. Adapter Logic **/
			
			// 사용자 정보 세팅(세션 저장)
			if( sessionNode != null ) {
				JsonUtil.putValue(sessionNode, CodesEx.DEMO_SESSION_USER_NAME, "테스트 사용자"); 	// 사용자명 
				JsonUtil.putValue(sessionNode, CodesEx.DEMO_SESSION_USER_ID, userId); 				// 사용자ID 
				JsonUtil.putValue(sessionNode, CodesEx.DEMO_SESSION_DEPT_NAME, "기술지원팀"); 	// 부서명 
				obj.put(TYPE.META, sessionNode);
			}
						
			DM0001Response			response	= new DM0001Response();
			DM0001Response_Body	resBody	= new DM0001Response_Body();
			
			resBody.setUserName("테스트 사용자");
			
			response.setHeader(header);
			response.setBody(resBody);
			
			return makeResponse(obj, response.toJsonNode());
		}catch (Exception e) {
			logger.error("어댑터 에러", e);
			return makeFailResponse( header.getTrcode() + 
					CodesEx.GROUPWARES_ERROR_PREFIX + 
					CodesEx.SYSTEM_EXCEPTION , 
					"어댑터에서 로직 처리 중 에러가 발생하였습니다." );
		}finally{
			endAdapter	= System.currentTimeMillis();
			AdapterUtil.adapterInfo(tID, (endAdapter - startAdapter) , (endLegacy - startLegacy), 
					header.getTrcode() , reqRootNode.toString());
			logger.debug(tID + "End::onProcess()");
		}
	}
}
