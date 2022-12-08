package model.SPL1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class SPL1000S1Request_Body {
	/**
	* 사용자 세션
	*/
	private String JwtToken = "";

	public SPL1000S1Request_Body() {
	}

	public SPL1000S1Request_Body(JsonNode jsonNode) {
		this.JwtToken = jsonNode.path("JwtToken").textValue();
	}

	@JsonProperty("JwtToken")
	public String getJwtToken() {
		return this.JwtToken;
	}

	public void setJwtToken(String JwtToken) {
		this.JwtToken = JwtToken;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SPL1000S1Request_Body [");
		builder.append("JwtToken=").append(JwtToken);
		builder.append("]");
		return builder.toString();
	}
}