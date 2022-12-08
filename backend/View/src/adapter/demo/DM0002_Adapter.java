package adapter.demo;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.hybrid.adapter.api.Adapter;
import com.mcnc.bizmob.hybrid.adapter.api.IAdapterJob;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;

import adapter.common.AdapterUtil;
import adapter.common.CodesEx;
import adapter.demo.model.DM0002.DM0002Request;
import adapter.demo.model.DM0002.DM0002Request_Body;
import adapter.demo.model.DM0002.DM0002Response;
import adapter.demo.model.DM0002.DM0002Response_Body;
import adapter.demo.model.DM0002.DM0002Response_Body_list;
import adapter.demo.model.header.DMHeader;

@Adapter(trcode = { "DM0002" })
public class DM0002_Adapter extends AbstractTemplateAdapter implements
		IAdapterJob {

	private static final Logger logger = LoggerFactory
			.getLogger(DM0002_Adapter.class);

	@Autowired
	private DBAdapter dbAdapter;
	
	@SuppressWarnings("unchecked")
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		long 		startAdapter	= System.currentTimeMillis();
		long 		endAdapter	= 0;
		long		startLegacy 	= 0 ;
		long		endLegacy 	= 0;
		
		JsonNode	reqRootNode 	= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		long 			tID 				= AdapterUtil.getTransactionId(obj);
		logger.debug(tID + "Start::onProcess()");
		
		/** 1. 전문의 Request Header, Body (전문별 코딩)  **/		
		DM0002Request			request	= new DM0002Request(obj);
		DMHeader				header	= request.getHeader();
		DM0002Request_Body	reqBody	= request.getBody();
		
		// 세션 Node
		ObjectNode 				sessionNode 	= (ObjectNode)obj.get(JsonAdaptorObject.TYPE.META);
				
		try {
			
			/** 2. 전문 데이터 추출 **/
			int startIndex = reqBody.getStartIndex();
			int endIndex = reqBody.getEndIndex();
			

			
			//Legacy 시작시간
			startLegacy		= System.currentTimeMillis();
			
			/** 3. Legacy 연동 코드 코딩 (DB, HTTP, SMTP 등등) **/
			HashMap< String, Object >	inputParameterMap	= new HashMap< String, Object >();
			inputParameterMap.put( "startIndex", startIndex );
			inputParameterMap.put( "endIndex", endIndex );
		
			
			int totalCnt = (Integer) dbAdapter.selectOne(CodesEx.DATA_SOURCE_DEMO_LEGACYDB, "DM0002.DM0002001", inputParameterMap);
			
			List <DM0002Response_Body_list> boardList = null;
			if( totalCnt > 0 ){
				boardList =  (List<DM0002Response_Body_list>) dbAdapter.selectList(CodesEx.DATA_SOURCE_DEMO_LEGACYDB, "DM0002.DM0002002", inputParameterMap);
			}
			
			//Legacy 종료시간
			endLegacy		= System.currentTimeMillis();
			
			/** 4. Adapter Logic **/
			
			
			/** 5. 전문의 Response Header, Body (전문별 코딩)  **/		
			DM0002Response			response	= new DM0002Response();
			DM0002Response_Body	resBody	= new DM0002Response_Body();
			
			resBody.setTotalCnt(totalCnt);
			if( boardList != null ){
				resBody.setList(boardList);
			}
			
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
