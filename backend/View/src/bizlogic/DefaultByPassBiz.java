

package bizlogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.hybrid.common.json.SimpleJsonResponse;
import com.mcnc.bizmob.hybrid.common.server.AbstractJsonAdaptor;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.bizmob.hybrid.common.server.MessageObject;
import com.mcnc.bizmob.hybrid.common.server.MessageProcessor;
import com.mcnc.bizmob.hybrid.server.core.AbstractMessageProcessor;



public class DefaultByPassBiz extends AbstractMessageProcessor implements MessageProcessor {
	public static Logger logger 	= LoggerFactory.getLogger(DefaultByPassBiz.class);
    
    @Autowired
    AbstractJsonAdaptor adapterDispatcher;

    public MessageObject process(String id, MessageObject session, MessageObject request) {
    	
        long tID = super.getTransactionId(session);

        String errorCode = null;
        JsonAdaptorObject reqObj = null;
        JsonAdaptorObject resObj = null;
        MessageObject theObj = null;

        // Adaptor dispatcher 얻기
        try {
            // JSON 값이 정상으로 전달 되었는 지 확인
            if (request.containsKey(MessageObject.MESSAGE_TYPE.JSON)) {
                // Adaptor Dispatcher에 전달할 Object 생성
                reqObj = buildAdpatorObject(session, null, request);
            }

            else {
                errorCode = id + _ERROR_PREFIX + _EMPTY_REQUEST;
            }
        } catch (Exception e) {
            errorCode = id + _ERROR_PREFIX + _SYSTEM_EXCEPTION;
        } catch (Throwable t) {
            errorCode = id + _ERROR_PREFIX + _SYSTEM_EXCEPTION;
        } finally {
            theObj = new MessageObject();
        }

        if (errorCode != null) {
            SimpleJsonResponse res = new SimpleJsonResponse(false, errorCode, null, null);

            theObj.put(MessageObject.MESSAGE_TYPE.ERROR, res);
        }

        else {
            try {
                // Adaptor Dispatcher 실행
                resObj = adapterDispatcher.dispatch(reqObj);
                
                logger.trace("{} Success to dispatch to adapter!", tID);
                logger.debug("{} Response Set: {}", tID, resObj.keySet());
                
                // 반환할 객체 생성
                theObj = buildResultObject(id, resObj);
            } catch (Exception e) {
                SimpleJsonResponse res = new SimpleJsonResponse(false, id + _ERROR_PREFIX + _SYSTEM_EXCEPTION,
                                                                e.getMessage(), null);

                theObj.put(MessageObject.MESSAGE_TYPE.ERROR, res);
                logger.warn("{} Failed to execute the adapter : {}", tID, e);
            }
        }

        return theObj;
    }
}
