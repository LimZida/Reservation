package adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.db.type.DBMap;
import com.mcnc.bizmob.hybrid.adapter.api.Adapter;
import com.mcnc.bizmob.hybrid.adapter.api.IAdapterJob;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;

import model.MYP1000S1.MYP1000S1Request;
import model.MYP1000S1.MYP1000S1Request_Body;
import model.MYP1000S1.MYP1000S1Response;
import model.MYP1000S1.MYP1000S1Response_Body;
import model.MYP1000S1.MYP1000S1Response_Body_reserveList;
import model.header.DMHeader;
import service.SliceArrayService;

/***
*
* <p> Title : 마이페이지 - MYP1000S1
*
* <p> Legacy System : BIZMEET DB
*
* <p> Description : 마이페이지를 통해 회원 정보와 사용자가 예약한 회의를 조회
*
* <p> Error Code : PersistenceException => 쿼리문 오류입니다. (reqMap 값 매핑 or 표현 오류)
*					NullPointerException => 요청으로 받은 값 중에 NULL 값을 참조했습니다.
*					OutOfMemoryError => 배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다.
*					Exception => 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException
*
*
*
* <p> Last Update Date : 2022.11.10
*
* @author LHY
*
*/
@Adapter(trcode = { "MYP1000S1" })
public class MYP1000S1_Adapter extends AbstractTemplateAdapter implements IAdapterJob {


	private static final Logger logger = LoggerFactory.getLogger(MYP1000S1_Adapter.class);
	// 배열 범위 반환
	@Autowired
	private SliceArrayService sliceArray;

	@Autowired
	private DBAdapter dbAdapter;

	@SuppressWarnings("finally")
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		/* 전문의 Request Header, Body (전문별 코딩) */
		// Request 객체 생성
		MYP1000S1Request 		request	= 	new MYP1000S1Request(obj);
		// Request Header, Body 가져오기
		DMHeader 				header 	= 	request.getHeader();
		MYP1000S1Request_Body	reqBody	= 	request.getBody();
		logger.debug("####MYP1000S1 reqBody :{}", reqBody);

//      응답 객체 생성
		MYP1000S1Response response = new MYP1000S1Response();
		MYP1000S1Response_Body resBody = new MYP1000S1Response_Body();

		try {
			// 전문코드 가져오기
			String trCode = header.getTrcode();

			// 요청으로 userSeqPK, endIndex, startIndex, 예정, 진행, 종료 버튼 값 가져오기
			int userSeqPK = reqBody.getUserSeqPK();
			int endIndex = reqBody.getEndIndex();
			int startIndex = reqBody.getStartIndex();
			List<Integer> checkResultArray= reqBody.getCheckResultArray();

			// DBMap 객체 생성
			DBMap reqMap = new DBMap();
			// DBMap에 userSeqPK 삽입
			reqMap.put("userSeqPK", userSeqPK);
			reqMap.put("endIndex", endIndex);
			reqMap.put("startIndex", startIndex);

			// resBody에 MYP1000S1.xml을 통한 SELECT_USER_INFO 쿼리문으로 해당 Response를 가져와 DB 매핑
			// MYP1000S1Response_Body에 존재하는 변수들에 값을 매핑
			resBody = dbAdapter.selectOne("eduDB", trCode + ".SELECT_USER_INFO", reqMap, MYP1000S1Response_Body.class);
			if(resBody==null) {
				logger.error("#### DB:{}","MYP1000S1 SelectOne 결과 실패");
			}
			else {
				// reserveList에 MYP1000S1.xml에 SELECT_RESERVE_LIST 쿼리문으로 해당 Response를 가져와 DB 매핑
				// MYP1000S1Response_Body_reserveList에 존재하는 변수들에 값을 매핑
				List<MYP1000S1Response_Body_reserveList> resultList = new ArrayList<MYP1000S1Response_Body_reserveList>();
				int index=2;
//				예정, 진행, 종료 순서
				for(int i:checkResultArray) {
					reqMap.put("checkResult", i);
					reqMap.put("userIdx", index);
//					버튼이 눌려있으면
					if(i!=0) {
//						Reserve 테이블에서 예정 or 진행 or 종료 예약 내역 확인
						List<MYP1000S1Response_Body_reserveList> reserveList = dbAdapter.selectList("eduDB",
								trCode + ".SELECT_RESERVE_LIST", reqMap, MYP1000S1Response_Body_reserveList.class);
						if(reserveList.size()!=0) {
//							resultList에 Reserve 테이블에서 나온 예정 or 진행 or 종료 순서대로 저장
							resultList.addAll(reserveList);
						}
						else {
						}
					}
					reqMap.remove("checkResult");
					reqMap.remove("userIdx");
					index--;
				}
				// 예약 리스트가 존재할 경우
				// 응답(성공)
				if( resultList.size() > 0) {
					logger.debug("#### DB:{}","MYP1000S1 resultList 결과 존재");

					List<MYP1000S1Response_Body_reserveList> responseList = sliceArray.getSlice(resultList, startIndex, endIndex+1);
					resBody.setReserveList(responseList);
				// 예약 리스트가 비어있을 경우
				// 응답(실패)
				} else {
					logger.error("#### DB:{}","MYP1000S1 resultList 결과 없음");

					resBody.setReserveList(null);
				}
			}
		}
//		예외처리
		catch(PersistenceException e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 쿼리문 오류입니다. (매핑 or 표현 오류) =>"+e.getMessage());
			header.setError_text(header.getTrcode() +" 쿼리문 오류입니다. (매핑 or 표현 오류)");
		}
		catch(NullPointerException e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 요청으로 받은 값 중에 NULL 값을 참조했습니다. => "+e.getMessage());
			header.setError_text(header.getTrcode() +" 요청으로 받은 값 중에 NULL 값을 참조했습니다.");
		}
		catch(OutOfMemoryError e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다. =>"+e.getMessage());
			header.setError_text(header.getTrcode() +" 배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다.");
		}
		catch(Exception e) {
			logger.error("#### " + header.getTrcode() + "_ERR : 그 외 오류 발생.");
			logger.error("#### " + header.getTrcode() + "_ERR : 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException => " + e);
			header.setError_text(header.getTrcode() +" 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException ");
			return makeFailResponse(header.getTrcode() + "EXCEPTION"," 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException");
		}
		finally {
			response.setHeader(header);
			response.setBody(resBody);
			return makeResponse(obj, response.toJsonNode());
		}
	}
}