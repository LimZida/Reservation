package model.LGN1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class LGN1000S1Request_Body {
	/**
	* 사용자 Id
	*/
	private String userId = "";
	/**
	* 사용자 비밀번호
	*/
	private String userPw = "";
	
	public LGN1000S1Request_Body() {
	}

	public LGN1000S1Request_Body(JsonNode jsonNode) {
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
		builder.append("LGN1000S1Request_Body [");
		builder.append("userId=").append(userId);
		builder.append(", ");
		builder.append("userPw=").append(userPw);
		builder.append("]");
		return builder.toString();
	}
}