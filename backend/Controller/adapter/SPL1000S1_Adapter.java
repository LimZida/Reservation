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

import model.SPL1000S1.SPL1000S1Request;
import model.SPL1000S1.SPL1000S1Request_Body;
import model.SPL1000S1.SPL1000S1Response;
import model.SPL1000S1.SPL1000S1Response_Body;
import model.header.DMHeader;
import service.JwtTokenProviderService;
/***
 *
 * <p> Title : 스플래쉬 - SPL1000S1
 *
 * <p> Legacy System : DB
 *
 * <p> Description : JWT 토큰이 만료 여부를 확인한다. [존재 시 TokenFlag : true,  만료 시 TokenFlag : false]
 * 					 만료되었을 때 로그인 화면으로, 만료되지 않았을 경우에는 메인 화면으로 이동
 *
 * <p> Error Code : SPL1000S1_NullPointerException	:	Jwt Token이 빈 값이거나 null일 경우 발생
 * 					SPL1000S1_PersistenceException	:	쿼리문 오류 (매핑 or 표현 오류)
 * 					SPL1000S1_ERR					:	그 외 어뎁터 오류 발생 시
 *
 *
 * <p> Last Update Date : 2022.11.28
 *
 * @author MKH
 *
 */
@Adapter(trcode = { "SPL1000S1" })
public class SPL1000S1_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(SPL1000S1_Adapter.class);

	// JWT 토큰
	@Autowired
	private JwtTokenProviderService JwtTokenProvider;

	@Autowired
	private DBAdapter dbAdapter;

	@SuppressWarnings("finally")
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		// Request 객체 생성
		SPL1000S1Request 			request		= new SPL1000S1Request(obj);
		// Request Header, Body 가져오기
		DMHeader 					header		= request.getHeader();
		SPL1000S1Request_Body 		reqBody		= request.getBody();

		// Response 객체 생성
		SPL1000S1Response			response	= new SPL1000S1Response();
		SPL1000S1Response_Body		resBody		= new SPL1000S1Response_Body();

		try {
			// 전문코드, JwtToken 가져오기
			String trCode	=	header.getTrcode();
			String JwtToken	=	reqBody.getJwtToken();

			// null 값으로 들어왔을 경우 예외 발생
			if(JwtToken == null || JwtToken.equals("")) {
				throw new NullPointerException();
			}

			// 해당 토큰이 만료되었는지 확인
			boolean validateResult = JwtTokenProvider.validateToken(JwtToken);
			// Token이 만료되었을 경우
			if(!validateResult) {
				logger.debug("#### " + header.getTrcode() + " : 토큰이 만료되었거나 존재하지 않습니다.");
				resBody.setTokenFlag(false);
				resBody.setUserSeqPK(null);
			}
			// Token이 만료되지 않았을 경우
			else{
				DBMap reqMap	=	new DBMap();
				reqMap.put("JwtToken",	JwtToken);

				// selectOne으로 JwtToken에 해당하는 user테이블의 userSeqPK 응답
				// resBody에 SPL1000S1.xml을 통한 SELECT 쿼리문으로 해당 Response를 가져와 DB 매핑
				resBody = dbAdapter.selectOne("eduDB", trCode + ".SELECT_USER_INFO", reqMap, SPL1000S1Response_Body.class);
				logger.debug("#### " + header.getTrcode() + " : " + resBody);
//				응답(실패)
				if(resBody==null) {
					throw new NullPointerException();
				}
//				응답(성공)
				else {
					resBody.setTokenFlag(true);
					resBody.setUserSeqPK(resBody.getUserSeqPK());
				}
			}
		}
//		예외처리
//		쿼리문 오류일 경우 발생
		catch(PersistenceException e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 쿼리문 오류입니다. (매핑 or 표현 오류) =>"+e.getMessage());
			header.setError_text(header.getTrcode() +" 쿼리문 오류입니다. (매핑 or 표현 오류)");
		}
		// Jwt Token이 빈 값이거나 null일 경우 발생
		catch(NullPointerException e) {
			logger.error("#### " + header.getTrcode() + "_NullPointerException : Jwt Token이나 resBody가 null입니다."+e.getMessage());
			header.setError_text(header.getTrcode() +" Jwt Token이나 resBody가 null입니다.");
		}
		// 그 외 오류 발생 시
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
