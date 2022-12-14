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

import model.RES1110S1.RES1110S1Request;
import model.RES1110S1.RES1110S1Request_Body;
import model.RES1110S1.RES1110S1Response;
import model.RES1110S1.RES1110S1Response_Body;
import model.RES1110S1.RES1110S1Response_Body_userList;
import model.header.DMHeader;

/***
* <p> Title : 직원 선택 - RES1110S1
*
* <p> Legacy System : BIZMEET DB
*
* <p> Description : 부서를 선택하면 그 부서에 해당하는 직원List를 전송
*
* <p> Error Code :  PersistenceException => 쿼리문 오류입니다. (값 매핑 or 표현 오류)
*					NullPointerException => userSeqPK에서 NULL 값을 참조했습니다.
*					OutOfMemoryError => 배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다.
*					Exception => 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException
*
*
*
* <p> Last Update Date : 2022.11.11
*
* @author LHY
*
*/
@Adapter(trcode = { "RES1110S1" })
public class RES1110S1_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(RES1110S1_Adapter.class);
	@Autowired
	private DBAdapter dbAdapter;

	@SuppressWarnings("finally")
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
//      요청 객체 생성
		RES1110S1Request request=new RES1110S1Request(obj);
//		요청 객체의 헤더 및 바디
		DMHeader header= request.getHeader();
		RES1110S1Request_Body reqBody=request.getBody();
		logger.debug("#### RES1110S1 reqBody :{}", reqBody);

//      응답 객체 생성
		RES1110S1Response response=new RES1110S1Response();
		RES1110S1Response_Body resBody=new RES1110S1Response_Body();

		try {
			String trCode=header.getTrcode();
//			요청에서 받은 값(회원 일련번호)
			int userSeqPK=reqBody.getUserSeqPK();

//			DBMap에 요청에서 받은 값 저장
			DBMap 		reqMap= new DBMap();
			reqMap.put("userSeqPK", userSeqPK);

//			User 테이블에서 로그인한 회원의 부서 selectOne 하기(부서 default 초기화면을 위해)
			RES1110S1Response_Body selectDepResult = dbAdapter.selectOne("eduDB", trCode + ".SELECT_USER_DEP_INFO", reqMap, RES1110S1Response_Body.class);
//		    응답(실패)
			if(selectDepResult==null) {
				logger.debug("#### DB:{}","RES1110S1 User 테이블에 해당하는 로그인한 직원의 부서가 없음");
//				default값 없음
				resBody.setUserDep("");
			}
//		    응답(성공)
			else {
				logger.debug("#### DB:{}","RES1110S1 User 테이블에 해당하는 로그인한 직원의 부서가 있음");
//				로그인한 회원의 userDep
				resBody.setUserDep(selectDepResult.getUserDep());
			}

//			User 테이블에서 회원 내역 List로 받아오기
			@SuppressWarnings("unchecked")
			List<RES1110S1Response_Body_userList>boardList =
			(List<RES1110S1Response_Body_userList>) dbAdapter.selectList("eduDB", trCode + ".SELECT_USER_INFO_ALL", reqMap);
//		    응답(실패)
			if(boardList.size()<=0) {
				logger.debug("#### DB:{}","RES1110S1 User 테이블에 해당하는 직원 없음");

//				비어있는 List 내역 전송
				resBody.setUserList(boardList);
			}
//			응답(성공)
			else {
				logger.debug("#### DB:{}","RES1110S1 User 테이블에 해당하는 직원 있음");

//				User 테이블에서 부서(userSeqPK)별 회원 내역 전송
				resBody.setUserList(boardList);
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
			"_ERR : 요청으로 받은 userSeqPK가 NULL 값을 참조했습니다. => "+e.getMessage());
			header.setError_text(header.getTrcode() +" 요청으로 받은 userSeqPK가 NULL 값을 참조했습니다. ");
		}
		catch(OutOfMemoryError e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다. =>"+e.getMessage());
			header.setError_text(header.getTrcode() +"  배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다. ");
		}
		catch(Exception e) {
			logger.error("#### " + header.getTrcode() + "_ERR : 그 외 오류 발생.");
			logger.error("#### " + header.getTrcode() + "_ERR : 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException => " + e);
			header.setError_text(header.getTrcode() +"  배열 혹은 리스트의 크기가 범위를 초과해 Heap 메모리 영역을 초과했습니다. ");
			return makeFailResponse(header.getTrcode() + "EXCEPTION"," 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException");
		}
		finally {
			response.setHeader(header);
			response.setBody(resBody);
			return makeResponse(obj,response.toJsonNode());
		}
	}

}
