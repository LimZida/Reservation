package test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;

import org.junit.BeforeClass;
import org.springframework.util.ResourceUtils;

import com.mcnc.bizmob.common.Smart;
import com.mcnc.bizmob.common.service.BaseConfigService;
import com.mcnc.bizmob.common.util.JsonUtil;

public class TestAdapter {

	@BeforeClass
	public static void initialize() {
		try {
			String bizmobBase = ResourceUtils.getURL("WebContent").getPath();
			System.setProperty("bizmobBase", bizmobBase);
			System.setProperty("bizmobCoreHome", bizmobBase + "/WEB-INF/config");
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"org.apache.xbean.spring.jndi.SpringInitialContextFactory");

			jndiInit();
			setBizmobConfiguration();

			new InitialContext();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void jndiInit() {
		try {
			Context jndiNamingContext = InitialContext.doLookup("java:/comp/env/");
			@SuppressWarnings("rawtypes")
			NamingEnumeration jndiNamingEnum = jndiNamingContext.list("");

			if (jndiNamingEnum.hasMore()) {
				System.setProperty("initial_context", "java:/comp/env/");
			} else {
				System.setProperty("initial_context", "");
			}

		} catch (Exception e) {
			System.setProperty("initial_context", "");
		}
	}

	private static void setBizmobConfiguration() {

		String bizmobConfigurationValue = "/WEB-INF/config/bizmob_configuration.xml";
		BaseConfigService baseConfigService;
		try {
			baseConfigService = new BaseConfigService(bizmobConfigurationValue);
			Smart.setAuthConfig(baseConfigService.getAuthConfig());
			Smart.setBaseConfig(baseConfigService.getBaseConfig());
			Smart.setCustomHome(baseConfigService.getCustHome());
			Smart.setCoreHome(System.getProperty("bizmobCoreHome"));
			Smart.setSessionConfig(baseConfigService.getSessionConfig());
			System.setProperty("bizmobCustHome", Smart.getCustomHome());

			StringBuffer sb = new StringBuffer();
			sb.append(
					"====================================bizMOB Configuration, bizMOB 환경설정========================================\n");
			sb.append(JsonUtil.toJson(baseConfigService, true) + "\n");
			sb.append(
					"================================================================================================================\n");

			System.out.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
