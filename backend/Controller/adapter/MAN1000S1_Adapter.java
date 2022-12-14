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

import model.MAN1000S1.MAN1000S1Request;
import model.MAN1000S1.MAN1000S1Request_Body;
import model.MAN1000S1.MAN1000S1Response;
import model.MAN1000S1.MAN1000S1Response_Body;
import model.MAN1000S1.MAN1000S1Response_Body_reserveList1;
import model.MAN1000S1.MAN1000S1Response_Body_reserveList2;
import model.MAN1000S1.MAN1000S1Response_Body_reserveList3;
import model.header.DMHeader;

/***
 *
 * <p>
 * Title : 메인 페이지 - 해당 회원 예약 내역 - MAN1000S1
 *
 * <p>
 * Legacy System : BIZMEET DB
 *
 * <p>
 * Description : 메인 화면에서 회원 이름, 해당 회원이 포함된 당일 예약 내역을 응답
 *
 * <p>
 * Error Code :		PersistenceException => 쿼리문 오류입니다. (reqMap 값 매핑 or 표현 오류)
 * 					NullPointerException => 요청으로 받은 값에서 NULL 값을 참조했습니다.
 *					Exception => 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException
 *
 *
 * <p>
 * Last Update Date : 2022.11.21
 *
 * @author LHY
 *
 */

@Adapter(trcode = { "MAN1000S1" })
public class MAN1000S1_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(MAN1000S1_Adapter.class);
	@Autowired
	private DBAdapter dbAdapter;

	@SuppressWarnings("finally")
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		// 요청 객체 생성
		MAN1000S1Request		request		=	new MAN1000S1Request(obj);
		// 요청 객체의 헤더 및 바디
		DMHeader				header		=	request.getHeader();
		MAN1000S1Request_Body	reqBody		=	request.getBody();
		logger.debug("####MAN1000S1 reqBody :{}", reqBody);

		// 응답 객체 생성
		MAN1000S1Response		response	=	new MAN1000S1Response();
		MAN1000S1Response_Body 	resBody		=	new MAN1000S1Response_Body();

		try {
			String trCode	=	header.getTrcode();
			// 요청에서 받은 값(날짜, 회원 일련번호)
			String Date		=	reqBody.getDate();
			int userSeqPK	=	reqBody.getUserSeqPK();

			// DBMap에 요청에서 받은 값 저장
			DBMap reqMap	=	new DBMap();
			reqMap.put("Date",		Date);
			reqMap.put("userSeqPK",	userSeqPK);

//			-----------------------------------------------------------------------------------------------------
//			내가 참석자인 경우
//			-----------------------------------------------------------------------------------------------------

			// Attend 테이블에서 예약 일련번호(reserveSeqFK) selectList으로 받아오기
			@SuppressWarnings("unchecked")
			List<MAN1000S1Response_Body_reserveList3> reserveSeqResult = (List<MAN1000S1Response_Body_reserveList3>) dbAdapter
					.selectList("eduDB", trCode + ".SELECT_ATTEND_INFO_ALL", reqMap);
			// 응답(실패)
			if (reserveSeqResult.size()==0) {
				logger.debug("#### DB:{}", "MAN1000S1 참석자인 경우 예약 일련번호 SelectOne 결과 없음");
			}
			// 응답(성공)
			else {
				// 예약 일련번호 List
				reqMap.put("reserveSeqResult", reserveSeqResult);

				// 나의 예약 내역 List로 받아오기(내가 참석자인 경우)
				@SuppressWarnings("unchecked")
				List<MAN1000S1Response_Body_reserveList2> resultList = (List<MAN1000S1Response_Body_reserveList2>) dbAdapter
						.selectList("eduDB", trCode + ".SELECT_RESERVE_INFO_ALL2", reqMap);
				// 응답(실패)
				if (resultList.size() <= 0) {
					logger.debug("#### DB:{}", "MAN1000S1 참석자인 경우 예약 내역 없음");
					resBody.setReserveList2(resultList);
				}
				// 응답(성공)
				else {
					logger.debug("#### DB:{}", "MAN1000S1 참석자인 경우 예약 내역 존재");
					// selectList에서 받아온 내가 참석자인 경우에 예약 내역
					resBody.setReserveList2(resultList);
				}
			}


//			-----------------------------------------------------------------------------------------------------
//			내가 예약자인 경우
//			-----------------------------------------------------------------------------------------------------

			// User 테이블에서 회원 이름(userName) selectOne으로 받아오기
			MAN1000S1Response_Body userNameResult = dbAdapter.selectOne("eduDB", trCode + ".SELECT_RESERVE_INFO",
					reqMap, MAN1000S1Response_Body.class);
			// 응답(실패)
			if (userNameResult == null) {
				logger.error("#### DB:{}", "MAN1000S1 SelectOne 결과 없음");
				resBody.setUserName("해당 이름이 존재하지 않습니다.");
			}
			// 응답(성공)
			else {
				// 회원 이름(userName)
				String userName = userNameResult.getUserName();

				// 나의 예약 내역 List로 받아오기(내가 예약자인 경우)
				@SuppressWarnings("unchecked")
				List<MAN1000S1Response_Body_reserveList1> resultList = (List<MAN1000S1Response_Body_reserveList1>) dbAdapter
						.selectList("eduDB", trCode + ".SELECT_RESERVE_INFO_ALL", reqMap);
				//				응답(실패)
				if (resultList.size() <= 0) {
					logger.debug("#### DB:{}", "MAN1000S1 예약자인 경우 예약 내역 없음");
					resBody.setUserName(userName);
					resBody.setReserveList1(resultList);
				}
				// 응답(성공)
				else {
					logger.debug("#### DB:{}", "MAN1000S1 예약자인 경우 예약 내역 존재");
					// 이름과 나의 예약 내역(예약자, 참석자인 경우) 전송
					resBody.setUserName(userName);
					resBody.setReserveList1(resultList);
				}
			}
		}
		// 예외처리
		catch(PersistenceException e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 쿼리문 오류입니다. (매핑 or 표현 오류) =>"+e.getMessage());
			header.setError_text(header.getTrcode() +" 쿼리문 오류입니다. (매핑 or 표현 오류)");
		}
		catch(NullPointerException e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 요청으로 받은 값 중에 NULL 값을 참조했습니다. => "+e.getMessage());
			header.setError_text(header.getTrcode() +" 요청으로 받은 값 중에 NULL 값을 참조했습니다. ");
		}
		catch (Exception e) {
			logger.error("#### " + header.getTrcode() + "_ERR : 그 외 오류 발생.");
			logger.error("#### " + header.getTrcode() + "_ERR : 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException => " + e);
			return makeFailResponse(header.getTrcode() + "EXCEPTION"," 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException");
		}
		finally {
			response.setHeader(header);
			response.setBody(resBody);
			return makeResponse(obj, response.toJsonNode());
		}
	}

}
