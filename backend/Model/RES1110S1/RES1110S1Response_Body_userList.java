package model.RES1110S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 회원 리스트 목록 (가장 높은 직급이 맨 처음)
 */
public class RES1110S1Response_Body_userList {
	/**
	* 회원 일련번호
	*/
	private int userSeqPK = 0;
	/**
	* 회원 직급
	*/
	private String userPosition = "";
	/**
	* 회원 부서
	*/
	private String userDep = "";
	/**
	* 회원 이름
	*/
	private String userName = "";
	/**
	* 회원 이메일
	*/
	private String userEmail = "";

	public RES1110S1Response_Body_userList() {
	}

	public RES1110S1Response_Body_userList(JsonNode jsonNode) {
		this.userSeqPK = jsonNode.path("userSeqPK").intValue();
		this.userPosition = jsonNode.path("userPosition").textValue();
		this.userDep = jsonNode.path("userDep").textValue();
		this.userName = jsonNode.path("userName").textValue();
		this.userEmail = jsonNode.path("userEmail").textValue();
	}

	@JsonProperty("userSeqPK")
	public int getUserSeqPK() {
		return this.userSeqPK;

	}

	public void setUserSeqPK(int userSeqPK) {
		this.userSeqPK = userSeqPK;
	}

	@JsonProperty("userPosition")
	public String getUserPosition() {
		return this.userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

	@JsonProperty("userDep")
	public String getUserDep() {
		return this.userDep;
	}

	public void setUserDep(String userDep) {
		this.userDep = userDep;
	}

	@JsonProperty("userName")
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty("userEmail")
	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1110S1Response_Body_userList [");
		builder.append("userSeqPK=").append(userSeqPK);
		builder.append(", ");
		builder.append("userPosition=").append(userPosition);
		builder.append(", ");
		builder.append("userDep=").append(userDep);
		builder.append(", ");
		builder.append("userName=").append(userName);
		builder.append(", ");
		builder.append("userEmail=").append(userEmail);
		builder.append("]");
		return builder.toString();
	}
}