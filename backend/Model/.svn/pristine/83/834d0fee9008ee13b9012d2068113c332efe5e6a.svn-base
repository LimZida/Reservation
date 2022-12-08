package model.EDU0001;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class EDU0001Request_Body {
	/**
	* 사용자ID
	*/
	private String userId = "";
	/**
	* 사용자패스워드
	*/
	private String userPw = "";

	public EDU0001Request_Body() {
	}

	public EDU0001Request_Body(JsonNode jsonNode) {
		this.userId = jsonNode.path("userId").textValue();
		this.userPw = jsonNode.path("userPw").textValue();
	}

	@JsonProperty("userId")
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonProperty("userPw")
	public String getUserPw() {
		return this.userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EDU0001Request_Body [");
		builder.append("userId=").append(userId);
		builder.append(", ");
		builder.append("userPw=").append(userPw);
		builder.append("]");
		return builder.toString();
	}
}