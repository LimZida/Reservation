package adapter.demo;


import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
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
import com.mcnc.bizmob.hybrid.server.web.dao.LocalFileStorageAccessor;

import adapter.common.AdapterUtil;
import adapter.common.CodesEx;
import adapter.demo.model.DM0004.DM0004Request;
import adapter.demo.model.DM0004.DM0004Request_Body;
import adapter.demo.model.DM0004.DM0004Request_Body_attachList;
import adapter.demo.model.DM0004.DM0004Response;
import adapter.demo.model.header.DMHeader;

@Adapter(trcode = { "DM0004" })
public class DM0004_Adapter extends AbstractTemplateAdapter implements
		IAdapterJob {

	private static final Logger logger = LoggerFactory
			.getLogger(DM0004_Adapter.class);

	@Autowired
	private DBAdapter dbAdapter;

	// LocalFileStorageAccessor Get Instance
	@Autowired
	private LocalFileStorageAccessor uploadStorageAccessor;
			
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		long 		startAdapter	= System.currentTimeMillis();
		long 		endAdapter	= 0;
		long		startLegacy 	= 0 ;
		long		endLegacy 	= 0;
		
		JsonNode	reqRootNode 	= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		long 			tID 				= AdapterUtil.getTransactionId(obj);
		logger.debug(tID + "Start::onProcess()");
		
		/** 1. 전문의 Request Header, Body (전문별 코딩)  **/		
		DM0004Request			request	= new DM0004Request(obj);
		DMHeader				header	= request.getHeader();
		DM0004Request_Body	reqBody	= request.getBody();
		
		// 세션 Node
		ObjectNode 				sessionNode 	= (ObjectNode)obj.get(JsonAdaptorObject.TYPE.META);
		
		// Transaction 처리 세션
		SqlSession session = dbAdapter.openSession("LegacyDB", false);
					
		try {
			
			/** 2. 전문 데이터 추출 **/
			String title = reqBody.getTitle();
			String contents = reqBody.getContents();
			List<DM0004Request_Body_attachList> attachList = reqBody.getAttachList();
			String attachFlag = "N" ;
			if( attachList != null && attachList.size() > 0 ){
				attachFlag = "Y";
			}

			String userId 		= sessionNode.get(CodesEx.DEMO_SESSION_USER_ID).textValue();
			String userName 	= sessionNode.get(CodesEx.DEMO_SESSION_USER_NAME).textValue();
			String deptName 	= sessionNode.get(CodesEx.DEMO_SESSION_DEPT_NAME).textValue();
			
			String docId = null; 
			
			//Legacy 시작시간
			startLegacy		= System.currentTimeMillis();
			
			/** 3. Legacy 연동 코드 코딩 (DB, HTTP, SMTP 등등) **/
			
			
			int updateResult = dbAdapter.update(session, "DM0004.DM0004001", null); // 시퀀스 업데이트
			
			if( updateResult > 0 ){
				session.commit();
			
				docId = (String) dbAdapter.selectOne(session, "DM0004.DM0004002", null, String.class);

				HashMap< String, Object >	inputParameterMap	= new HashMap< String, Object >();
				inputParameterMap.put( "docId", docId );
				inputParameterMap.put( "title", title );
				inputParameterMap.put( "contents", contents );
				inputParameterMap.put( "attachFlag", attachFlag );
				inputParameterMap.put( "userId", userId );
				
				int insertResult = dbAdapter.insert(session, "DM0004.DM0004003", inputParameterMap);
				if( insertResult < 1){
					logger.error("게시물 등록 실패!!");
					return makeFailResponse( header.getTrcode() + 
							CodesEx.GROUPWARES_ERROR_PREFIX + 
							"1000" , 
							"게시물 등록 중 오류가 발생했습니다." );						
				}
				
				// 첨부파일 등록
//				String baseDir = Smart.HOME_DIR + File.separator + "datafiles";
//				if( "Y".equalsIgnoreCase(attachFlag) ){
//					for (DM0004Request_Body_attachList attach : attachList) {
//						String attachUID = attach.getUid();
//						String fileName = uploadStorageAccessor.getFileName(attachUID);
//						
//						String tmpFile = Smart.HOME_DIR + File.separator + "upload_temp" + File.separator + attachUID + ".mob" ;
//						String saveFile = baseDir + File.separator + "bizMOBDemo" + File.separator + docId + File.separator + fileName;
//						
//						FileUtil.copyFile(new File(tmpFile), new File(saveFile) );
//						
//						inputParameterMap.clear();
//						inputParameterMap.put( "docId", docId );
//						inputParameterMap.put( "filePath", "bizMOBDemo" + File.separator + docId );
//						inputParameterMap.put( "fileName", fileName );
//						int attachInsertResult = dbAdapter.insert(session, "DM0004.DM0004004", inputParameterMap);
//						if( attachInsertResult  > 0 ){
//							uploadStorageAccessor.remove(attachUID);
//						}
//					}
//				}
				
			}else{
				dbAdapter.rollbackAndClose(session);
				logger.error("시퀀스 업데이트 실패!!");
				return makeFailResponse( header.getTrcode() + 
						CodesEx.GROUPWARES_ERROR_PREFIX + 
						"1001" , 
						"게시물 등록 중 오류가 발생했습니다." );
			}
			
			dbAdapter.commitAndClose(session);
			//Legacy 종료시간
			endLegacy		= System.currentTimeMillis();
			
			/** 4. Adapter Logic **/
			
			/** 5. 전문의 Response Header, Body (전문별 코딩)  **/		
			DM0004Response			response	= new DM0004Response();
			
			response.setHeader(header);
			
			return makeResponse(obj, response.toJsonNode());
		}catch (Exception e) {
			dbAdapter.rollbackAndClose(session);
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
