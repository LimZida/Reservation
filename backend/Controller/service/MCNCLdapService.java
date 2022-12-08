package service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.springframework.stereotype.Component;
/***
*
* <p> Title : MCNCLdapService
*
*
* <p> Description : ldapLogin(String userId, String passwd) => Ldap 로그인 함수
*
*
* <p> Last Update Date : 2022.11.14
*
*
* @author LHY
*
*/

@Component
public class MCNCLdapService {
	private static final String SIMPLE = "simple";
	private static final String COM_SUN_JNDI_LDAP_LDAP_CTX_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	private static final String domain = "@mcnc.co.kr";
	private static final String searchBase = "DC=mcnc,DC=co, DC=kr";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map ldapLogin(String userId, String passwd) {

		try {
			String ldapURL = "ldap.mcnc.co.kr";
			String ldapHost = "ldap://" + ldapURL + ":389";

			String searchFilter = "(&(objectClass=user)(sAMAccountName=" + userId
					+ "))";

			// Create the search controls
			SearchControls searchCtls = new SearchControls();

			// Specify the search scope
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					COM_SUN_JNDI_LDAP_LDAP_CTX_FACTORY);
			env.put(Context.PROVIDER_URL, ldapHost);
			env.put(Context.SECURITY_AUTHENTICATION, SIMPLE);
			env.put(Context.SECURITY_PRINCIPAL, userId + domain);
			env.put(Context.SECURITY_CREDENTIALS, passwd);
			LdapContext ctxGC = null;

			ctxGC = new InitialLdapContext(env, null);
			// Search objects in GC using filters
			NamingEnumeration answer = ctxGC.search(searchBase, searchFilter,
					searchCtls);

			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				Attributes attrs = sr.getAttributes();
				Map amap = null;
				if (attrs != null) {
					amap = new HashMap();
					NamingEnumeration ne = attrs.getAll();
					while (ne.hasMore()) {
						Attribute attr = (Attribute) ne.next();
						amap.put(attr.getID(), attr.get());
					}
					ne.close();
				}
				return amap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
