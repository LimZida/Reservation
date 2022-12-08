package adapter.demo.model.DM0001;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class DM0001Response_Body {
	/**
	 * 사용자이름
	 */
	private String userName = "";

	public DM0001Response_Body() {
	}

	public DM0001Response_Body(JsonNode jsonNode) {
		this.userName = jsonNode.path("userName").textValue();
	}

	@JsonProperty("userName")
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DM0001Response_Body [");
		builder.append("userName=").append(userName);
		builder.append("]");
		return builder.toString();
	}
}