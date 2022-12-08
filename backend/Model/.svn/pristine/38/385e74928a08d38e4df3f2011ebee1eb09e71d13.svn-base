package model.EML1001S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class EML1001S1Request_Body {
	/**
	* 인증번호
	*/
	private String authCode = "";
	/**
	* 이메일
	*/
	private String inputEmail = "";

	public EML1001S1Request_Body() {
	}

	public EML1001S1Request_Body(JsonNode jsonNode) {
		this.authCode = jsonNode.path("authCode").textValue();
		this.inputEmail = jsonNode.path("inputEmail").textValue();
	}

	@JsonProperty("authCode")
	public String getAuthCode() {
		return this.authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	@JsonProperty("inputEmail")
	public String getInputEmail() {
		return this.inputEmail;
	}

	public void setInputEmail(String inputEmail) {
		this.inputEmail = inputEmail;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EML1001S1Request_Body [");
		builder.append("authCode=").append(authCode);
		builder.append(", ");
		builder.append("inputEmail=").append(inputEmail);
		builder.append("]");
		return builder.toString();
	}
}