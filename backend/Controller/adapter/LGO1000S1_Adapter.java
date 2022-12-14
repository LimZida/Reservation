package adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.hybrid.adapter.api.Adapter;
import com.mcnc.bizmob.hybrid.adapter.api.IAdapterJob;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;

import model.LGO1000S1.LGO1000S1Request;
import model.LGO1000S1.LGO1000S1Request_Body;
import model.LGO1000S1.LGO1000S1Response;
import model.LGO1000S1.LGO1000S1Response_Body;
import model.header.DMHeader;
import service.PushService;

/***
*
* <p>
* Title : 로그아웃 - LGO1000S1
*
* <p>
* Legacy System : BIZMEET DB
*
* <p>
* Description : 로그아웃 시 해당 사용자의 JWT 세션과 등록 DATE를 NULL로 수정
*
* <p>
* Error Code : 		PersistenceException => 쿼리문 오류입니다. (값 매핑 or 표현 오류)
* 					NullPointerException => pushKey가 NULL 값을 참조했습니다.
*					Exception => 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException
* <p>
* Last Update Date : 2022.11.21
*
* @author LHY
*
*/

@Adapter(trcode = { "LGO1000S1" })
public class LGO1000S1_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	//	알림설정
	@Autowired
	private PushService pushService;

	private static final Logger logger = LoggerFactory.getLogger(LGO1000S1_Adapter.class);

	@SuppressWarnings("finally")
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		/* 전문의 Request Header, Body (전문별 코딩) */

		// Request 객체 생성 후 Request Header, Body 가져오기
		LGO1000S1Request		request	=	new LGO1000S1Request(obj);
		DMHeader				header	=	request.getHeader();
		LGO1000S1Request_Body	reqBody	=	request.getBody();

		// Response, Response_Body 객체 생성
		LGO1000S1Response		response	=	new LGO1000S1Response();
		LGO1000S1Response_Body	resBody		=	new LGO1000S1Response_Body();

		try {
//			요청에서 받은 값(회원 일련번호)
			String pushKey		=	reqBody.getPushKey();
			
			String deleteResult=pushService.DeletePushKey(pushKey);
			if(deleteResult==null) {
				logger.error("PushKey(" + pushKey + ") 삭제 실패");
				resBody.setFlag(false);
			}
			else {				
				logger.debug("PushKey(" + pushKey + ") 삭제 완료");
				resBody.setFlag(true);
			}

		}
//		예외처리
		catch(NullPointerException e) {
			logger.error("#### {}", header.getTrcode() +
			"_ERR : pushKey가 NULL 값을 참조했습니다. => "+e.getMessage());
			header.setError_text(header.getTrcode() +" pushKey가 NULL 값을 참조했습니다.");
		}
		catch(Exception e){
			logger.error("#### " + header.getTrcode() + "_ERR : 그 외 오류 발생.");
			logger.error("#### " + header.getTrcode() + "_ERR : 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException => " + e);
			return makeFailResponse(header.getTrcode() + "EXCEPTION"," 어댑터에서 로직 처리 Exception 에러가 발생하였습니다. Ex)SQLException , IOException");
		}
		finally {
			response.setHeader(header);
			response.setBody(resBody);
			return makeResponse(obj,response.toJsonNode());
		}

	}

}
