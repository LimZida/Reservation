package model.RES1110S1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class RES1110S1Response_Body {
	/**
	* 회원 리스트 목록 (가장 높은 직급이 맨 처음)
	*/
	private List<RES1110S1Response_Body_userList> userList = new ArrayList<RES1110S1Response_Body_userList>();
	/**
	* 해당 사용자의 부서
	*/
	private String userDep = "";

	public RES1110S1Response_Body() {

	}

	public RES1110S1Response_Body(JsonNode jsonNode) {
		this.userList = new ArrayList<RES1110S1Response_Body_userList>();
		JsonNode userListNode = jsonNode.path("userList");
		for (Iterator<JsonNode> iter = userListNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			userList.add(new RES1110S1Response_Body_userList(node));
		}
		this.userDep = jsonNode.path("userDep").textValue();
	}

	@JsonProperty("userList")
	public List<RES1110S1Response_Body_userList> getUserList() {
		return this.userList;
	}

	public void setUserList(List<RES1110S1Response_Body_userList> userList) {
		this.userList = userList;
	}

	@JsonProperty("userDep")
	public String getUserDep() {
		return this.userDep;
	}

	public void setUserDep(String userDep) {
		this.userDep = userDep;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1110S1Response_Body [");
		builder.append("userList=").append(userList);
		builder.append(", ");
		builder.append("userDep=").append(userDep);
		builder.append("]");
		return builder.toString();
	}
}