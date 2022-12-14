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

import model.RES1320S1.RES1320S1Request;
import model.RES1320S1.RES1320S1Request_Body;
import model.RES1320S1.RES1320S1Response;
import model.RES1320S1.RES1320S1Response_Body;
import model.header.DMHeader;
import service.PushService;
import service.TimeChangeService;

/***
 * <p> Title : 예약 상세 (종료) - RES1320S1
 *
 * <p> Legacy System : BIZMEET DB
 *
 * <p> Description : 예약 상세 화면에서 예약자가 예약을 종료한 뒤, 해당 회의에 참석되는 사람들에게 PUSH알람 취소를 알리는 전문
 *
 *
 * <p> Error Code : PersistenceException => 쿼리문 오류입니다. (reqMap 값 매핑 or 표현 오류)
 * 					NullPointerException => 요청으로 받은 값에서 NULL 값을 참조했습니다.
 * 					ArrayIndexOutOfBoundsException =>  timeservice 함수에서 옳지 않은 배열의 접근을 진행했습니다.
 * 					IllegalArgumentException => timeservice 함수에 잘못된 Parameter를 전송하였습니다.
 *					Exception => 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException
 *
 * <p> Last Update Date : 2022.11.28
 *
 * @author LHY
 *
 */
@Adapter(trcode = { "RES1320S1" })
public class RES1320S1_Adapter extends AbstractTemplateAdapter implements IAdapterJob {
	private static final Logger logger = LoggerFactory.getLogger(RES1320S1_Adapter.class);

	//알림 설정
	@Autowired
	private PushService pushService;

	@Autowired
	private DBAdapter dbAdapter;
	// 시간 변환
	@Autowired
	private TimeChangeService timeService;

	@SuppressWarnings("finally")
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		// 요청 객체 생성
		RES1320S1Request		request		=	new RES1320S1Request(obj);
		// 요청 객체의 헤더 및 바디
		DMHeader				header		=	request.getHeader();
		RES1320S1Request_Body	reqBody		=	request.getBody();
		logger.debug("#### RES1320S1 reqBody :{}", reqBody);

		// 응답 객체 생성
		RES1320S1Response		response	=	new RES1320S1Response();
		RES1320S1Response_Body	resBody		=	new RES1320S1Response_Body();

		try {
			// 요청에서 받은 값(종료 시간, 예약 일련번호, 회원 일련번호, 참석자 리스트, 알람 일련번호 및 날짜, 예약명)
			String trCode		=	header.getTrcode();
			String Hour			=	reqBody.getHour();
			int reserveSeqPK	=	reqBody.getReserveSeqPK();
			int userSeqPK		=	reqBody.getUserSeqPK();
			List<Integer> attendUserSeqList=reqBody.getAttendUserSeqList();
			List<Integer> trxIdList=reqBody.getTrxId();
			String trxDate = reqBody.getTrxDate();
			String reserveName =reqBody.getReserveName();

			// DBMap에 예약 일련번호(reserveSeqPK), 회원 일련번호(userSeqPK) 저장
			DBMap 	reqMap	=	new DBMap();
			reqMap.put("reserveSeqPK",	reserveSeqPK);
			reqMap.put("userSeqPK",		userSeqPK);

//			-----------------------------------------------------------------------------------------------------------
//			예약 종료
//			-----------------------------------------------------------------------------------------------------------
			// Reserve 테이블에서 예약 일련번호(reserveSeqPK)와 회원 일련번호(userSeqPK)에
			// 해당하는 예약내역 selectOne으로 받아오기
			RES1320S1Response_Body reserveResult = dbAdapter.selectOne("eduDB", trCode + ".SELECT_TIME_INFO", reqMap, RES1320S1Response_Body.class);

			// 응답(실패)
			if(reserveResult == null) {
				logger.error("#### DB:{}","RES1320S1 예약내역 SelectOne 결과 없음");
				logger.error("#### " + header.getTrcode() + "_SeqERR : userSeq 또는 reserveSeq가 잘못된 값이거나 Null 입니다.");

				resBody.setFlag(false);
				resBody.setReserveEndCheck("예약 종료 실패");
			}
			// 응답(성공)
			else {
				// 취소하려는 예약내역의 종료시간
				String reserveEnd = reserveResult.getReserveEnd();
				// 취소하려는 예약내역의 시간 수정
				reserveEnd = timeService.makeTimeChange(reserveEnd,Hour);
				reqMap.put("reserveEnd", reserveEnd);

				// Reserve 테이블에서 해당 예역 내역 시간과 예약 종료여부 Update
				int timeUpdateResult = dbAdapter.update("eduDB", trCode + ".UPDATE_TIME_INFO", reqMap);
				// 응답(실패)
				if(timeUpdateResult<1) {
					logger.error("#### DB:{}", "RES1320S1 update 실패");

					resBody.setFlag(false);
					resBody.setReserveEndCheck("예약 종료 실패");
				}
				// 응답(성공)
				else {
					logger.debug("#### DB:{}", "RES1320S1 update 성공");

					resBody.setFlag(true);
					resBody.setReserveEndCheck("해당 회의는 " + reserveEnd + "에 종료하는 것으로 바뀌었습니다.");
				}

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
				pushService.sendPost(attendUserSeqList, "INSTANT", "", "",reserveName, reserveSeqPK,"회의가 종료되었습니다.");
			}

		}
		// 예외처리
		catch(PersistenceException e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 쿼리문 오류입니다. (매핑 or 표현 오류) =>"+e.getMessage());
			header.setError_text(header.getTrcode() +"  쿼리문 오류입니다. (매핑 or 표현 오류) ");
		}
		catch(ArrayIndexOutOfBoundsException e) {
			logger.error("#### " + header.getTrcode() + "_ArrayIndexOutOfBoundsException : 옳지 않은 배열의 접근입니다. "+e.getMessage());
			header.setError_text(header.getTrcode() +"  timeservice 함수에서 옳지 않은 배열의 접근을 진행했습니다.");
		}
		catch (IllegalArgumentException e) {
			logger.error("#### " + header.getTrcode() + "_IllegalArgumentException : 잘못된 Parameter를 전송하였습니다. "+e.getMessage());
			header.setError_text(header.getTrcode() +" timeservice함수에 잘못된 Parameter를 전송하였습니다. ");
		}
		catch(NullPointerException e) {
			logger.error("#### " + header.getTrcode() + "_NullPointerException : 요청으로 받은 값에서 NULL 값을 참조했습니다. "+e.getMessage());
			header.setError_text(header.getTrcode() +"  요청으로 받은 값에서 NULL 값을 참조했습니다.");
		}
		catch(Exception e){
			logger.error("#### " + header.getTrcode() + "_ERR : 그 외 오류 발생.");
			logger.error("#### " + header.getTrcode() + "_ERR : 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException => " + e);
			header.setError_text(header.getTrcode() +" 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException");
			return makeFailResponse(header.getTrcode() + "EXCEPTION"," 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException");
		}
		finally {
			response.setHeader(header);
			response.setBody(resBody);
			return makeResponse(obj,response.toJsonNode());
		}
	}

}
