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

import model.RES1330S1.RES1330S1Request;
import model.RES1330S1.RES1330S1Request_Body;
import model.RES1330S1.RES1330S1Response;
import model.RES1330S1.RES1330S1Response_Body;
import model.header.DMHeader;
import service.PushService;
/***
*
* <p> Title : 예약 상세 (취소) - RES1330S1
*
* <p> Legacy System : DB
*
* <p> Description : 예약 상세에서 예약이 종료되면 해당 예약을 취소하여 그 예약을 삭제함
* 					-> INSTANT로 기존 예약자들에게 "예약이 취소되었습니다."  알림
*					-> 요청받은 푸시 예약날짜와 푸시 일련번호에 해당되는 알람 삭제
*
*
* <p> Error Code :  PersistenceException => 쿼리문 오류입니다. (매핑 or 표현 오류)
*					NullPointerException => 요청 값 중에 NULL 값을 참조했습니다.
*					Exception => 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException
*
* <p> Last Update Date : 2022.11.10
*
* @author MKH
*
*/
@Adapter(trcode = { "RES1330S1" })
public class RES1330S1_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(RES1330S1_Adapter.class);
	//알림 설정
	@Autowired
	private PushService pushService;

	@Autowired
	private DBAdapter dbAdapter;

	@SuppressWarnings("finally")
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		// Request 객체 생성 후 Request Header, Body 가져오기
		RES1330S1Request		request	=	new RES1330S1Request(obj);
		DMHeader				header	=	request.getHeader();
		RES1330S1Request_Body	reqBody	=	request.getBody();
		logger.debug("#### RES1330S1 reqBody :{}", reqBody);


		// Response, Response_Body 객체 생성
		RES1330S1Response		response	=	new RES1330S1Response();
		RES1330S1Response_Body	resBody		=	new RES1330S1Response_Body();

		try {
//			요청으로 받은 회원 일련번호, 예약 일련번호, 참석자 일련번호 List, 푸시 예약 일련번호 List, 예약명, 푸시 예약 날짜
			String trCode		=	header.getTrcode();
			int userSeqPK		=	reqBody.getUserSeqPK();
			int reserveSeqPK	=	reqBody.getReserveSeqPK();
			List<Integer> attendUserSeqList=reqBody.getAttendUserSeqList();
			List<Integer> trxIdList=reqBody.getTrxId();
			String trxDate = reqBody.getTrxDate();
			String reserveName =reqBody.getReserveName();

			DBMap reqMap	=	new DBMap();

			reqMap.put("userSeqPK",		userSeqPK);
			reqMap.put("reserveSeqPK",	reserveSeqPK);

//			-----------------------------------------------------------------------------------------------------------
//			예약 삭제
//			-----------------------------------------------------------------------------------------------------------

//			reserve 테이블에서 userSeqPK와 reserveSeqPK에 해당하는 예약 삭제
			int deleteResult = dbAdapter.delete("eduDB", trCode + ".DELETE_RESERVATION", reqMap);
//			응답(실패)
			if(deleteResult < 1) {
				logger.error("#### RES1330S1 deleteResult :{}", "예약 취소 실패");
				logger.error("#### " + header.getTrcode() + "_SeqERR : reserveSeqPK 또는 userSeqPK 값이 존재하지 않거나 null 입니다.");
				resBody.setFlag(false);
			}
//			응답(성공)
			else {
				logger.debug("#### RES1330S1 deleteResult :{}", "예약 취소 성공");
				resBody.setFlag(true);

//          -----------------------------------------------------------------------------------------------------------
//			푸시 알람 삭제
//			-----------------------------------------------------------------------------------------------------------

//				Push 알림을 보낼 참석자 일련번호 List에 예약자 일련번호 삽입
				attendUserSeqList.add(userSeqPK);


//				요청에서 받은 푸시 알람 일련번호 List 순회
				for(int i:trxIdList) {
//					해당 푸시 알람 취소
					pushService.cancelPushAlarm(trxDate, i);
				}

//				예약 실시간 취소 알림
				pushService.sendPost(attendUserSeqList, "INSTANT", "", "",reserveName, reserveSeqPK,"회의가 취소되었습니다.");
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
		catch(Exception e){
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
