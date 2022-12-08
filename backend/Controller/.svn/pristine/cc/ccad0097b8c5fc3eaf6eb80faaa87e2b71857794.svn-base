package adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.db.type.DBMap;
import com.mcnc.bizmob.hybrid.adapter.api.Adapter;
import com.mcnc.bizmob.hybrid.adapter.api.IAdapterJob;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;

import model.RES1100S1.RES1100S1Request;
import model.RES1100S1.RES1100S1Request_Body;
import model.RES1100S1.RES1100S1Response;
import model.RES1100S1.RES1100S1Response_Body;
import model.RES1100S1.RES1100S1Response_Body_reserveList;
import model.header.DMHeader;
import service.PushService;

/***
*
* <p> Title : 예약하기 - RES1100S1
*
* <p> Legacy System : BIZMEET DB
*
* <p> Description : 예약 화면에서 사용자들이 회의를 예약하면 예약 테이블에 해당 정보를 삽입 후,
*
* 실시간, 회의 시작 10분 전(단, 현재 시간 기준으로, 회의 시작 10분 전이 과거일 경우에는 알림 X), 회의 종료 10분 전 Push 알림
*
* <p> Error Code :  PersistenceException => 쿼리문 오류입니다. (값 매핑 or 표현 오류)
*					NullPointerException => 요청으로 받은 값이나 pushService 함수의 매개변수 중에 NULL 값을 참조했습니다.
*					OutOfMemoryError => 배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다.
*					IndexOutOfBoundsException =>  makeDate 함수의 매개변수 `time`에서 정상적이지 않은 값을 받아 인덱스 범위 오류가 발생했습니다.
*					Exception => 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException
*
*
* <p> Last Update Date : 2022.11.28
*
* @author LHY
*
*/

@Adapter(trcode = { "RES1100S1" })
public class RES1100S1_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(RES1100S1_Adapter.class);
	//알림 설정
	@Autowired
	private PushService pushService;

	@Autowired
	private DBAdapter dbAdapter;

	@SuppressWarnings("finally")
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
//      요청 객체 생성
		RES1100S1Request request=new RES1100S1Request(obj);
//		요청 객체의 헤더 및 바디
		DMHeader header=request.getHeader();
		RES1100S1Request_Body reqBody=request.getBody();
		logger.debug("#### RES1100S1 reqBody :{}", reqBody);

//      클라이언트에 응답 보낼 Response
		RES1100S1Response response=new RES1100S1Response();
		RES1100S1Response_Body resBody=new RES1100S1Response_Body();
		try {
			String trCode=header.getTrcode();
//			요청에서 받은 값(예약 날짜, 시작 종료시간, 예약명, 장소, 일련번호, 참석자 리스트)
			List<Integer> attendUserSeqList=reqBody.getAttendUserSeqList();
			String reserveDate=reqBody.getReserveDate();
			String reserveEnd=reqBody.getReserveEnd();
			String reserveName=reqBody.getReserveName();
			String reserveRoom=reqBody.getReserveRoom();
			String reserveStart=reqBody.getReserveStart();
			int userSeqPK=reqBody.getUserSeqPK();

//			DBMap에 요청받은 변수 설정
			DBMap 		reqMap= new DBMap();
			reqMap.put("reserveDate", reserveDate);
			reqMap.put("reserveEnd", reserveEnd);
			reqMap.put("reserveName", reserveName);
			reqMap.put("reserveRoom", reserveRoom);
			reqMap.put("reserveStart",reserveStart);
			reqMap.put("userSeqPK", userSeqPK);

//			-----------------------------------------------------------------------------------------------------------
//			예약 하기
//			-----------------------------------------------------------------------------------------------------------
			@SuppressWarnings("unchecked")
//			예약이 가능한지 (시간대가 겹치지 않는지) 확인
			List<RES1100S1Response_Body_reserveList>reserveTimeResult =
			(List<RES1100S1Response_Body_reserveList>) dbAdapter.selectList("eduDB", trCode + ".SELECT_RESERVE_TIME_CHECK", reqMap);
//			예약이 가능하면
			if(reserveTimeResult.size() == 0) {
//				Reserve 테이블에 예약 정보(reserveDate....userSeqFk) 삽입
				int insertResult = dbAdapter.insert("eduDB", trCode + ".INSERT_RESERVE_INFO", reqMap);
//				응답(실패)
				if(insertResult<1) {
					logger.error("#### RES1100S1 DB:{}","reserve table insert 실패");
					resBody.setFlag(false);
					resBody.setReserveSeqPK(-1);
				}
//				응답(성공)
				else {
//					Reserve 테이블에서 예약 일련번호(reserveSeqPK)를 selectOne으로 받아오기
					RES1100S1Response_Body reserveSeqResult = dbAdapter.selectOne("eduDB", trCode + ".SELECT_RESERVE_INFO", reqMap,RES1100S1Response_Body.class);
//					응답(실패)
					if(reserveSeqResult==null) {
						logger.error("#### DB:{}","RES1100S1 예약 일련번호 SelectOne 결과 없음");
						resBody.setFlag(false);
						resBody.setReserveSeqPK(-1);
					}
//					응답(성공)
					else {
//						현재 Reserve 테이블에 해당하는 예약 일련번호(reserveSeqPK)와 참석자 List
						int reserveSeqPK=reserveSeqResult.getReserveSeqPK();
						resBody.setReserveSeqPK(reserveSeqPK);
						reqMap.put("reserveSeqPK",reserveSeqPK);

//						참석자 리스트가 있으면
						if(attendUserSeqList.size()!=0) {
//							요청에서 받은 참석자 List를 reqMap에 설정
							reqMap.put("attendUserSeqList", attendUserSeqList);

//							Attend 테이블에 reserveSeqPK와 그에 해당하는 참석자 일련번호삽입
							int attendInsertResult = dbAdapter.insert("eduDB", trCode + ".INSERT_ATTEND_INFO", reqMap);
//							응답(실패)
							if(attendInsertResult<1) {
								logger.error("#### DB:{}","RES1100S1 참석자 정보 insert 실패");
								resBody.setFlag(false);
							}
//							응답(성공)
							else {
								logger.debug("#### DB:{}","RES1100S1 참석자 정보 insert 성공");
								resBody.setFlag(true);
							}
						}
//					-----------------------------------------------------------------------------------------------------------
//					푸시 알람 등록
//					-----------------------------------------------------------------------------------------------------------

//						Push 알림을 보낼 참석자 일련번호 List에 예약자 일련번호 삽입
						attendUserSeqList.add(userSeqPK);

//						PUSH API에 요청해서 받은 JSON 응답에서 회의 시작 10분 전 / 종료 10분 전,
//						각각의 trxId와 해당 날짜의 trxDate 가져오기
						List<Integer> trxIdArr=new ArrayList<>();

//						회의 시작 10분 전 알림
						String startTime=pushService.makeDate(reserveDate, reserveStart);
//						JSON으로 결과 받아서 body에 있는 trxId, trxDate 뽑아내기
						JSONObject startJSON=pushService.sendPost(attendUserSeqList, "SCHEDULE", startTime, "start",reserveName,reserveSeqPK,"");
//						미래의 시간에 10분 전 알림을 보낸 경우
						if(startJSON!=null) {
							LinkedHashMap<?, ?> startResult=(LinkedHashMap<?, ?>) startJSON.get("body");
							trxIdArr.add((Integer) startResult.get("trxId"));
						}

//						회의 종료 10분 전 알림
						String endTime=pushService.makeDate(reserveDate, reserveEnd);
//						JSON으로 결과 받아서 body에 있는 trxId, trxDate 뽑아내기
						JSONObject endJSON = pushService.sendPost(attendUserSeqList, "SCHEDULE", endTime, "end",reserveName,reserveSeqPK,"");
						LinkedHashMap<?, ?> endResult=(LinkedHashMap<?, ?>) endJSON.get("body");
						trxIdArr.add((Integer) endResult.get("trxId"));

						logger.debug("#### RES1100S1 회의 시작 10분 전 알람 전송 결과:{}",startJSON);
						logger.debug("#### RES1100S1 회의 종료 10분 전 알람 전송 결과:{}",endJSON);


//						reqMap에 trxDate와 trxIdArr 할당
						reqMap.put("pushTrxDate", endResult.get("trxDate"));
						reqMap.put("trxIdArr", trxIdArr);

//						Push 테이블에 reserveSeqPK에 해당하는 Push 일련번호와 Push 날짜 삽입 (회의 시작 10분 전, 종료 10분 전)
						int pushStartResult = dbAdapter.insert("eduDB", trCode + ".INSERT_PUSH_INFO", reqMap);
//						응답(실패)
						if(pushStartResult<1){
							logger.error("#### RES1100S1 Push:{}","RES1100S1 Push 테이블 trxDate, trxId insert 실패");
						}
//						응답(성공)
						else{
							logger.debug("#### RES1100S1 Push:{}","RES1100S1 Push 테이블 trxDate, trxId insert성공");
						}

//						예약 실시간 알림
						pushService.sendPost(attendUserSeqList, "INSTANT", "", "",reserveName,reserveSeqPK,"회의가 예약되었습니다.");
						resBody.setFlag(true);
					}
				}
			}
//			예약이 불가능하면
			else {
				logger.debug("#### DB:{}","RES1100S1 이미 예약이 존재");
				resBody.setFlag(false);
				resBody.setReserveSeqPK(-10);
			}

		}
//		예외처리
		catch(PersistenceException e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 쿼리문 오류입니다. (매핑 or 표현 오류) =>"+e.getMessage());
			header.setError_text(header.getTrcode() +"  쿼리문 오류입니다. (매핑 or 표현 오류) ");
		}
		catch(NullPointerException e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 요청으로 받은 값이나 pushService 함수의 매개변수 중에 NULL 값을 참조했습니다. => "+e.getMessage());
			header.setError_text(header.getTrcode() +"  요청으로 받은 값이나 pushService 함수의 매개변수 중에 NULL 값을 참조했습니다. ");
		}
		catch(IndexOutOfBoundsException e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : makeDate 함수의 매개변수 `time`에서 정상적이지 않은 값을 받아 인덱스 범위 오류가 발생했습니다.=> "+e.getMessage());
			header.setError_text(header.getTrcode() +"  makeDate 함수의 매개변수 `time`에서 정상적이지 않은 값을 받아 인덱스 범위 오류가 발생했습니다. ");
		}
		catch(OutOfMemoryError e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다. =>"+e.getMessage());
			header.setError_text(header.getTrcode() +"  배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다. ");
		}
		catch(Exception e) {
			logger.error("#### " + header.getTrcode() + "_ERR : 그 외 오류 발생.");
			logger.error("#### " + header.getTrcode() + "_ERR : 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException => " + e);
			header.setError_text(header.getTrcode() +"  어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException ");
			return makeFailResponse(header.getTrcode() + "EXCEPTION"," 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException");
		}
		finally {
			response.setHeader(header);
			response.setBody(resBody);
			return makeResponse(obj,response.toJsonNode());
		}
	}

}
