package adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.db.type.DBMap;
import com.mcnc.bizmob.hybrid.adapter.api.Adapter;
import com.mcnc.bizmob.hybrid.adapter.api.IAdapterJob;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;

import model.EML1001S1.EML1001S1Request;
import model.EML1001S1.EML1001S1Request_Body;
import model.EML1001S1.EML1001S1Response;
import model.EML1001S1.EML1001S1Response_Body;
import model.header.DMHeader;
/***
*
* <p>
* Title : 이메일 - EML1001S1
*
* <p>
* Legacy System : BIZMEET DB
*
* <p>
* Description : 사용자가 이메일 인증 검증 시
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
@Adapter(trcode = { "EML1001S1" })
public class EML1001S1_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(EML1001S1_Adapter.class);
	@Autowired
	private DBAdapter dbAdapter;

	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
//      요청 객체 생성
		EML1001S1Request request=new EML1001S1Request(obj);
//		요청 객체의 헤더 및 바디
		DMHeader header= request.getHeader();
		EML1001S1Request_Body reqBody=request.getBody();
		logger.debug("#### EML1001S1 reqBody :{}", reqBody);


		try {
//			요청에서 받은 값(인증번호, 이메일)
			String trCode=header.getTrcode();
			String authCode=reqBody.getAuthCode();
			String inputEmail=reqBody.getInputEmail();

//			DBMap에 요청에서 받은 값 저장
			DBMap 		reqMap= new DBMap();
			reqMap.put("authCode", authCode);
			reqMap.put("inputEmail", inputEmail);
			logger.debug("#### EML1001S1 reqMap :{}", reqMap);

//          응답 객체 생성
			EML1001S1Response response=new EML1001S1Response();
			EML1001S1Response_Body resBody=new EML1001S1Response_Body();

//			이메일 selectOne으로 받아오기
			EML1001S1Response_Body userEmailResult = dbAdapter.selectOne("eduDB", trCode + ".SELECT_EMAIL_INFO", reqMap,EML1001S1Response_Body.class);
//			selectOne 결과가 없을 경우
//			응답(실패)
			if(userEmailResult==null) {
				logger.error("#### DB:{}","EML1001S1 SelectOne 결과 없음");
				resBody.setFlag(false);
			}
//			selectOne 결과가 있을 경우
//			응답(성공)
			else {
				logger.debug("#### DB:{}","EML1001S1 SelectOne 결과 있음");
				resBody.setFlag(true);
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