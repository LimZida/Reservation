package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import adapter.RES1100S1_Adapter;
/***
*
* <p> Title : pushService
*
*
* <p> Description : makeDate(String date, String time) => 시간 변환 함수
*					sendPost(List<Integer> userList, String trxType, String date, String check, String instantMessage) => 예약 알림 요청 함수
*					DeletePushKey(String pushKey) => 해당 pushKey를 가진 사용자의 예약 알림 요청 삭제 함수
*					cancelPushAlarm(String trxDay, Integer trxId) => 해당 trxDay와 trxId를 가진 알람 취소
*
* <p> Last Update Date : 2022.11.28
*
*
* @author LHY
*
*/
@Component
public class PushService {

	private static final Logger logger = LoggerFactory.getLogger(RES1100S1_Adapter.class);

//	시간 변환
//	ex) date=2022-11-08 time=11:00이면,
//  "yyyyMMddHHmmssSSS"인 "20221108110000000"로 변환
	public String makeDate(String date, String time) {
//		"yyyyMMddHHmmssSSS"로 변환 될 변수 changedDate
		String changedDate = "";

//		ex) date=2022-11-08 => ["2022","11","08"]
//			time=11:00 => ["11","00"]
		String[] originalDate = date.split("-");
		String[] originalTime = time.split(":");

//		날짜를 changedDate에 넣기
//		ex) 20221108
		for (String i : originalDate) {
			changedDate += i;
		}

//		ex)시간이 12:00인 경우
		if (originalTime[1].equals("00")) {
//			11:50분으로 표기해야함
			int hour = Integer.parseInt(originalTime[0]);
			hour -= 1;
//			만약 시간이 09:00인 경우 hour가 09가 아닌 9로 parseInt 되기 때문에, String으로 변환 과정에서 맨 앞에 "0"을 추가해주어야,
//			"yyyyMMddHHmmssSSS" 양식을 만족한다.
			if(hour<10) {
				originalTime[0] = "0"+Integer.toString(hour);
			}
//			시간이 10:00 이상의 경우
			else {
				originalTime[0] = Integer.toString(hour);
			}
			originalTime[1] = "50";
		}
//		ex)시간이 13:30인 경우
		else if (originalTime[1].equals("30")) {
//		13:20분으로 표기해야함
			originalTime[1] = "20";
		}

//		변환된 시간을 changedDate에 넣기
//		ex)1320
		for (String i : originalTime) {
			changedDate += i;
		}

//		초&밀리초
		changedDate += "00000";

		return changedDate;
	}

//	예약 알림
	@SuppressWarnings("unchecked")
	public JSONObject sendPost(List<Integer> userList, String trxType, String date, String check,
		String reserveName, Integer reserveSeqPK, String instantMessage) {
		try {
//			헤더 생성
			HttpHeaders headers = new HttpHeaders();

//      	POST 보낼 BODY JSON 객체 생성
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("trxType", trxType);
			jsonObject.put("appName", "BizMeet");
			jsonObject.put("toUsers", userList);
			jsonObject.put("toAll", false);
			jsonObject.put("fromUser", "BizMeet");
			jsonObject.put("messageSubject", '"'+reserveName+'"');

//			취소된 회의면,
//			해당 예약 일련번호(reserveSeqPK)로 예약된 회의가 없으므로 def로 설정
			if(instantMessage.equals("회의가 취소되었습니다.")||instantMessage.equals("회의가 종료되었습니다.")) {
				jsonObject.put("messageCategory", "def");
			}
//			취소되지 않은 회의면,
//			해당 예약 일련번호(reserveSeqPK)로 예약된 회의가 있으므로 reserveSeqPK로 설정
			else {
				jsonObject.put("messageCategory", reserveSeqPK);
			}

//			trxType가 "SCHEDULE"일 경우에, 즉시 알림이 아닌 예약 알림
			if (!check.equals("") && !date.equals("") && trxType.equals("SCHEDULE")) {
				jsonObject.put("scheduleDate", date);
//	    		예약 시 회의 시작 10분 전 알림
				if (check.equals("start")) {
					//현재 시간
					Date nowDate = new Date();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					String strNowDate = simpleDateFormat.format(nowDate);
					if(strNowDate.compareTo(date)>=0) {
						logger.debug("#### Push알림 :{}","보내려는 시간이 현재 시간보다 작으므로 회의 시작 10분 전 푸시 요청 보내지 않음");
						return null;
					}
					else {
						jsonObject.put("messageContent", "회의 시작 10분 전 입니다.");
						logger.debug("#### Push알림 :{}","회의 시작 10분 전 푸시 요청");
					}
				}
//	    		예약 시 회의 종료 10분 전 알림
				else if (check.equals("end")) {
					jsonObject.put("messageContent", "회의 종료 10분 전 입니다.");
					logger.debug("#### Push알림 :{}","회의 종료 10분 전 푸시 요청");
				}
			}
//			trxType가 "INSTANT"일 경우에, 예약 알림이 아닌 즉시 알림
			else if(check.equals("") && date.equals("") && trxType.equals("INSTANT")) {
				jsonObject.put("messageContent", instantMessage);
				logger.debug("#### Push알림 :{}","실시간 푸시 요청");
			}
//			두가지 경우가 아니라면 error
			else {
				logger.error("#### Push알림 :{}","PushService sendPost INSTANT, SCHEDULE 알람 판별 중 if문 예외 발생");
				throw new Exception();
			}

//	    	헤더와 BODY 병합
			HttpEntity<JSONObject> entity = new HttpEntity<>(jsonObject, headers);

//	   		POST 요청
			RestTemplate rt = new RestTemplate();
			ResponseEntity<JSONObject> response = rt.exchange(
					"http://192.168.1.49:8080/bizpush/v150/push/messages/fireMessages", HttpMethod.POST, // 요청할 방식
					entity, // 요청할 때 보낼 데이터
					JSONObject.class // 요청시 반환되는 데이터 타입
			);

			logger.debug("#### 응답 결과:{}",response);
			return response.getBody();
		}
//		예외 및 에러
		catch (HttpClientErrorException | HttpServerErrorException e) {
			logger.error("#### Push알림 :{}","sendPost 함수에서 통신 오류가 발생했습니다."+e.getClass().getName()+" "+e.getMessage());
			return null;
		}
		catch (Exception e) {
			logger.error("#### Push알림 :{}","sendPost 함수 예외가 발생했습니다."+e.getClass().getName()+" "+e.getMessage());
			return null;
		}
	}

//	예약 알림 삭제(로그아웃 시)
	public String DeletePushKey(String pushKey) {
		try {
//			헤더 생성
			HttpHeaders headers = new HttpHeaders();

			// POST로 보낼 LIST 객체 생성
			List<String> list = new ArrayList<>();
			// 삭제할 pushKey 배열에 추가
			list.add(pushKey);

//		    헤더와 LIST 병합
			HttpEntity<List<String>> entity = new HttpEntity<>(list, headers);

//		    POST 요청
			RestTemplate rt = new RestTemplate();
			ResponseEntity<String> response = rt.exchange(
			"http://192.168.1.49:8080/bizpush//v150/push/pushkeys/removePushKeysByPushKeys", HttpMethod.POST, // 요청할 방식
			entity, // 요청할 때 보낼 데이터
			String.class // 요청시 반환되는 데이터 타입
			);

			logger.debug("#### 응답 결과:{}",response);
			return response.getBody();
		}
//			예외 및 에러
		catch (HttpClientErrorException | HttpServerErrorException e) {
			logger.error("#### Push알림 :{}","DeletePushKey 함수에서 통신 오류가 발생했습니다."+e.getClass().getName()+" "+e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("#### Push알림 :{}","DeletePushKey 함수 예외가 발생했습니다."+e.getClass().getName()+" "+e.getMessage());
			return null;
		}
	}

//	예약 푸시 알림 취소
	@SuppressWarnings("unchecked")
	public void cancelPushAlarm(String trxDay, Integer trxId) {
		try {
//			헤더 생성
			HttpHeaders headers = new HttpHeaders();

//      	POST 보낼 BODY JSON 객체 생성
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("trxDay", trxDay);
			jsonObject.put("trxId", trxId);

//	    	헤더와 BODY 병합
			HttpEntity<JSONObject> entity = new HttpEntity<>(jsonObject, headers);

//	   		 POST 요청
			RestTemplate rt = new RestTemplate();
			ResponseEntity<String> response = rt.exchange(
					"http://192.168.1.49:8080/bizpush/v150/push/messages/cancelReservedPushMessage", HttpMethod.POST, // 요청할 방식
					entity, // 요청할 때 보낼 데이터
					String.class // 요청시 반환되는 데이터 타입
			);

			logger.debug("#### Push알림 응답 결과:{}",response);
		}
//		예외 및 에러
		catch (HttpClientErrorException | HttpServerErrorException e) {
			logger.error("#### Push알림 :{}","cancelPushAlarm 함수에서 통신 오류가 발생했습니다."+e.getClass().getName()+" "+e.getMessage());
		}
		catch (Exception e) {
			logger.error("#### Push알림 :{}","cancelPushAlarm 함수 예외가 발생했습니다."+e.getClass().getName()+" "+e.getMessage());
		}
	}
}