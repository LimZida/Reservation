package adapter;

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

import model.RES1300S1.RES1300S1Request;
import model.RES1300S1.RES1300S1Request_Body;
import model.RES1300S1.RES1300S1Response;
import model.RES1300S1.RES1300S1Response_Body;
import model.RES1300S1.RES1300S1Response_Body_attendList;
import model.RES1300S1.RES1300S1Response_Body_trxIdList;
import model.header.DMHeader;

/***
*
* <p> Title : 예약 상세 - RES1300S1
*
* <p> Legacy System : BIZMEET DB
*
* <p> Description : 사용자가 해당 예약의 상세에 들어가면 예약 정보의 모든 것을 응답
*
* <p> Error Code :  PersistenceException => 쿼리문 오류입니다. (reqMap 값 매핑 or 표현 오류)
*					NullPointerException => 요청 값 중에 NULL 값을 참조했습니다.
*					OutOfMemoryError => 배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다.
*					Exception => 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException
*
*
*
* <p> Last Update Date : 2022.11.10
*
* @author MKH
*
*/
@Adapter(trcode = { "RES1300S1" })
public class RES1300S1_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(RES1300S1_Adapter.class);
	@Autowired
	private DBAdapter dbAdapter;

	@SuppressWarnings({ "finally"})
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		/* 전문의 Request Header, Body (전문별 코딩) */
		// Request 객체 생성
		RES1300S1Request 		request		= 	new RES1300S1Request(obj);
		// Request Header, Body 가져오기
		DMHeader 				header		= 	request.getHeader();
		RES1300S1Request_Body	reqBody		=	request.getBody();
		logger.debug("#### RES1300S1 reqBody :{}", reqBody);
		// Response 객체 생성
		RES1300S1Response		response	=	new RES1300S1Response();
		// Response Body 객체 생성
		RES1300S1Response_Body	resBody		=	new RES1300S1Response_Body();


		try {
			// 전문코드 가져오기
			String trCode		=	header.getTrcode();
			// 예약 일련번호 가져오기
			int reserveSeqPK	=	reqBody.getReserveSeqPK();

			// DBMap 객체 생성
			DBMap reqMap		=	new DBMap();
			// DBMap에 userSeqPK 삽입
			reqMap.put("reserveSeqPK", reserveSeqPK);

			// 예약 일련번호(reserveSeqPK)를 통해 reserve 테이블에서 해당 예약 정보 selectOne
			resBody = dbAdapter.selectOne("eduDB", trCode + ".SELECT_RESERVATION_DETAIL", reqMap, RES1300S1Response_Body.class);
			// 응답(실패)
			if(resBody == null) {
				logger.error("#### DB:{}","RES1300S1 SelectOne 결과 없음");
			}
			// 응답(성공)
			else {
				// 예약 일련번호(reserveSeqPK)를 통해 attend 테이블에서 해당 참석자 정보 selectList
				List<RES1300S1Response_Body_attendList> attendList = dbAdapter.selectList("eduDB",
						trCode + ".SELECT_ATTNED_USER_LIST", reqMap, RES1300S1Response_Body_attendList.class);
				// 예약 리스트가 존재할 경우
				if( attendList.size() > 0) {
					logger.debug("#### DB:{}","RES1300S1 참석자 SelectList 결과 있음");

					// 비어있지 않은 attendList 응답
					resBody.setAttendList(attendList);
				}
				// 예약 리스트가 비어있을 경우
				else {
					logger.debug("#### DB:{}","RES1300S1 참석자 SelectList 결과 없음");

                    // 비어있는 attendList 응답
					resBody.setAttendList(attendList);
				}
				// reserve 테이블에서 가져온 예약 장소(reserveRoom), 예약 종료시간(reserveEnd)을 reqMap에 삽입
				reqMap.put("reserveRoom",	resBody.getReserveRoom());
				reqMap.put("reserveEnd",	resBody.getReserveEnd());

				RES1300S1Response_Body checkAvail = dbAdapter.selectOne("eduDB", trCode + ".SELECT_CHECKAVAILABLE", reqMap, RES1300S1Response_Body.class);
				// 해당 예약 바로 뒤에 예약된 회의가 존재하지 않을 경우
				if(checkAvail == null) {
					// 회의 연장 가능 여부 true
					resBody.setCheckAvailable(true);
				}
				// 해당 예약 바로 뒤에 예약된 회의가 존재할 경우
				else {
					// 회의 연장 가능 여부 false
					resBody.setCheckAvailable(false);
				}

//				Push테이블에서 예약 일련번호(reserveSeqPK)에 해당하는 pushId와 pushDate 리스트 Select하기
				List<RES1300S1Response_Body_trxIdList> pushList = dbAdapter.selectList("eduDB",
						trCode + ".SELECT_PUSH_LIST", reqMap, RES1300S1Response_Body_trxIdList.class);
//				응답(실패)
				if(pushList.size()<1) {
					logger.error("#### DB:{}","RES1300S1 pushId, pushDate SelectList 결과  없음");

					// 비어있는 attendList 응답
					resBody.setTrxIdList(pushList);
				}
//				응답(성공)
				else {
					logger.debug("#### DB:{}","RES1300S1 pushId, pushDate SelectList 결과 있음");

					// 비어있지 않은 attendList 응답
					resBody.setTrxIdList(pushList);
				}
				resBody.setFlag(true);
			}
		}
//		예외처리
		catch(PersistenceException e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 쿼리문 오류입니다. (매핑 or 표현 오류) =>"+e.getMessage());
			header.setError_text(header.getTrcode() +"   쿼리문 오류입니다. (매핑 or 표현 오류) ");
		}
		catch(OutOfMemoryError e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다."+e.getMessage());
			header.setError_text(header.getTrcode() +"  배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다. ");
		}
		catch(NullPointerException e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : reserveSeqPK가 NULL 값을 참조했습니다. => "+e.getMessage());
			header.setError_text(header.getTrcode() +" reserveSeqPK가 NULL 값을 참조했습니다.");
		}
		catch(Exception e) {
			logger.error("#### " + header.getTrcode() + "_ERR : 그 외 오류 발생.");
			logger.error("#### " + header.getTrcode() + "_ERR : 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException => " + e);
			header.setError_text(header.getTrcode() +" 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException ");
			return makeFailResponse(header.getTrcode() + "EXCEPTION"," 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException");
		}
		finally{
			response.setHeader(header);
			response.setBody(resBody);
			return makeResponse(obj, response.toJsonNode());
		}
	}

}