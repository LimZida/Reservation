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

import model.EML1000S1.EML1000S1Request;
import model.EML1000S1.EML1000S1Request_Body;
import model.EML1000S1.EML1000S1Response;
import model.EML1000S1.EML1000S1Response_Body;
import model.header.DMHeader;
import service.MailSendService;
/***
*
* <p>
* Title : 이메일 - EML1000S1
*
* <p>
* Legacy System : BIZMEET DB
*
* <p>
* Description : 이메일 인증 시 사용자에게 이메일 관련 정보 전송
*
* <p>
* Error Code : 		PersistenceException => 쿼리문 오류입니다. (값 매핑 or 표현 오류)
*			Exception => 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException
* <p>
* Last Update Date : 2022.11.21
*
* @author LHY
*
*/
@Adapter(trcode = { "EML1000S1" })
public class EML1000S1_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(EML1000S1_Adapter.class);
	@Autowired
	private DBAdapter dbAdapter;

	@Autowired
	private MailSendService mailService;

	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
//      요청 객체 생성
		EML1000S1Request request=new EML1000S1Request(obj);
//		요청 객체의 헤더 및 바디
		DMHeader header= request.getHeader();
		EML1000S1Request_Body reqBody=request.getBody();
		logger.debug("#### EML1000S1 reqBody :{}", reqBody);


		try {
//			요청에서 받은 값(날짜, 회원 일련번호)
			String trCode=header.getTrcode();
			String inputEmail=reqBody.getInputEmail();

//          응답 객체 생성
			EML1000S1Response response=new EML1000S1Response();
			EML1000S1Response_Body resBody=new EML1000S1Response_Body();


//			해당 이메일에 인증번호 발송
			String authCode= mailService.joinEmail(inputEmail);
//			통신 오류가 있으면
//			응답(실패)
			if(authCode.isEmpty()) {
				resBody.setFlag(false);
			}
//			통신 오류가 없으면
			else {
//			  	DBMap에 이메일, 인증번호 저장
				DBMap 		reqMap= new DBMap();
				reqMap.put("inputEmail", inputEmail);
				reqMap.put("authCode", authCode);
				logger.debug("#### EML1000S1 reqMap :{}", reqMap);


//				회원 이메일 selectOne으로 받아오기
				EML1000S1Response_Body userEmailResult = dbAdapter.selectOne("eduDB", trCode + ".SELECT_EMAIL_INFO", reqMap,EML1000S1Response_Body.class);
//				회원 이메일이 없을 경우
				if(userEmailResult==null) {
//					이메일, 인증번호 삽입
					int insertResult = dbAdapter.insert("eduDB", trCode + ".INSERT_EMAIL_INFO", reqMap);
//					결과가 없으면
//					응답(실패)
					if(insertResult<1) {
						logger.error("#### DB:{}","EML1000S1 insert 실패");
						resBody.setFlag(false);
					}
//					결과가 있으면
//					응답(성공)
					else {
						resBody.setFlag(true);
					}
				}
//				회원 이메일이 있을 경우
				else {
//					해당 이메일에 대한 인증번호 업데이트
					int updateResult = dbAdapter.update("eduDB", trCode + ".UPDATE_EMAIL_INFO", reqMap);
//					결과가 없으면
//					응답(실패)
					if(updateResult<1) {
						logger.error("#### DB:{}","EML1000S1 update 실패");
						resBody.setFlag(false);
					}
//					결과가 있으면
//					응답(성공)
					else {
						resBody.setFlag(true);
					}
				}
			}
		}
//		예외처리
		catch(PersistenceException e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : 쿼리문 오류입니다. (매핑 or 표현 오류) =>"+e.getMessage());
			header.setError_text(header.getTrcode() +" 쿼리문 오류입니다. (매핑 or 표현 오류)");
		}
		catch(Exception e){
//			에러코드 전송
				return makeFailResponse(header.getTrcode()+" 어댑터에서 로직 처리 중 에러가 발생하였습니다."+ e.getMessage());
		}
		finally {
			response.setHeader(header);
			response.setBody(resBody);
			return makeResponse(obj, response.toJsonNode());
		}
	}

}