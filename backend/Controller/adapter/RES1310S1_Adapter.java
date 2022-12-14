package adapter;

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

import model.RES1310S1.RES1310S1Request;
import model.RES1310S1.RES1310S1Request_Body;
import model.RES1310S1.RES1310S1Response;
import model.RES1310S1.RES1310S1Response_Body;
import model.header.DMHeader;
/***
 *
 * <p> Title : 예약 상세 (연장) - RES1310S1
 *
 * <p> Legacy System : DB
 *
 * <p> Description : 회의를 연장할 경우 진행 중인 회의 뒤에 예약된 회의 시간을 조회하여,
 * 					 진행 중인 회의 뒤에 회의가 비었을 경우 회의를 30분 연장하고,
 * 					 회의가 존재할 경우 회의 연장을 하지 못함
 *
 * <p> Error Code : RES1310S1_NullPointerException		: reserveRoom 또는 reserveDate가 null일 경우 발생
 * 					RES1310S1_PersistenceException		: reserveEnd가 Null 혹은 쿼리문 오류
 * 					RES1310S1_ERR						: 그 외 오류 발생 시
 *
 * <p> Last Update Date : 2022.11.10
 *
 * @author MKH
 *
 */
@Adapter(trcode = { "RES1310S1" })
public class RES1310S1_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(RES1310S1_Adapter.class);
	@Autowired
	private DBAdapter dbAdapter;

	@SuppressWarnings("finally")
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		// Request 객체 생성
		RES1310S1Request 			request		=	new RES1310S1Request(obj);
		// Request Header, Body 객체 생성
		DMHeader 					header		=	request.getHeader();
		RES1310S1Request_Body 		reqBody		=	request.getBody();

		// Response , Body 객체 생성
		RES1310S1Response		response	=	new RES1310S1Response();
		RES1310S1Response_Body	resBody		=	new RES1310S1Response_Body();

		try {
			// 전문코드 가져오기
			String trCode 		=	header.getTrcode();

			// 회원 & 예약 일련번호, 회의 종료시간, 회의실명, 회의시간 가져오기
			int userSeqPK 		= 	reqBody.getUserSeqPK();
			int reserveSeqPK	=	reqBody.getReserveSeqPK();
			String reserveEnd	=	reqBody.getReserveEnd();
			String reserveRoom	=	reqBody.getReserveRoom();
			String reserveDate	=	reqBody.getReserveDate();

			// reserveRoom이나 reserveDate 값이 비어있었거나 null일 경우
			if(reserveRoom.equals("") || reserveRoom == null
					|| reserveDate.equals("") || reserveDate == null) {
				// NullPointException으로 catch
				throw new NullPointerException();
			}

			// DBMap 객체 생성
			DBMap reqMap		= 	new DBMap();

			// DBMap에 사용자 정보 삽입
			reqMap.put("userSeqPK", 		userSeqPK);
			reqMap.put("reserveSeqPK", 		reserveSeqPK);
			reqMap.put("reserveEnd", 		reserveEnd);
			reqMap.put("reserveRoom", 		reserveRoom);
			reqMap.put("reserveDate", 		reserveDate);

			// updateResult에 RES1310S1.xml을 통한 UPDATE_MY_INFO 쿼리문으로 해당 Response를 가져와 DB 매핑
			int updateResult = dbAdapter.update("eduDB", trCode + ".UPDATE_EXTENSION" , reqMap);

			// 수정 실패 시
			if(updateResult < 1) {
				// 수정 실패시 수정 성공 여부를 'false'로 지정
				logger.error("#### RES1310S1 :{}","updateResult 결과 실패" );
				logger.error("#### " + header.getTrcode() + "_SeqERR : userSeq 또는 reserveSeq가 잘못된 값이거나 Null 입니다.");
				resBody.setFlag(false);
			}
			// 수정 성공 시
			else {
				// 수정 성공시 수정 성공 여부를 'true'로 지정
				logger.debug("#### RES1310S1 :{}","updateResult 결과 성공" );
				resBody.setFlag(true);
			}
		}
//		예외처리
		catch(NullPointerException e) {
			logger.error("#### " + header.getTrcode() + "_NullPointerException : reserveRoom 또는 Date가 Null입니다. "+ e.getMessage());
			header.setError_text(header.getTrcode() +" reserveRoom 또는 Date가 Null입니다. ");
		}
		catch(PersistenceException e) {
			logger.error("#### " + header.getTrcode() + "_PersistenceException : reserveEnd가 Null 혹은 쿼리문 오류입니다. (매핑 or 표현 오류) "+ e.getMessage());
			header.setError_text(header.getTrcode() +" reserveEnd가 Null 혹은 쿼리문 오류입니다. (매핑 or 표현 오류) ");
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
			return makeResponse(obj, response.toJsonNode());
		}
	}

}