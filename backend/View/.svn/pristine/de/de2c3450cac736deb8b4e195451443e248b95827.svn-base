package adapter.test;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.hybrid.adapter.api.Adapter;
import com.mcnc.bizmob.hybrid.adapter.api.IAdapterJob;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.bizmob.journal.JournalInfo;

@Adapter(trcode = { "CES0232" })
public class CES0232_ScheduleInsert extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CES0232_ScheduleInsert.class);
	@Autowired
	private DBAdapter dbAdapter;

	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		SqlSession session = null; 
		JournalInfo journalInfo = new JournalInfo(1230123, "SESSIONID", "TESTDEVICE", "CES0232", "TESTER", System.currentTimeMillis(), 12, true, "", "", "", "127.0.0.1", "127.0.0.1", "3.0", "3.0");
		
		try{
			session = dbAdapter.openSession("JournalDB", false);
			int result = dbAdapter.insert(session, "journal.SQL_KEEP", journalInfo);
			logger.info("RESULT : " + result);
			
//			throw new Exception();
			dbAdapter.commitAndClose(session);
			return makeResponse(obj, null);
			
		} catch (Exception e) {
			dbAdapter.rollbackAndClose(session);
			logger.error("", e);
			return makeFailResponse("CES0232BZDM" + "0000");
		} finally {
			
		}
	}
}
