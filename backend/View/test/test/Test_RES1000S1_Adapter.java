package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;

import adapter.RES1000S1_Adapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class Test_RES1000S1_Adapter extends TestAdapter {

	@Autowired
	private RES1000S1_Adapter adapter;
	private JsonAdaptorObject obj = new JsonAdaptorObject();

	@Before
	public void setUp() throws Exception {
		//TODO 요청데이터 입력. JsonAdaptorObject.TYPE.REQUEST
		InputStream in = new FileInputStream(new File("./test/data/RES1000S1.json"));
		ObjectMapper om = new ObjectMapper();
		JsonNode root = om.readTree(in);
		obj.put(JsonAdaptorObject.TYPE.REQUEST, root);
	}

	@Before
	public void setSession() {
		// TODO 세션 데이터 입력. JsonAdaptorObject.TYPE.META

	}

	@Test
	public void testAdapterBeanInstance() {
		// test creating adapter bean
		assertNotNull(adapter);
	}

	@Test
	public void testOnProcess() {

		// test adapter process.
		JsonAdaptorObject res = adapter.onProcess(obj);
		assertNotNull("Must not return a null response object", res);

		System.out.println(res.toString());

		assertTrue("Result of Response should be True",
				res.get(JsonAdaptorObject.TYPE.RESULT).path("result").booleanValue());
	}

}
