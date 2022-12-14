package adapter;

import java.util.Map;

import javax.crypto.IllegalBlockSizeException;

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

import model.LGN1000S1.LGN1000S1Request;
import model.LGN1000S1.LGN1000S1Request_Body;
import model.LGN1000S1.LGN1000S1Response;
import model.LGN1000S1.LGN1000S1Response_Body;
import model.header.DMHeader;
import service.GetAesService;
import service.JwtTokenProviderService;
import service.MCNCLdapService;

/***
 *
 * <p>
 * Title : 로그인 - LGN1000S1
 *
 * <p>
 * Legacy System : BIZMEET DB
 *
 * <p>
 * Description : ID와 PW를 입력하여 로그인을 진행
 *
 * <p>
 * Error Code : LGN1000S1_NullPointerException		: ID 또는 PW가 틀릴 경우 발생
 * 				LGN1000S1_IllegalArgumentException	: 잘못된 Parameter를 전송할 경우 발생
 * 				LGN1000S1_IllegalBlockSizeException	: 암호화되지 않은 비밀번호를 전송할 경우 발생
 * 				LGN1000S1_PersistenceException		: 쿼리 오류 발생
 * 				LGN1000S1_ERR						: 그 외 오류 발생 시
 * <p>
 * Last Update Date : 2022.11.09
 *
 * @author MKH
 *
 */
@Adapter(trcode = { "LGN1000S1" })
public class LGN1000S1_Adapter extends AbstractTemplateAdapter implements IAdapterJob {
	private static final Logger logger = LoggerFactory.getLogger(LGN1000S1_Adapter.class);

	// AES 암호화
	@Autowired
	private GetAesService AES;

	// JWT 토큰
	@Autowired
	private JwtTokenProviderService Jwt;

	// Ldap 로그인
	@Autowired
	private MCNCLdapService mcncLdapSample;

	@Autowired
	private DBAdapter dbAdapter;

	@SuppressWarnings("finally")
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		// Request 객체 생성
		LGN1000S1Request		request		=	new LGN1000S1Request(obj);
		// Request Header, Body 가져오기
		DMHeader 				header		=	request.getHeader();
		LGN1000S1Request_Body	reqBody		=	request.getBody();
		logger.debug("#### " + header.getTrcode() + " reqBody :{}", reqBody);

		// Response 객체 생성
		LGN1000S1Response 		response	=	new LGN1000S1Response();
		LGN1000S1Response_Body 	resBody		=	new LGN1000S1Response_Body();

		try {
			// 전문코드 및 사용자 Id, Pw 가져오기
			String trCode = header.getTrcode();
			String userId = reqBody.getUserId();
			String userPw = reqBody.getUserPw();

			// 암호화로 받은 PW 복호화
			userPw = AES.decrypt(userPw);

			// userId, userPw를 통해 ldapLogin 시도
			@SuppressWarnings("rawtypes")
			Map ldapLogin = mcncLdapSample.ldapLogin(userId, userPw);

			// DB테이블에 해당하는 Mail, Name의 양식으로 변환
			String userEmail	=	(String) ldapLogin.get("userPrincipalName");
			String userName		=	(String) ldapLogin.get("displayName") + "(" + ldapLogin.get("sAMAccountName") + ")";

			// DBMap 객체 생성
			DBMap reqMap = new DBMap();

			// DBMap에 사용자 Email, Name 삽입
			reqMap.put("userEmail",	userEmail);
			reqMap.put("userName",	userName);

			// resBody에 LGN1000S1.xml을 통한 SELECT 쿼리문으로 해당 Response를 가져와 DB 매핑
			resBody = dbAdapter.selectOne("eduDB", trCode + ".SELECT_USER", reqMap, LGN1000S1Response_Body.class);

			// resBody에서 userSeqPK를 가져와 pushUserSeqFK에 할당
			int pushUserSeqFK = resBody.getUserSeqPK();
			// reqMap에 pushUserSeqFK 삽입
			reqMap.put("pushUserSeqFK", pushUserSeqFK);

			logger.debug("#### " + header.getTrcode() + " : 로그인  시도 [ID :" + userId + ", Name : " + userName + "]");

			// JWT 토큰 생성
			String JwtToken = Jwt.createToken(resBody.getUserSeqPK());
			// reqMap에 JwtToken 삽입
			reqMap.put("JwtToken", JwtToken);

			logger.debug("#### " + header.getTrcode() + " 만료기간 : "
					+ Jwt.getInformation(JwtToken).getExpiration().toString());

			// USER 테이블에 JWT 토큰 삽입
			// updateResult에 LGN1000S1.xml을 통한 UPDATE 쿼리문으로 해당 Response를 가져와 DB 매핑
			int updateResult = dbAdapter.update("eduDB", trCode + ".UPDATE_JWT_TOKEN", reqMap);

			// 업데이트 성공 시
			if (updateResult >= 1) {
				logger.debug("#### " + header.getTrcode() + " : USER TABLE JWT TOKEN UPDATE 성공");
				logger.debug("#### " + header.getTrcode() + " : TOKEN 발급 (" + JwtToken + ")");

				resBody.setJwtToken(JwtToken);
				resBody.setFlag(true);
				resBody.setUserName(userName);
			}
			// 실패 시
			else {
				logger.error("#### " + header.getTrcode() + " : USER TABLE JWT TOKEN UPDATE 실패");

				resBody.setJwtToken(null);
				resBody.setFlag(false);
				resBody.setUserName(null);
			}

		}
		// 쿼리문 오류
		catch(PersistenceException e) {
			logger.error("#### {}", header.getTrcode() +
			"_PersistenceException : 쿼리문 오류입니다. (매핑 or 표현 오류) =>"+e.getMessage());
			header.setError_text(header.getTrcode() +" 쿼리문 오류입니다. (매핑 or 표현 오류) ");
		}
		// 회원 정보가 존재하지 않을 경우
		catch (NullPointerException e) {
			logger.error("#### " + header.getTrcode() + "_NullPointerException : ID 또는 PW가 틀렸습니다."+e.getMessage());
			header.setError_text(header.getTrcode() +"  ID 또는 PW가 틀렸습니다.");
		}
		// 잘못된 매개변수를 전송할 경우
		catch (IllegalArgumentException e) {
			logger.error("#### " + header.getTrcode() + "_IllegalArgumentException : 잘못된 Parameter를 전송하였습니다."+e.getMessage());
			header.setError_text(header.getTrcode() +" 잘못된 Parameter를 전송하였습니다.");
		}
		// 암호화되지 않은 패스워드를 보낼 경우
		catch (IllegalBlockSizeException e) {
			logger.error("#### " + header.getTrcode() + "_IllegalBlockSizeException : 암호화되지 않은 비밀번호를 전송하였습니다."+e.getMessage());
			header.setError_text(header.getTrcode() +"암호화되지 않은 비밀번호를 전송하였습니다.");
		}
		// 그 외 오류 발생 시
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
