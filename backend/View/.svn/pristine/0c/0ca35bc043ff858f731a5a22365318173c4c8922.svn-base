package adapter.demo.model.DM0001;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class DM0001Request_Body {
	/**
	 * 패스워드
	 */
	private String passwd = "";
	/**
	 * 사용자ID
	 */
	private String userId = "";

	public DM0001Request_Body() {
	}

	public DM0001Request_Body(JsonNode jsonNode) {
		this.passwd = jsonNode.path("passwd").textValue();
		this.userId = jsonNode.path("userId").textValue();
	}

	@JsonProperty("passwd")
	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@JsonProperty("userId")
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DM0001Request_Body [");
		builder.append("passwd=").append(passwd);
		builder.append(", ");
		builder.append("userId=").append(userId);
		builder.append("]");
		return builder.toString();
	}
}