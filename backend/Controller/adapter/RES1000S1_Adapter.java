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

import model.RES1000S1.RES1000S1Request;
import model.RES1000S1.RES1000S1Request_Body;
import model.RES1000S1.RES1000S1Response;
import model.RES1000S1.RES1000S1Response_Body;
import model.RES1000S1.RES1000S1Response_Body_reserveList;
import model.header.DMHeader;

/***
*
* <p> Title : 메인화면 - 회의실별 예약현황, 예약조회 - RES1000S1
*
* <p> Legacy System : BIZMEET DB
*
* <p> Description : 메인 화면과 예약 조회 화면에서 회의실별 실시간 전체 예약 내역을 조회하기 위해 응답
*
* <p> Error Code :  PersistenceException => 쿼리문 오류입니다. (reqMap 값 매핑 or 표현 오류)
* 					NullPointerException => 요청으로 받은 값에서 NULL 값을 참조했습니다.
*					OutOfMemoryError => 배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다.
*					Exception => 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException
*
*
*
* <p> Last Update Date : 2022.11.21
*
* @author LHY
*
*/
@Adapter(trcode = { "RES1000S1" })
public class RES1000S1_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(RES1000S1_Adapter.class);
	@Autowired
	private DBAdapter dbAdapter;

	@SuppressWarnings("finally")
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
//      요청 객체 생성
		RES1000S1Request request=new RES1000S1Request(obj);
//		요청 객체의 헤더 및 바디
		DMHeader header= request.getHeader();
		RES1000S1Request_Body reqBody=request.getBody();
		logger.debug("#### RES1000S1 reqBody :{}", reqBody);

//      응답 객체 생성
		RES1000S1Response response=new RES1000S1Response();
		RES1000S1Response_Body resBody=new RES1000S1Response_Body();
		try {
			String trCode=header.getTrcode();
//			요청에서 받은 값(날짜, 회의실)
			String Date=reqBody.getDate();
			String reserveRoom=reqBody.getReserveRoom();

//			DBMap에 요청에서 받은 값 저장
			DBMap 		reqMap= new DBMap();
			reqMap.put("Date", Date);
			reqMap.put("reserveRoom", reserveRoom);

//			강의실별 예약 내역 List로 받아오기
			@SuppressWarnings("unchecked")
			List<RES1000S1Response_Body_reserveList>resultList =
			(List<RES1000S1Response_Body_reserveList>) dbAdapter.selectList("eduDB", trCode + ".SELECT_RESERVE_INFO_ALL", reqMap);

//			List 결과가 없을 경우
//		    예약 내역 없이 응답
			if(resultList.size()<=0) {
				logger.debug("#### DB:{}","RES1000S1 해당 회의실 예약 내역 없음");

//				비어있는 List 내역 전송
				resBody.setReserveList(resultList);
			}
//			List 결과가 있을 경우
//			존재하는 예약 내역 응답
			else {
				logger.debug("#### DB:{}","RES1000S1 해당 회의실 예약 내역 존재");

//				selectList에서 받아온 회의실별 실시간 전체 예약 내역 전송
				resBody.setReserveList(resultList);
			}
		}
//		예외처리
		catch(PersistenceException e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 쿼리문 오류입니다. (매핑 or 표현 오류) =>"+e.getMessage());
			header.setError_text(header.getTrcode() +"  쿼리문 오류입니다. (매핑 or 표현 오류)");
		}
		catch(NullPointerException e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 요청으로 받은 값 중에 NULL 값을 참조했습니다. => "+e.getMessage());
			header.setError_text(header.getTrcode() +"  요청으로 받은 값 중에 NULL 값을 참조했습니다. ");
		}
		catch(OutOfMemoryError e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다. =>"+e.getMessage());
			header.setError_text(header.getTrcode() +"  배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다. ");
		}
		catch(Exception e) {
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
