package model.EDU0001;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class EDU0001Response_Body {
	/**
	* 사용자 삭제 여부(Y:삭제, N:정상)
	*/
	private String userDelYn = "";
	/**
	* 사용자이메일
	*/
	private String userEmail = "";
	/**
	* 사용자명
	*/
	private String userName = "";
	/**
	* 사용자등록일(YYYYMMDD)
	*/
	private String userRegDate = "";
	/**
	* 사용자수정일(YYYYMMDD)
	*/
	private String userUpdDate = "";
	/**
	* 사용자휴대폰번호(-제외)
	*/
	private String userPhoneNo = "";

	public EDU0001Response_Body() {
	}

	public EDU0001Response_Body(JsonNode jsonNode) {
		this.userDelYn = jsonNode.path("userDelYn").textValue();
		this.userEmail = jsonNode.path("userEmail").textValue();
		this.userName = jsonNode.path("userName").textValue();
		this.userRegDate = jsonNode.path("userRegDate").textValue();
		this.userUpdDate = jsonNode.path("userUpdDate").textValue();
		this.userPhoneNo = jsonNode.path("userPhoneNo").textValue();
	}

	@JsonProperty("userDelYn")
	public String getUserDelYn() {
		return this.userDelYn;
	}

	public void setUserDelYn(String userDelYn) {
		this.userDelYn = userDelYn;
	}

	@JsonProperty("userEmail")
	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@JsonProperty("userName")
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty("userRegDate")
	public String getUserRegDate() {
		return this.userRegDate;
	}

	public void setUserRegDate(String userRegDate) {
		this.userRegDate = userRegDate;
	}

	@JsonProperty("userUpdDate")
	public String getUserUpdDate() {
		return this.userUpdDate;
	}

	public void setUserUpdDate(String userUpdDate) {
		this.userUpdDate = userUpdDate;
	}

	@JsonProperty("userPhoneNo")
	public String getUserPhoneNo() {
		return this.userPhoneNo;
	}

	public void setUserPhoneNo(String userPhoneNo) {
		this.userPhoneNo = userPhoneNo;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EDU0001Response_Body [");
		builder.append("userDelYn=").append(userDelYn);
		builder.append(", ");
		builder.append("userEmail=").append(userEmail);
		builder.append(", ");
		builder.append("userName=").append(userName);
		builder.append(", ");
		builder.append("userRegDate=").append(userRegDate);
		builder.append(", ");
		builder.append("userUpdDate=").append(userUpdDate);
		builder.append(", ");
		builder.append("userPhoneNo=").append(userPhoneNo);
		builder.append("]");
		return builder.toString();
	}
}